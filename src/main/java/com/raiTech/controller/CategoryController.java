package com.raiTech.controller;

import com.raiTech.dto.CategoryDto;
import com.raiTech.dto.CategoryResponse;
import com.raiTech.entity.Category;
import com.raiTech.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
   @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?>saveCategory(@RequestBody CategoryDto categorydto) {

        Boolean saveCategory = categoryService.saveCategory(categorydto);
        if (saveCategory) {
            return new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Save Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<?>getAllCategory() {

        List<Category> allCategory = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(allCategory)){
            return ResponseEntity.noContent().build();
        }else {
            return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/active-category")
    public ResponseEntity<?>getActiveCategory() {

        List<CategoryResponse> activeCategory = categoryService.getActiveCategory();
        if (CollectionUtils.isEmpty(activeCategory)){
            return ResponseEntity.noContent().build();
        }else {
            return new ResponseEntity<>(activeCategory, HttpStatus.OK);
        }
    }

}
