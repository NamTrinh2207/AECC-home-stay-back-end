package com.example.homestay.repository;

import com.example.homestay.model.HomeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHomeTypeRepository extends JpaRepository<HomeType, Long> {
}
