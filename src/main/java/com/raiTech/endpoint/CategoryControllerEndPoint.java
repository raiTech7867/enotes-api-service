package com.raiTech.endpoint;

import com.raiTech.dto.CategoryDto;
import com.raiTech.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/category")
public interface CategoryControllerEndPoint {

    @PostMapping("/save")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categorydto);

    @GetMapping("/")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getAllCategory();

    @GetMapping("/active")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getActiveCategory();


    @GetMapping("/{id}")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception;

    @DeleteMapping("/{id}")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> deleteCATEGORYById(@PathVariable Integer id);



}
