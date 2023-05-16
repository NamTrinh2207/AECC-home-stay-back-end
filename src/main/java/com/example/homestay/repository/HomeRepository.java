package com.example.homestay.repository;

import com.example.homestay.model.Homes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends JpaRepository<Homes, Long> {
}
