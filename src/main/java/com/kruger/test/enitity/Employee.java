package com.kruger.test.enitity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "employee")
@EqualsAndHashCode(callSuper = true)
public class Employee extends AbstractEntity {

    @Temporal(TemporalType.DATE)
    @Column(name="date_birth")
    private Date dateBirth;

    @Column(name="address", length = 50)
    private String address;

    @Column(name="phone_number", length = 10)
    private String phoneNumber;

    @Embedded
    private VaccinateData vaccinateData;

}
