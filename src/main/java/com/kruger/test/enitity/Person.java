package com.kruger.test.enitity;

import com.kruger.test.util.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
@Table(name="person", uniqueConstraints = {
        @UniqueConstraint(name = "UK_PERSON_IDENTIFICATION_NUMBER", columnNames = {"identification_number"})
})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Person extends AbstractEntity {

    @NotEmpty
    @Size(min = 10, max = 10)
    @Pattern(regexp="^[0-9]{10}$", message="This field only accepts numeric values")
    @Column(name="identification_number", length = 10)
    private String identificationNumber;

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

    @OneToOne
    @JoinColumn(name="employee_id", referencedColumnName="id")
    private Employee employee;

     public String getFullName() {
        return concatNames(names, surnames);
    }

    public String getFullNameInverse() {
        return concatNames(surnames, names);
    }

    private String concatNames(String... names) {
        return Stream.of(names)
                .map(StringUtils::trimToNull)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }

}
