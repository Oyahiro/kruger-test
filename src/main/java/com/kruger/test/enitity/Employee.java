package com.kruger.test.enitity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Entity
@Table(name = "employee", uniqueConstraints = {
        @UniqueConstraint(name = "UK_EMPLOYEE_IDENTIFICATION", columnNames = {"document"})
})
@EqualsAndHashCode(callSuper = true)
public class Employee extends AbstractEntity {

    @NotEmpty
    @Size(min = 10, max = 10)
    @Pattern(regexp="^[0-9]{10}$", message="This field only accepts numeric values")
    @Column(name="document", length = 10)
    private String document;

    @NotEmpty
    @Pattern(regexp="^[A-Za-z_ ]*$", message="This field only accepts letters")
    @Column(name="names", length = 50)
    private String names;

    @NotEmpty
    @Pattern(regexp="^[A-Za-z_ ]*$", message="This field only accepts letters")
    @Column(name="surnames", length = 50)
    private String surnames;

    @NotEmpty
    @Email
    @Column(name="email", length = 40)
    private String email;

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
