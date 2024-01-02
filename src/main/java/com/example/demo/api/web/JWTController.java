package com.example.demo.api.web;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.domain.User;
import com.example.demo.api.exception.ResourceNotFoundException;
import com.example.demo.api.repository.UserRepository;
import com.example.demo.api.security.jwt.TokenProvider;
import com.example.demo.api.web.dto.AuthRequest;
import com.example.demo.api.web.dto.AuthResponse;
import com.example.demo.api.web.dto.UserProfileDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/api")
@RestController
public class JWTController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        User user = userRepository
                .findByEmail(authRequest.getEmail())
                .orElseThrow(ResourceNotFoundException::new);

        UserProfileDTO userProfileDTO = new ModelMapper().map(user, UserProfileDTO.class);
        AuthResponse authResponse = new AuthResponse(token, userProfileDTO);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(authResponse);
    }

}
