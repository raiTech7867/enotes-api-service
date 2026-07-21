package com.raiTech.controller;


import com.raiTech.dto.PasswordChangeRequest;
import com.raiTech.dto.PasswordResetRequest;
import com.raiTech.service.add.HomeService;
import com.raiTech.service.add.UserService;
import com.raiTech.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<?>verifyUserAccount(@RequestParam Integer id,@RequestParam String vc) throws Exception {

        Boolean verifyAccount=homeService.verifyUserAccount(id,vc);
        if(verifyAccount){
            return CommonUtil.createBuildResponseMessage("Account verification Success", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/send-email-reset")
    public ResponseEntity<?>sendEmailForPasswordReset(@RequestParam String email,HttpServletRequest request) throws Exception {
        userService.sendEmailPasswordReset(email, request);
        return CommonUtil.createBuildResponseMessage("Email Send Success !! Check Email Reset Password", HttpStatus.OK);
    }


    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?>verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception {

        userService.verifyPswdResetLink(uid,code);
        return CommonUtil.createBuildResponseMessage("Password Reset Link Success", HttpStatus.OK);

    }

    @PostMapping("/reset-pswd")
    public ResponseEntity<?>resetPassword(@RequestBody PasswordResetRequest passwordChangeRequest) throws Exception {
        userService.resetPassword(passwordChangeRequest);
        return CommonUtil.createBuildResponseMessage("Password Reset Success", HttpStatus.OK);


    }



}
