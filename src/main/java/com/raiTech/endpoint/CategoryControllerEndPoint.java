package com.raiTech.endpoint;

import com.raiTech.dto.CategoryDto;
import com.raiTech.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CategoryApi",description = "All the Category Operation  Api")
@RequestMapping("/api/v1/category")
public interface CategoryControllerEndPoint {

    @Operation(summary = "Save Category ",tags = {"CategoryApi"},description = "Admin can save the category")
    @PostMapping("/save")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categorydto);

    @Operation(summary = "Get All Category Endpoint",tags = {"CategoryApi"},description = "Admin can get all category")
    @GetMapping("/")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getAllCategory();


    @Operation(summary = "Get Active Category Endpoint",tags = {"CategoryApi"},description = "Admin can get all active category")
    @GetMapping("/active")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getActiveCategory();

    @Operation(summary = "Get Category Endpoint",tags = {"CategoryApi"},description = "Admin can get category by id")
    @GetMapping("/{id}")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Get Delete Category  By Id Endpoint",tags = {"CategoryApi"},description = "Admin can delete category by id")
    @DeleteMapping("/{id}")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> deleteCATEGORYById(@PathVariable Integer id);



}
