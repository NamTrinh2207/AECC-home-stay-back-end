package com.example.homestay.service.cancelRequest;

import com.example.homestay.model.CancelRequest;
import com.example.homestay.repository.ICancelRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CancelRequestService implements ICancelRequestService {
    @Autowired
    private ICancelRequestRepository cancelRequestRepository;

    @Override
    public Iterable<CancelRequest> findAll() {
        return cancelRequestRepository.findAll();
    }

    @Override
    public Optional<CancelRequest> findById(Long id) {
        return cancelRequestRepository.findById(id);
    }

    @Override
    public CancelRequest save(CancelRequest cancelRequest) {
        return cancelRequestRepository.save(cancelRequest);
    }

    @Override
    public void remove(Long id) {
        cancelRequestRepository.deleteById(id);
    }
}
