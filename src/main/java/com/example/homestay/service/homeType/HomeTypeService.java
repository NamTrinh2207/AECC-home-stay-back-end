package com.example.homestay.service.homeType;

import com.example.homestay.model.HomeType;
import com.example.homestay.model.Homes;
import com.example.homestay.repository.IHomeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class HomeTypeService implements IHomeTypeService{
    @Autowired
    private IHomeTypeRepository homeTypeRepository;
    @Override
    public Iterable<HomeType> findAll() {
        return homeTypeRepository.findAll();
    }

    @Override
    public Optional<HomeType> findById(Long id) {
        return homeTypeRepository.findById(id);
    }

    @Override
    public HomeType save(HomeType homeType) {
        return homeTypeRepository.save(homeType);
    }

    @Override
    public void remove(Long id) {
        homeTypeRepository.deleteById(id);
    }
}
