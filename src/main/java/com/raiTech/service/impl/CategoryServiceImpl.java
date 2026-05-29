package com.raiTech.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.raiTech.dto.CategoryDto;
import com.raiTech.dto.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.raiTech.entity.Category;
import com.raiTech.repository.Categoryrepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private Categoryrepository categoryRepo;

    @Autowired
    private ModelMapper mapper;

	@Override
	public Boolean saveCategory(CategoryDto categorydto) {


//       Category category=new Category();
//       category.setName(categorydto.getName());
//       category.setDescription(categorydto.getDescription());
//       category.setIsActive(categorydto.getIsActive());
        Category category=mapper.map(categorydto, Category.class);
       category.setIsDeleted(false);
       category.setCreatedBy(1);
       category.setCreatedOn(new Date());
        Category saveCategory=categoryRepo.save(category);
		if(ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	@Override
	public List<Category> getAllCategory() {
		List<Category> categories=categoryRepo.findAll();
		return categories;
	}

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category>categories=categoryRepo.findByIsActiveTrue();
        List<CategoryResponse>categoryList=categories.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();

        return categoryList;
    }

}
