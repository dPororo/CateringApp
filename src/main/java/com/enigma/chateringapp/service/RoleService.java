package com.enigma.chateringapp.service;

import com.enigma.chateringapp.constant.ERole;
import com.enigma.chateringapp.model.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
