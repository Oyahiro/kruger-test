package com.kruger.test.service;

import com.kruger.test.dto.EmployeeDTO;
import com.kruger.test.dto.UserDTO;
import com.kruger.test.enitity.Employee;
import com.kruger.test.dto.filter.EmployeeFilter;
import com.kruger.test.enitity.Person;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    UserDTO saveInitialData(Person person) throws Exception;
    EmployeeDTO saveEmployeeData(Employee employee, String identificationNumber) throws Exception;
    Page<EmployeeDTO> filterEmployees(EmployeeFilter filter) throws Exception;

}
