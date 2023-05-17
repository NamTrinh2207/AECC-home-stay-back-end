package com.example.homestay.repository;

import com.example.homestay.model.DTO.ICountRole;
import com.example.homestay.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface IUserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String name);

    Optional<Users> findByVerificationToken(String verificationToken);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query(nativeQuery = true, value = "select r.name, count(users.username) as 'number' from users join user_role ur on users.id = ur.user_role join roles r on r.id = ur.role_id group by r.name;")
    Iterable<ICountRole> getRoleNumber();
}
