package com.example.homestay.service.role;

import com.example.homestay.model.Roles;
import com.example.homestay.service.IGeneralService;

import java.util.Set;

public interface IRoleService extends IGeneralService<Roles> {
    Roles findByName(String name);
    Set<Roles> getRolesByName(Set<String> roleNames);
}
