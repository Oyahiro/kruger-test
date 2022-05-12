package com.kruger.test.enitity.filter;

import com.kruger.test.enums.VaccinateType;
import lombok.Getter;

import java.util.Date;

@Getter
public class EmployeeFilter extends PageFilter {
    Boolean vaccinateStatus;
    VaccinateType vaccinateType;
    Date vaccinateDateFrom;
    Date vaccinateDateTo;
}
