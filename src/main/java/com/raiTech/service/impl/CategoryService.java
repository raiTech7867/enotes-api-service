package com.raiTech.service.impl;

import java.util.List;

import com.raiTech.entity.Category;

public interface CategoryService {
	
	public Boolean saveCategory(Category category);
	public List<Category>getAllCategory();
	

}
