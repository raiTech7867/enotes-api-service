package com.raiTech.repository;

import com.raiTech.dto.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.raiTech.entity.Category;

import java.util.List;
import java.util.Optional;

public interface Categoryrepository extends JpaRepository<Category,Integer>{


    List<Category> findByIsActiveTrue();

    Optional<Category> findByIdAndIsDeletedFalse(Integer id);

    List<Category> findByIsDeletedFalse();

    List<Category> findByIsActiveTrueAndIsDeletedFalse();

    Boolean existsByName(String trim);
}
