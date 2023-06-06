package com.example.homestay.repository;

import com.example.homestay.model.Booking;
import com.example.homestay.model.DTO.IGetMostRentedBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
    @Query(nativeQuery = true, value = "SELECT h.id, h.address as address, h.bedroom, h.bathroom, h.name as name, h.status as status, h.price_by_day as pricebyday, ht.name as hometype, u.name as username,r.rating as rating, COUNT( DISTINCT b.id) AS bookingcount, min(hi.image) AS images FROM homes h\n" +
            "join booking b on h.id = b.home_id\n" +
            "join home_type ht on h.home_type_id = ht.id\n" +
            "join users u on h.user_id = u.id\n" +
            "left join homes_image hi on h.id = hi.homes_id\n" +
            "left join review r on h.id = r.homes_id\n" +
            "GROUP BY h.id, h.address, h.name, h.price_by_day, u.name ,ht.name, h.bedroom, h.bathroom, r.rating, h.status\n" +
            "ORDER BY bookingcount DESC LIMIT 4;")
    Iterable<IGetMostRentedBooking> getMostRentedBooking();

    Page<Booking> findByUsers_Id(Long id, Pageable pageable);
    Page<Booking> findByUsers_IdAndStatusBAndDone(Long id, boolean status, boolean done, Pageable pageable);

    @Query(nativeQuery = true, value = "Select * from booking where home_id = :home_id")
    Iterable<Booking> getAllBookingsIdByHomeId(@Param("home_id") Long id);

//    @Query(nativeQuery = true, value = "select * from booking where status = true and user_id = :id")
//    Page<Booking> getBookingsByUser_Id(@Param("id") Long id, Pageable pageable);
@Query(nativeQuery = true, value = "select * from booking b where b.user_id = :id and b.statusb = true and b.is_done = false")
List<Booking> getAllBookingByUserIdAndStatusAndDone(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM booking b JOIN homes ON b.home_id = homes.id JOIN users ON homes.user_id = users.id WHERE users.id = :id AND b.statusb = true AND b.is_done = false;")
    List<Booking> getUncheckedBooking(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM booking b JOIN homes ON b.home_id = homes.id JOIN users ON homes.user_id = users.id WHERE users.id = :id AND b.statusb = true AND b.is_done = true;")
    List<Booking> getCheckedBooking(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM booking b JOIN homes ON b.home_id = homes.id JOIN users ON homes.user_id = users.id WHERE users.id = :id AND b.statusb = false;")
    List<Booking> getCancelRequest(@Param("id") Long id);
    @Query(nativeQuery = true, value = "select * from booking b where b.user_id = :id and b.statusb = false")
    List<Booking> getAllBookingByUserIdAndStatusFalse(@Param("id") Long id);
    @Query(nativeQuery = true, value = "select * from booking where user_id = :id and is_done = true and is_checkoutb = true;")
    List<Booking> getBookingByUserIddAndStatusTrue(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select * from booking where user_id = :user_id and home_id = :home_id limit 1")
    Optional<Booking> getFirstByUsersIdAndHomeId(@Param("user_id") Long user_id, @Param("home_id") Long home_id);

}
