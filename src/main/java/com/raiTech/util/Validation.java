package com.raiTech.util;

import com.raiTech.dto.CategoryDto;
import com.raiTech.dto.TodoDto;
import com.raiTech.dto.UserDto;
import com.raiTech.enums.TodoStatus;
import com.raiTech.exception.ExistDataException;
import com.raiTech.exception.ResourceNotFoundException;
import com.raiTech.exception.ValidationException;
import com.raiTech.repository.RoleRepository;
import com.raiTech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class Validation {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

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

    public void todoValidation(TodoDto tododto) throws Exception{
        TodoDto.StatusDto statusdto = tododto.getStatus();
        Boolean statusFound=false;
        for (TodoStatus st:TodoStatus.values()){
            if (st.getId().equals(statusdto.getId())){
                statusFound=true;
            }
        }
        if (!statusFound){
            throw new ResourceNotFoundException("Invalid Status");
        }
    }

    public void userValidation(UserDto userdto){

        if (!StringUtils.hasText(userdto.getFirstName())) {
            throw new IllegalArgumentException("First name field is empty");
        }
        if (!StringUtils.hasText(userdto.getLastName())) {
            throw new IllegalArgumentException("Last name field is empty");
        }
        if (!StringUtils.hasText(userdto.getEmail())&&!userdto.getEmail().matches(Constants.EMAIL_REGEX)) {
            throw new IllegalArgumentException("Email is Invalid");
        }else {
           Boolean isEmailExist= userRepository.existsByEmail(userdto.getEmail());
           if (isEmailExist) {
               throw new ExistDataException("Email already exists");
           }
        }
        if (!StringUtils.hasText(userdto.getPassword())) {
            throw new IllegalArgumentException("Password field is empty");
        }
        if (!StringUtils.hasText(userdto.getMobNumber())||!userdto.getMobNumber().matches(Constants.MOBILE_NUMBER_REGEX)) {
            throw new IllegalArgumentException("Mobile Number is empty");
        }
        if (CollectionUtils.isEmpty(userdto.getRoles())) {
            throw new IllegalArgumentException("Roles field is empty");
        }else {
            List<Integer>roleIds=roleRepository.findAll().stream().map(r->r.getId()).toList();
            List<Integer>invalidReqRoleIds=userdto.getRoles().stream().map(r->r.getId()).filter(roleId->!roleIds.contains(roleId)).toList();
            if (!CollectionUtils.isEmpty(invalidReqRoleIds)) {
                throw new IllegalArgumentException("Role Is Invalid="+invalidReqRoleIds);
            }
        }
    }

}
