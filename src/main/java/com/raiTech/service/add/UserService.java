package com.raiTech.service.add;

import com.raiTech.dto.UserDto;

public interface UserService {

    public Boolean register(UserDto userDto, String url)  throws Exception;

}
