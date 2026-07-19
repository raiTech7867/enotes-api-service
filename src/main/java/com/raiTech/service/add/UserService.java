package com.raiTech.service.add;

import com.raiTech.dto.LoginRequest;
import com.raiTech.dto.LoginResponse;
import com.raiTech.dto.UserDto;

public interface UserService {

    public Boolean register(UserDto userDto, String url)  throws Exception;

    public LoginResponse login(LoginRequest loginRequest);
}
