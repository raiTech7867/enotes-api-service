package com.raiTech.service.add;

import com.raiTech.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    public String generateToken(User user);

    public String extractUserName(String token);

    public Boolean validateToken(String token, UserDetails userDetails);

}
