package com.example.homestay.repository;

import com.example.homestay.model.RentalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRentalHistoryRepository extends JpaRepository<RentalHistory, Long> {

}
