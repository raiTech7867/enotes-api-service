package com.raiTech.repository;

import com.raiTech.dto.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.raiTech.entity.Category;

import java.util.List;

public interface Categoryrepository extends JpaRepository<Category,Integer>{


    List<Category> findByIsActiveTrue();
}
