package com.example.homestay.service.home;

import com.example.homestay.model.DTO.HomeSearch;
import com.example.homestay.model.DTO.IncomeDTO;
import com.example.homestay.model.Homes;
import com.example.homestay.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HomeService implements IHomeService {
    @Autowired
    private HomeRepository homeRepository;


    @Override
    public Iterable<Homes> findAll() {
        return null;
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

    @Override
    public Page<Homes> findAll(Pageable pageable) {
        return homeRepository.findAll(pageable);
    }

    @Override
    public Page<Homes> findByUsers(Long userId, Pageable pageable) {
        return homeRepository.findByUsers_Id(userId, pageable);
    }

    @Override
    public List<HomeSearch> getAllSearchHomes() {
        return homeRepository.getAllSearchHomes();
    }

    @Override
    public Optional<Homes> updateStatusAfterBooking(Long id) {
        return homeRepository.updateStatusAfterBooking(id);
    }

    @Override
    public List<IncomeDTO> getUserIncome(Long userId) {
        return homeRepository.getUserIncome(userId);
    }
    @Override
    public Page<Homes> findHomeByHomeTypeId(Long id, Pageable pageable) {
        return homeRepository.findHomesByHomeTypeId(id, pageable);
    }

}
