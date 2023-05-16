package com.example.homestay.service.role;


import com.example.homestay.model.Roles;
import com.example.homestay.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository roleRepo;

    @Override
    public Iterable<Roles> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public Optional<Roles> findById(Long id) {
        return roleRepo.findById(id);
    }

    @Override
    public Roles save(Roles roles) {
        return roleRepo.save(roles);
    }

    @Override
    public void remove(Long id) {
        roleRepo.deleteById(id);
    }

    @Override
    public Roles findByName(String name) {
        return roleRepo.findByName(name);
    }

    @Override
    public Set<Roles> getRolesByName(Set<String> roleNames) {
        Set<Roles> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Roles role = roleRepo.findByName(roleName);
            if (role != null) {
                roles.add(role);
            }
        }
        return roles;
    }
}
