package com.example.homestay.model;

import javax.persistence.criteria.*;
import java.time.LocalDate;

public class SearchPredicateBuilder {
    public static Predicate buildAddressPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, String address) {
        return criteriaBuilder.like(root.get("address"), "%" + address + "%");
    }

    public static Predicate buildBedroomPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, int bedroom) {
        return criteriaBuilder.equal(root.get("bedroom"), bedroom);
    }

    public static Predicate buildBathroomPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, int bathroom) {
        return criteriaBuilder.equal(root.get("bathroom"), bathroom);
    }

    public static Predicate buildPriceByDayPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, double minPrice, double maxPrice) {
        return criteriaBuilder.between(root.get("priceByDay"), minPrice, maxPrice);
    }

    public static Predicate buildBookingConditionsPredicate(CriteriaBuilder criteriaBuilder, Root<?> root, LocalDate checkinDate) {
        Join<?, ?> usersBookingJoin = root.join("users", JoinType.INNER).join("bookings", JoinType.LEFT);

        return criteriaBuilder.or(
                criteriaBuilder.isNull(usersBookingJoin),
                criteriaBuilder.or(
                        criteriaBuilder.greaterThan(usersBookingJoin.get("checkout"), checkinDate),
                        criteriaBuilder.lessThan(usersBookingJoin.get("checkin"), checkinDate)
                )
        );
    }
}
