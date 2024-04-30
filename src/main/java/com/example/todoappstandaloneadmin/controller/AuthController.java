package com.example.todoappstandaloneadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoappstandaloneadmin.dto.AuthorizationResponseDto;
import com.example.todoappstandaloneadmin.dto.LoginDto;
import com.example.todoappstandaloneadmin.dto.RegisterDto;
import com.example.todoappstandaloneadmin.entity.UserEntity;
import com.example.todoappstandaloneadmin.entity.UserRoles;
import com.example.todoappstandaloneadmin.repository.RoleRepository;
import com.example.todoappstandaloneadmin.repository.UserRepository;
import com.example.todoappstandaloneadmin.security.configurations.JWTGenerator;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    private final JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder encoder,
            JWTGenerator jwtGenerator) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDto> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerDto.getUsername());
        userEntity.setPassword(encoder.encode((registerDto.getPassword())));

        UserRoles userRoles = roleRepository.findByName("USER").orElse(null);
        userEntity.setRoles(Collections.singletonList(userRoles));

        userRepository.save(userEntity);
        return new ResponseEntity<>(registerDto, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<AuthorizationResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication  = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthorizationResponseDto(token), HttpStatus.OK);
    }
}
