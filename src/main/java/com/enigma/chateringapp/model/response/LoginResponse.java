package com.enigma.chateringapp.model.response;

import com.enigma.chateringapp.constant.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String username;
    private List<ERole> roles;
    private String token;
}
