package com.example.demo.api.web.dto;

import com.example.demo.api.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private User.Role role;
}
