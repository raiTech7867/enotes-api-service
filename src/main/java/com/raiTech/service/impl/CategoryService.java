package com.raiTech.service.impl;

import java.util.List;

import com.raiTech.dto.CategoryDto;
import com.raiTech.dto.CategoryResponse;
import com.raiTech.entity.Category;

public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categorydto);
	public List<Category>getAllCategory();


    List<CategoryResponse> getActiveCategory();

    CategoryDto getCategoryById(Integer id) throws Exception;

    Boolean deleteCategory(Integer id);
}
