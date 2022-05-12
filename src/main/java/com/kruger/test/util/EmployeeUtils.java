package com.kruger.test.util;

import com.kruger.test.enitity.Employee;

public class EmployeeUtils {

    public static Employee getPartialEmployee(Employee employee) {
        Employee partialEmployee = new Employee();
        partialEmployee.setId(employee.getId());
        partialEmployee.setDocument(employee.getDocument());
        partialEmployee.setNames(employee.getNames());
        partialEmployee.setSurnames(employee.getSurnames());
        partialEmployee.setEmail(employee.getEmail());
        return partialEmployee;
    }

}
