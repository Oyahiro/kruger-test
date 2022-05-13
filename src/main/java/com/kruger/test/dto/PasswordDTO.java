package com.kruger.test.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PasswordDTO {

    private String token;
    private String oldPassword;
    @NotBlank private String newPassword;

}
