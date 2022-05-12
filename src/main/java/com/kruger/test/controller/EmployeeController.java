package com.kruger.test.controller;

import com.kruger.test.enitity.Employee;
import com.kruger.test.enitity.filter.EmployeeFilter;
import com.kruger.test.service.EmployeeService;
import com.kruger.test.util.ResponseEntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

import static com.kruger.test.common.Constants.URI_EMPLOYEE;

@RestController
@RequestMapping(value = {URI_EMPLOYEE})
public class EmployeeController {

    private final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("save")
    public ResponseEntity<Employee> save(@Valid @RequestBody Employee employee) {
        try {
            Employee employeeResponse = employeeService.saveEmployee(employee);
            return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return ResponseEntity
                    .badRequest()
                    .headers(ResponseEntityUtils.generateErrorHeaders(Employee.class, ex))
                    .body(null);
        }
    }

    @Transactional
    @PostMapping("update")
    public ResponseEntity<Employee> update(@Valid @RequestBody Employee employee) {
        try {
            Employee employeeResponse = employeeService.updateEmployee(employee);
            return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return ResponseEntity
                    .badRequest()
                    .headers(ResponseEntityUtils.generateErrorHeaders(Employee.class, ex))
                    .body(null);
        }
    }

    @PostMapping("filter")
    public ResponseEntity<Page<Employee>> filter(@RequestBody EmployeeFilter filter) {
        try {
            Page<Employee> employees = employeeService.filterEmployees(filter);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return ResponseEntity
                    .badRequest()
                    .headers(ResponseEntityUtils.generateErrorHeaders(Employee.class, ex))
                    .body(null);
        }
    }

}
