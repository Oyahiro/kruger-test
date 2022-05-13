package com.kruger.test.dto;

import com.kruger.test.enitity.Employee;
import com.kruger.test.enitity.Person;
import com.kruger.test.enitity.VaccinateData;
import com.kruger.test.enums.VaccinateType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

@Data
public class EmployeeDTO {

    @NotBlank private String identificationNumber;
    @NotBlank private String fullName;
    @NotBlank private String email;
    private Date username;
    private Date dateBirth;
    private String address;
    private String phoneNumber;
    private Boolean vaccinated;
    private VaccinateType vaccinateType;
    private Date vaccinateDate;
    private Short doseAmount;

    public EmployeeDTO(Person person) {
        Employee employee = person.getEmployee();

        this.identificationNumber = person.getIdentificationNumber();
        this.fullName = person.getFullName();
        this.email = person.getEmail();

        if(Objects.nonNull(employee)) {
            VaccinateData vaccinateData = employee.getVaccinateData();

            this.dateBirth = employee.getDateBirth();
            this.address = employee.getAddress();
            this.phoneNumber = employee.getPhoneNumber();

            if(Objects.nonNull(vaccinateData)) {
                this.vaccinated = vaccinateData.getVaccinated();
                this.vaccinateType = vaccinateData.getVaccinateType();
                this.vaccinateDate = vaccinateData.getVaccinateDate();
                this.doseAmount = vaccinateData.getDoseAmount();
            }
        }
    }
}
