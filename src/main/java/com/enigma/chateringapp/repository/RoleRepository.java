package com.enigma.chateringapp.repository;


import com.enigma.chateringapp.constant.ERole;
import com.enigma.chateringapp.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
