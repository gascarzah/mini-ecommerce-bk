package com.example.demo.api.web.dto;

import com.example.demo.api.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserFormDTO {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private User.Role role;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
