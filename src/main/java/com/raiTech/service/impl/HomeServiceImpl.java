package com.raiTech.service.impl;

import com.raiTech.entity.AccountStatus;
import com.raiTech.entity.User;
import com.raiTech.exception.ResourceNotFoundException;
import com.raiTech.exception.SuccessException;
import com.raiTech.repository.UserRepository;
import com.raiTech.service.add.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean verifyUserAccount(Integer userId, String verificationCode) throws Exception{
       log.info("HomeController:verifyUserAccount()");
       User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("Invalid User"));

       if (user.getStatus().getVerificationCode()==null){
           throw new SuccessException("Account Already Verified");
       }

       if (user.getStatus().getVerificationCode().equals(verificationCode)){
           AccountStatus status=user.getStatus();
           status.setIsActive(true);
           status.setVerificationCode(null);
           userRepository.save(user);
           return true;
       }
        return false;
    }
}
