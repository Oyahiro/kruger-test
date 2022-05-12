package com.kruger.test.service;

import com.kruger.test.enitity.Employee;
import com.kruger.test.enitity.filter.EmployeeFilter;
import com.kruger.test.enums.VaccinateType;
import com.kruger.test.repository.EmployeeRepository;
import com.kruger.test.util.EmployeeUtils;
import com.kruger.test.util.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee saveEmployee(Employee employee) throws Exception {
        Assert.isTrue(ValidationUtils.validateDocument(employee.getDocument()), "Document is not valid");
        Optional<Employee> optionalEmployee = employeeRepository.findByDocument(employee.getDocument());
        if(optionalEmployee.isPresent()) {
            throw new Exception("There is already a employee registered with this identification number");
        }

        return employeeRepository.save(EmployeeUtils.getPartialEmployee(employee));
    }

    @Override
    public Employee updateEmployee(Employee employee) throws Exception {
        Employee employeeToUpdate = employeeRepository.findByDocument(employee.getDocument())
                .orElseThrow(() -> new Exception(String.format("There is no employee registered with identification number %s", (employee.getDocument()))));

        employeeToUpdate.setDateBirth(employee.getDateBirth());
        employeeToUpdate.setAddress(employee.getAddress());
        employeeToUpdate.setPhoneNumber(employee.getPhoneNumber());
        employeeToUpdate.setVaccinateData(employee.getVaccinateData());

        return employeeRepository.save(employeeToUpdate);
    }

    @Override
    public Page<Employee> filterEmployees(EmployeeFilter filter) throws Exception {
        Assert.notNull(filter, "Filters are required");
        Assert.notNull(filter.getPage(), "Page number is required");
        Assert.notNull(filter.getSize(), "Page size is required");

        PageRequest pageable = PageRequest.of(filter.getPage(), filter.getSize());

        Collection<Boolean> vaccinatedOptions = Objects.nonNull(filter.getVaccinateStatus())
                ? List.of(filter.getVaccinateStatus())
                : Arrays.asList(Boolean.TRUE, Boolean.FALSE);

        Collection<VaccinateType> vaccinateTypes = Objects.nonNull(filter.getVaccinateType())
                ? List.of(filter.getVaccinateType())
                : Arrays.stream(VaccinateType.values()).collect(Collectors.toList());

        if(Objects.nonNull(filter.getVaccinateDateFrom()) && Objects.nonNull(filter.getVaccinateDateTo())) {
            return employeeRepository.findAllByVaccinateDataVaccinatedInAndVaccinateDataVaccinateDateBetweenAndVaccinateDataVaccinateTypeInOrderBySurnames(
                    vaccinatedOptions,
                    filter.getVaccinateDateFrom(),
                    filter.getVaccinateDateTo(),
                    vaccinateTypes,
                    pageable
            );
        } else {
            return employeeRepository.findAllByVaccinateDataVaccinatedInAndVaccinateDataVaccinateTypeInOrderBySurnames(
                    vaccinatedOptions,
                    vaccinateTypes,
                    pageable
            );
        }
    }
}
