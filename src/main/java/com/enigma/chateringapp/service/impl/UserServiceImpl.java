package com.enigma.chateringapp.service.impl;

import com.enigma.chateringapp.model.entity.AppUser;
import com.enigma.chateringapp.model.entity.UserCredential;
import com.enigma.chateringapp.repository.UserCredentialRepository;
import com.enigma.chateringapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserCredentialRepository userCredentialRepository;

    @Override
    public AppUser loadUserByUserId(String id) {
        // verif JWT
        UserCredential userCredential = userCredentialRepository.findById(id).orElseThrow(() -> new
                UsernameNotFoundException("Invalid credential!"));
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // verif uname
        UserCredential userCredential = userCredentialRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Invalid credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }
}
