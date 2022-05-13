package com.kruger.test.dto;

import com.kruger.test.enitity.Person;
import com.kruger.test.enitity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    @NotBlank private String identificationNumber;
    @NotBlank private String fullName;
    @NotBlank private String email;
    @NotBlank private String username;
    @NotEmpty private Set<String> roles = new HashSet<>();

    public UserDTO(Person person, User user) {
        this.identificationNumber = person.getIdentificationNumber();
        this.fullName = person.getFullName();
        this.email = person.getEmail();
        this.username = user.getUsername();
        this.roles = user.getRoles();
    }

}
