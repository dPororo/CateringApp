package com.enigma.chateringapp.service.impl;

import com.enigma.chateringapp.constant.ERole;
import com.enigma.chateringapp.model.entity.Role;
import com.enigma.chateringapp.repository.RoleRepository;
import com.enigma.chateringapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    @Override
    public Role getOrSave(ERole role) {
        Optional<Role> optionalRole = repository.findByName(role);

//        return with lamba
//        return optionalRole.orElseGet(() -> repository.save(role));

        if (!optionalRole.isEmpty()){
            return optionalRole.get();
        }

        Role currentRole = Role.builder()
                .name(role)
                .build();
        return repository.saveAndFlush(currentRole);
    }
}
