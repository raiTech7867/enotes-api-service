package com.raiTech.controller;


import com.raiTech.service.add.HomeService;
import com.raiTech.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/verify")
    public ResponseEntity<?>verifyUserAccount(@RequestParam Integer id,@RequestParam String vc) throws Exception {

        Boolean verifyAccount=homeService.verifyUserAccount(id,vc);
        if(verifyAccount){
            return CommonUtil.createBuildResponseMessage("Account verification Success", HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);

    }

}
