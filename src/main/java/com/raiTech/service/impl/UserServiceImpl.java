package com.raiTech.service.impl;

import com.raiTech.dto.PasswordChangeRequest;
import com.raiTech.entity.User;
import com.raiTech.repository.UserRepository;
import com.raiTech.service.add.UserService;
import com.raiTech.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {

        User loggedInUser= CommonUtil.getLoggedInUser();
        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword())) {
            throw new IllegalArgumentException("Old password doesn't match!!");
        }
        String encodedPassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
        loggedInUser.setPassword(encodedPassword);
        userRepository.save(loggedInUser);

    }
}
