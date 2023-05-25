package com.example.homestay.service.rentalHistory;

import com.example.homestay.model.RentalHistory;
import com.example.homestay.repository.IRentalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RentalHistoryService implements IRentalHistoryService {
    @Autowired
    IRentalHistoryRepository rentalHistoryRepository;

    @Override
    public Iterable<RentalHistory> findAll() {
        return rentalHistoryRepository.findAll();
    }

    @Override
    public Optional<RentalHistory> findById(Long id) {
        return rentalHistoryRepository.findById(id);
    }

    @Override
    public RentalHistory save(RentalHistory rentalHistory) {
        return rentalHistoryRepository.save(rentalHistory);
    }

    @Override
    public void remove(Long id) {
        rentalHistoryRepository.deleteById(id);
    }
}
