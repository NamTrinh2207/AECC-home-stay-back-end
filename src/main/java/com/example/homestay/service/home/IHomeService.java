package com.example.homestay.service.home;

import com.example.homestay.model.Homes;
import com.example.homestay.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IHomeService extends IGeneralService<Homes> {
    Page<Homes> findAll(Pageable pageable);
    Page<Homes> findByUsers(Long userId, Pageable pageable);
    List<Homes> findTop5(int limit);

}
