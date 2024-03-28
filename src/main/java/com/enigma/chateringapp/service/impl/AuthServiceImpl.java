package com.enigma.chateringapp.service.impl;

import com.enigma.chateringapp.constant.ERole;
import com.enigma.chateringapp.model.entity.AppUser;
import com.enigma.chateringapp.model.entity.Customer;
import com.enigma.chateringapp.model.entity.Role;
import com.enigma.chateringapp.model.entity.UserCredential;
import com.enigma.chateringapp.model.request.AuthRequest;
import com.enigma.chateringapp.model.request.CustomerRequest;
import com.enigma.chateringapp.model.response.LoginResponse;
import com.enigma.chateringapp.model.response.RegisterResponse;
import com.enigma.chateringapp.repository.UserCredentialRepository;
import com.enigma.chateringapp.security.JwtUtil;
import com.enigma.chateringapp.service.AuthService;
import com.enigma.chateringapp.service.CustomerService;
import com.enigma.chateringapp.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {

        try {
            //set role
            Role role = roleService.getOrSave(ERole.ROLE_CUSTOMER);

            //set credential
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            //set customer
            Customer customer = Customer.builder()
                    .name(request.getName())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .build();
            customerService.save(customer);

            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .roles(List.of(userCredential.getRole().getName()))
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist!");
        }
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(
                authRequest.getUsername().toLowerCase(),
                authRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        return LoginResponse.builder()
                .token(token)
                .username(appUser.getUsername())
                .roles(List.of(appUser.getRole()))
                .build();
    }
}
