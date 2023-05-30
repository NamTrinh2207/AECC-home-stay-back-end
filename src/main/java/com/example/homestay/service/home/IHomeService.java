package com.example.homestay.service.home;

import com.example.homestay.model.DTO.HomeSearch;
import com.example.homestay.model.DTO.IncomeDTO;
import com.example.homestay.model.Homes;
import com.example.homestay.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IHomeService extends IGeneralService<Homes> {
    List<Homes> findAll();

    List<Homes> findByUsers(Long userId);

    List<HomeSearch> getAllSearchHomes();

    Optional<Homes> updateStatusAfterBooking(Long id);
    List<IncomeDTO> getUserIncome(@Param("userId") Long userId);

    Page<Homes> findHomeByHomeTypeId(Long id, Pageable pageable);
}
