package com.kruger.test.dto.filter;

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
