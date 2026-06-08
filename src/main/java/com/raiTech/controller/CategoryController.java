package com.raiTech.controller;

import com.raiTech.dto.CategoryDto;
import com.raiTech.dto.CategoryResponse;
import com.raiTech.entity.Category;
import com.raiTech.service.add.CategoryService;
import com.raiTech.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categorydto) {
        Boolean saveCategory = categoryService.saveCategory(categorydto);
        if (saveCategory) {
            return CommonUtil.createBuildResponseMessage("Category saved successfully", HttpStatus.CREATED);
           // return new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED);
        } else {
            return CommonUtil.createErrorResponseMessage("Failed to save category", HttpStatus.INTERNAL_SERVER_ERROR);
            //return new ResponseEntity<>("Save Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {

        List<Category> allCategory = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(allCategory)) {
            return ResponseEntity.noContent().build();
        } else {
            return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
            // return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory() {

        List<CategoryResponse> activeCategory = categoryService.getActiveCategory();
        if (CollectionUtils.isEmpty(activeCategory)) {
            return ResponseEntity.noContent().build();
        } else {
            return CommonUtil.createBuildResponse(activeCategory, HttpStatus.OK);
         //   return new ResponseEntity<>(activeCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception{
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if (ObjectUtils.isEmpty(categoryDto)) {
            return CommonUtil.createErrorResponse("Category Not Found with Id="+id, HttpStatus.NOT_FOUND);
           // return new ResponseEntity<>("Category Not Found with Id="+id, HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);
      //  return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCATEGORYById(@PathVariable Integer id) {

        Boolean IsDeleted=categoryService.deleteCategory(id);
        if (IsDeleted) {
            return CommonUtil.createBuildResponse("Deleted Successfully", HttpStatus.OK);
           // return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Delete Failed", HttpStatus.INTERNAL_SERVER_ERROR);
       // return new ResponseEntity<>("Delete Failed", HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
