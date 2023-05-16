package com.example.homestay.service.home;

import com.example.homestay.model.Homes;
import com.example.homestay.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HomeService implements IHomeService{
    @Autowired
    private HomeRepository homeRepository;

    @Override
    public Iterable<Homes> findAll() {
        return homeRepository.findAll();
    }

    @Override
    public Optional<Homes> findById(Long id) {
        return homeRepository.findById(id);
    }

    @Override
    public Homes save(Homes homes) {
        return homeRepository.save(homes);
    }

    @Override
    public void remove(Long id) {
        homeRepository.deleteById(id);
    }
}
