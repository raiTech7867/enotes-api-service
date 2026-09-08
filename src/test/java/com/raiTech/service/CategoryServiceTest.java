package com.raiTech.service;

import com.raiTech.dto.CategoryDto;
import com.raiTech.entity.Category;
import com.raiTech.repository.Categoryrepository;
import com.raiTech.service.add.CategoryService;
import com.raiTech.service.impl.CategoryServiceImpl;
import com.raiTech.util.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private Categoryrepository categoryRepository;


    @Mock
    private ModelMapper mapper;

    @Mock
    private Validation validation;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryDto categoryDto;

    private Category category;



    @BeforeEach
    public void initialize() {

        categoryDto =  CategoryDto.builder().id(null).name("Test Notes").description("Notes for testing").isActive(true).build();
        category=mapper.map(categoryDto, Category.class);

    }

    @Test
    public void testSaveCategory() {

        //Arrange

        when( categoryRepository.existsByName(category.getName())).thenReturn(true);
        when(mapper.map(categoryDto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        //Act

        Boolean result = categoryService.saveCategory(categoryDto);

        //Assert
        Assertions.assertTrue(result);

        //Verify
        Mockito.verify(validation).categoryValidation(categoryDto);
        Mockito.verify(categoryRepository).existsByName(categoryDto.getName());
        Mockito.verify(categoryRepository).save(category);
    }

}
