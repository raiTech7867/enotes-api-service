package com.raiTech.util;

import com.raiTech.dto.CategoryDto;
import com.raiTech.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class Validation {

    public void categoryValidation(CategoryDto categorydto) {

        Map<String, Object> error = new LinkedHashMap<>();
        if (ObjectUtils.isEmpty(categorydto)) {
            throw new IllegalArgumentException("Category Object/JSON shouldn't be null or empty");
        }else {
            //Validation name field
            if (ObjectUtils.isEmpty(categorydto.getName())) {
                error.put("name","name field is empty or null");
            }else {
                if (categorydto.getName().length() < 10) {
                    error.put("name","name length should be at least 10 characters");
                }
                if (categorydto.getName().length() > 100) {
                    error.put("name","name length should be at most 100 characters");
                }
            }

            //validation description
            if (ObjectUtils.isEmpty(categorydto.getDescription())) {
                error.put("description","description field is empty or null");
            }

            //Validation Is Active
            if (ObjectUtils.isEmpty(categorydto.getIsActive())) {
                error.put("isActive","isActive field is empty or null");
            }else {
                if (categorydto.getIsActive()!=Boolean.TRUE.booleanValue() &&categorydto.getIsActive()!=Boolean.FALSE.booleanValue()) {
                    error.put("isActive","invalid value isActive field");
                }
            }
        }

        if (!error.isEmpty()){
            throw new ValidationException(error);
        }

    }

}
