package com.raiTech.controller;


import com.raiTech.dto.PasswordChangeRequest;
import com.raiTech.dto.UserResponse;
import com.raiTech.entity.User;
import com.raiTech.service.add.UserService;
import com.raiTech.util.CommonUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        User currentUser = CommonUtil.getLoggedInUser();
        UserResponse userResponse = modelMapper.map(currentUser, UserResponse.class);
        return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
    }

    @PostMapping("/chng-pswd")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {

        userService.changePassword(passwordChangeRequest);
        return CommonUtil.createBuildResponseMessage("Password Changed Successfully", HttpStatus.OK);

    }
}
