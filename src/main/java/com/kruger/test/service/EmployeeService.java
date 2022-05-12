package com.kruger.test.service;

import com.kruger.test.enitity.Employee;
import com.kruger.test.enitity.filter.EmployeeFilter;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    Employee saveEmployee(Employee employee) throws Exception;
    Employee updateEmployee(Employee employee) throws Exception;
    Page<Employee> filterEmployees(EmployeeFilter filter) throws Exception;

}
