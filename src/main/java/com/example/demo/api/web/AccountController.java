package com.example.demo.api.web;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.domain.User;
import com.example.demo.api.exception.BadRequestException;
import com.example.demo.api.repository.UserRepository;
import com.example.demo.api.web.dto.SignupUserDTO;
import com.example.demo.api.web.dto.UserProfileDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    UserProfileDTO signup(@RequestBody @Validated SignupUserDTO signupUserDTO) {
        boolean existsEmail = userRepository.existsByEmail(signupUserDTO.getEmail());

        if (existsEmail) {
            throw new BadRequestException("Email already exists.");
        }
        User user = new ModelMapper().map(signupUserDTO, User.class);
        user.setPassword(passwordEncoder.encode(signupUserDTO.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
//        user.setRole(User.Role.USER);
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);

        return new ModelMapper().map(user, UserProfileDTO.class);
    }

}
