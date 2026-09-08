package com.raiTech.controller;


import com.raiTech.dto.PasswordChangeRequest;
import com.raiTech.dto.PasswordResetRequest;
import com.raiTech.endpoint.HomeControllerEndPoint;
import com.raiTech.service.add.HomeService;
import com.raiTech.service.add.UserService;
import com.raiTech.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@RestController
public class HomeController implements HomeControllerEndPoint {

    Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?>verifyUserAccount( Integer id, String vc) throws Exception {
          log.info("HomeController:verifyUserAccount():Execution Started");
        Boolean verifyAccount=homeService.verifyUserAccount(id,vc);
        if(verifyAccount){
            return CommonUtil.createBuildResponseMessage("Account verification Success", HttpStatus.OK);
        }
        log.info("HomeController:verifyUserAccount():Execution Ended");
        return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<?>sendEmailForPasswordReset(String email,HttpServletRequest request) throws Exception {
        userService.sendEmailPasswordReset(email, request);
        return CommonUtil.createBuildResponseMessage("Email Send Success !! Check Email Reset Password", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?>verifyPasswordResetLink(Integer uid,String code) throws Exception {

        userService.verifyPswdResetLink(uid,code);
        return CommonUtil.createBuildResponseMessage("Password Reset Link Success", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?>resetPassword( PasswordResetRequest passwordChangeRequest) throws Exception {
        userService.resetPassword(passwordChangeRequest);
        return CommonUtil.createBuildResponseMessage("Password Reset Success", HttpStatus.OK);


    }



}
