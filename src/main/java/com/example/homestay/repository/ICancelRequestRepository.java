package com.example.homestay.repository;

import com.example.homestay.model.CancelRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICancelRequestRepository extends JpaRepository<CancelRequest, Long> {
}
