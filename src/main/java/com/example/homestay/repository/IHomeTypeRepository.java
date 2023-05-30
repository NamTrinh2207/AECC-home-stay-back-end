package com.example.homestay.repository;

import com.example.homestay.model.HomeType;
import com.example.homestay.model.Homes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHomeTypeRepository extends JpaRepository<HomeType, Long> {
}
