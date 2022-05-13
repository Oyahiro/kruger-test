package com.kruger.test.controller;

import com.kruger.test.dto.EmployeeDTO;
import com.kruger.test.dto.UserDTO;
import com.kruger.test.enitity.Employee;
import com.kruger.test.dto.filter.EmployeeFilter;
import com.kruger.test.enitity.Person;
import com.kruger.test.service.EmployeeService;
import com.kruger.test.util.ResponseEntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("save")
    public ResponseEntity<UserDTO> save(@Valid @RequestBody Person person) {
        try {
            UserDTO userResponse = employeeService.saveInitialData(person);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return ResponseEntity
                    .badRequest()
                    .headers(ResponseEntityUtils.generateErrorHeaders(Person.class, ex))
                    .body(null);
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @Transactional
    @PostMapping("update/{identificationNumber}")
    public ResponseEntity<EmployeeDTO> update(@Valid @RequestBody Employee employee,
                                           @PathVariable String identificationNumber) {
        try {
            EmployeeDTO employeeResponse = employeeService.saveEmployeeData(employee, identificationNumber);
            return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            return ResponseEntity
                    .badRequest()
                    .headers(ResponseEntityUtils.generateErrorHeaders(Employee.class, ex))
                    .body(null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("filter")
    public ResponseEntity<Page<EmployeeDTO>> filter(@RequestBody EmployeeFilter filter) {
        try {
            Page<EmployeeDTO> employees = employeeService.filterEmployees(filter);
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
