package com.kruger.test.service;

import com.kruger.test.common.Constants;
import com.kruger.test.dto.EmployeeDTO;
import com.kruger.test.dto.UserDTO;
import com.kruger.test.enitity.Employee;
import com.kruger.test.dto.filter.EmployeeFilter;
import com.kruger.test.enitity.Person;
import com.kruger.test.enitity.User;
import com.kruger.test.enums.Role;
import com.kruger.test.enums.VaccinateType;
import com.kruger.test.repository.EmployeeRepository;
import com.kruger.test.repository.PersonRepository;
import com.kruger.test.util.SessionUtils;
import com.kruger.test.util.StringUtils;
import com.kruger.test.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final PersonRepository personRepository;
    private final UserService userService;

    public EmployeeServiceImpl(PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, PersonRepository personRepository, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.personRepository = personRepository;
        this.userService = userService;
    }


    @Override
    public UserDTO saveInitialData(Person person) throws Exception {
        Assert.isTrue(ValidationUtils.validateDocument(person.getIdentificationNumber()), "Identification number is not valid");
        Optional<Person> optionalPerson = personRepository.findByIdentificationNumber(person.getIdentificationNumber());
        if(optionalPerson.isPresent()) {
            throw new Exception("There is already a employee registered with this identification number");
        }

        person = personRepository.saveAndFlush(person);

        String newUsername = null;
        int additional = 0;

        do {
            String possiblyUsername = StringUtils.generateUsername(person.getNames(), person.getSurnames(), additional);
            if(!userService.existsByUsername(possiblyUsername)) {
                newUsername = possiblyUsername;
            }
            additional += 1;
        } while(Objects.isNull(newUsername));

        User user = new User(newUsername, passwordEncoder.encode(person.getIdentificationNumber()));
        user.setRoles(new HashSet<>(Collections.singletonList(Role.ROLE_EMPLOYEE.toString())));
        user.setPerson(person);
        userService.save(user);

        return new UserDTO(person, user);
    }

    @Override
    public EmployeeDTO saveEmployeeData(Employee employee, String identificationNumber) throws Exception {
        Person person = personRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new Exception(String.format("There is no employee registered with identification number %s", (identificationNumber))));
        User user = SessionUtils.getValueFromSession(Constants.CURRENT_USER);
        boolean existsEmployee = Objects.nonNull(person.getEmployee());

        if(Objects.isNull(user) ||
                !user.getPerson().getIdentificationNumber().equals(person.getIdentificationNumber())) {
            throw new Exception("The current user does not have permission to update this information");
        }

        if(existsEmployee) {
            employee.setId(person.getEmployee().getId());
            employeeRepository.save(employee);
        } else {
            employee = employeeRepository.saveAndFlush(employee);
            person.setEmployee(employee);
            personRepository.save(person);
        }

        return new EmployeeDTO(person);
    }

    @Override
    public Page<EmployeeDTO> filterEmployees(EmployeeFilter filter) {
        Assert.notNull(filter, "Filters are required");
        Assert.notNull(filter.getPage(), "Page number is required");
        Assert.notNull(filter.getSize(), "Page size is required");

        Page<Person> filterResult;
        PageRequest pageable = PageRequest.of(filter.getPage(), filter.getSize());

        Collection<Boolean> vaccinatedOptions = Objects.nonNull(filter.getVaccinateStatus())
                ? List.of(filter.getVaccinateStatus())
                : Arrays.asList(Boolean.TRUE, Boolean.FALSE);

        Collection<VaccinateType> vaccinateTypes = Objects.nonNull(filter.getVaccinateType())
                ? List.of(filter.getVaccinateType())
                : Arrays.stream(VaccinateType.values()).collect(Collectors.toList());

        if(Objects.nonNull(filter.getVaccinateDateFrom()) && Objects.nonNull(filter.getVaccinateDateTo())) {
            filterResult = personRepository.findAllByEmployeeVaccinateDataVaccinatedInAndEmployeeVaccinateDataVaccinateDateBetweenAndEmployeeVaccinateDataVaccinateTypeInOrderBySurnames(
                    vaccinatedOptions,
                    filter.getVaccinateDateFrom(),
                    filter.getVaccinateDateTo(),
                    vaccinateTypes,
                    pageable
            );
        } else {
            filterResult = personRepository.findAllByEmployeeVaccinateDataVaccinatedInAndEmployeeVaccinateDataVaccinateTypeInOrderBySurnames(
                    vaccinatedOptions,
                    vaccinateTypes,
                    pageable
            );
        }
        return filterResult.map(EmployeeDTO::new);
    }
}
