package com.raiTech.service.impl;


import com.raiTech.dto.UserDto;
import com.raiTech.entity.Role;
import com.raiTech.entity.User;
import com.raiTech.repository.RoleRepository;
import com.raiTech.repository.UserRepository;
import com.raiTech.service.add.UserService;
import com.raiTech.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Boolean register(UserDto userDto) {

        validation.userValidation(userDto);
        User user= modelMapper.map(userDto,User.class);
        setRole(userDto,user);
        User saveUser= userRepository.save(user);
        if (ObjectUtils.isEmpty(saveUser)) {
            return false;
        }
        return true;
    }

    private void setRole(UserDto userDto, User user) {

        List<Integer> regRoleId=userDto.getRoles().stream().map(r->r.getId()).toList();
       List<Role>roles =roleRepository.findAllById(regRoleId);
       user.setRoles(roles);
    }
}
