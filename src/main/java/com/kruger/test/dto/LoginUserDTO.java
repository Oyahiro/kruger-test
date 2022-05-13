package com.kruger.test.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUserDTO {

    @NotBlank private String username;
    @NotBlank private String password;

}
