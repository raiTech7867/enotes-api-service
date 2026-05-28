package com.raiTech.controller;

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
    public ResponseEntity<?>saveCategory(@RequestBody Category category) {

        Boolean saveCategory = categoryService.saveCategory(category);
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

}
