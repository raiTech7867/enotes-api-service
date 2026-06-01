package com.raiTech.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.raiTech.dto.CategoryDto;
import com.raiTech.dto.CategoryResponse;
import com.raiTech.exception.ResourceNotFoundException;
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

        if (ObjectUtils.isEmpty(category.getId())) {
            category.setIsDeleted(false);
            category.setCreatedBy(1);
            category.setCreatedOn(new Date());
        }else {
            updateCategory(category);
        }

        Category saveCategory=categoryRepo.save(category);
		if(ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

    private void updateCategory(Category category) {

        Optional<Category> findById=categoryRepo.findById(category.getId());
        if (findById.isPresent()) {
            Category existCategory=findById.get();
            category.setCreatedBy(existCategory.getCreatedBy());
            category.setCreatedOn(existCategory.getCreatedOn());
            category.setIsDeleted(existCategory.getIsDeleted());
            category.setUpdatedBy(1);
            category.setUpdatedOn(new Date());
        }

    }

    @Override
	public List<Category> getAllCategory() {
		List<Category> categories=categoryRepo.findByIsDeletedFalse();
		return categories;
	}

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category>categories=categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse>categoryList=categories.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();

        return categoryList;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws Exception{
       Category category=categoryRepo.findByIdAndIsDeletedFalse(id).orElseThrow(()->new ResourceNotFoundException("Category not found with id"+id));
        if(!ObjectUtils.isEmpty(category)) {
            return mapper.map(category, CategoryDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Optional<Category> findByCategory=categoryRepo.findById(id);
        if(findByCategory.isPresent()) {
            Category category=findByCategory.get();
            category.setIsDeleted(true);
            categoryRepo.save(category);
            return true;
        }

        return false;
    }

}
