package com.enigma.chateringapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequest {
    private String password;
    private String username;
    private String name;
    private String address;
    private String phone;
    private String email;
}
