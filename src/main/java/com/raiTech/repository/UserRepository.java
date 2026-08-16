package com.raiTech.repository;


import com.raiTech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {


    Boolean existsByEmail(String email);

    User findByEmail(String email);
}
