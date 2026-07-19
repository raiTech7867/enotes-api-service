package com.raiTech.service.add;

import com.raiTech.dto.LoginRequest;
import com.raiTech.dto.LoginResponse;
import com.raiTech.dto.UserRequest;

public interface AuthService {

    public Boolean register(UserRequest userDto, String url)  throws Exception;

    public LoginResponse login(LoginRequest loginRequest);
}
