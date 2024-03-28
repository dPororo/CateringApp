package com.enigma.chateringapp.service;


import com.enigma.chateringapp.model.request.AuthRequest;
import com.enigma.chateringapp.model.response.LoginResponse;
import com.enigma.chateringapp.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest request);
    LoginResponse login(AuthRequest authRequest);
}
