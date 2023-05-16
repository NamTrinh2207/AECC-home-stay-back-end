package com.example.homestay.repository;

import com.example.homestay.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Roles, Long> {
    Roles findByName(String name);
}
