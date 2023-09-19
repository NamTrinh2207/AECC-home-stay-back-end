package com.example.homestay.service.home;

import com.example.homestay.model.Booking;
import com.example.homestay.model.DTO.HomeSearch;
import com.example.homestay.model.DTO.IncomeDTO;
import com.example.homestay.model.DTO.SearchResultDTO;
import com.example.homestay.model.DTO.request.InputSearch;
import com.example.homestay.model.Homes;
import com.example.homestay.model.SearchPredicateBuilder;
import com.example.homestay.model.Users;
import com.example.homestay.repository.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
* @author: Nam
* @since: 18/09/2023 11:55 PM
* */

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
    public List<SearchResultDTO> searchByMoreNormal(InputSearch search) {
        LocalDate checkinDate;
        if (!search.getCheckin().isEmpty()) {
            checkinDate = LocalDate.parse(search.getCheckin());
        } else {
            checkinDate = null;
        }
        return homeRepository.findAll(((root, query, criteriaBuilder) -> {
            Join<Homes, Users> homesUsersJoin = root.join("users", JoinType.INNER);
            Join<Users, Booking> usersBookingJoin = homesUsersJoin.join("bookings", JoinType.LEFT);
            Predicate alwaysTrue = criteriaBuilder.and();

                alwaysTrue = criteriaBuilder.and(alwaysTrue,
                        criteriaBuilder.like(root.get("address"), "%" + search.getAddress() + "%"));
            if (search.getBedroom() != 0) {
                alwaysTrue = criteriaBuilder.and(alwaysTrue,
                        criteriaBuilder.equal(root.get("bedroom"), search.getBedroom()));
            }
            if (search.getBathroom() != 0) {
                alwaysTrue = criteriaBuilder.and(alwaysTrue,
                        criteriaBuilder.equal(root.get("bathroom"), search.getBathroom()));
            }
            if (search.getMinPrice() != 0 || search.getMaxPrice() != 0) {
                alwaysTrue = criteriaBuilder.and(alwaysTrue,
                        criteriaBuilder.between(root.get("priceByDay"), search.getMinPrice(), search.getMaxPrice()));
            }
            if (!search.getCheckin().isEmpty()) {
                alwaysTrue = criteriaBuilder.and(alwaysTrue,
                        criteriaBuilder.or(
                                criteriaBuilder.isNull(usersBookingJoin),
                                criteriaBuilder.or(
                                        criteriaBuilder.greaterThan(usersBookingJoin.get("checkout"), checkinDate),
                                        criteriaBuilder.lessThan(usersBookingJoin.get("checkin"), checkinDate)
                                )
                        ));
            }
            return alwaysTrue;
        })).stream().map(homes -> {
            String image = homes.getImage().stream().findFirst().orElse(null);
            return new SearchResultDTO(
                    homes.getId(),
                    homes.getAddress(),
                    homes.getBedroom(),
                    homes.getBathroom(),
                    homes.getName(),
                    homes.getPriceByDay(),
                    homes.getStatus(),
                    Collections.singleton(image),
                    homes.getUsers().getUsername(),
                    homes.getHomeType().getName()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<Homes> searchAll(InputSearch search) {
        LocalDate checkinDate = search.getCheckin().isEmpty() ? null : LocalDate.parse(search.getCheckin());
        return homeRepository.findAll(((root, query, criteriaBuilder) -> {
            Predicate result = criteriaBuilder.and();
            result = criteriaBuilder.and(result,SearchPredicateBuilder.
                    buildAddressPredicate(criteriaBuilder, root, search.getAddress()));
            if (search.getBedroom() != 0){
                result = criteriaBuilder.and(result,SearchPredicateBuilder.
                        buildBedroomPredicate(criteriaBuilder, root, search.getBedroom()));
            }
            if (search.getBathroom() != 0) {
                result = criteriaBuilder.and(result,SearchPredicateBuilder.
                        buildBathroomPredicate(criteriaBuilder, root, search.getBathroom()));
            }
            if (search.getMinPrice() != 0 || search.getMaxPrice() !=0){
                result = criteriaBuilder.and(result,SearchPredicateBuilder.
                        buildPriceByDayPredicate(criteriaBuilder, root, search.getMinPrice(), search.getMaxPrice()));
            }
            result = criteriaBuilder.and(result,SearchPredicateBuilder.
                    buildBookingConditionsPredicate(criteriaBuilder, root, checkinDate));
            return result;
        }));
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
