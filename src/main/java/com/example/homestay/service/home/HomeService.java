package com.example.homestay.service.home;

import com.example.homestay.model.DTO.HomeSearch;
import com.example.homestay.model.DTO.IncomeDTO;
import com.example.homestay.model.Homes;
import com.example.homestay.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HomeService implements IHomeService {
    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    public List<Homes> findAll() {
        return homeRepository.findAll();
    }

    @Override
    public List<Homes> findByUsers(Long userId) {
        return homeRepository.findByUsers_Id(userId);
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
    public List<Homes> findHomeByHomeTypeId(Long id) {
        return homeRepository.findHomesByHomeTypeId(id);
    }

    @Override
    public List<Map<String, Object>> getHomesWithAverageRating() {
        String sql = "SELECT h.id, h.name, h.address, h.bathroom, h.bedroom, h.description, h.price_by_day, h.home_type_id, h.user_id , ROUND(AVG(rv.rating), 2) AS avgRating " +
                "FROM homes h " +
                "LEFT JOIN review rv ON h.id = rv.homes_id " +
                "GROUP BY h.id";
        return jdbcTemplate.queryForList(sql);
    }

}
