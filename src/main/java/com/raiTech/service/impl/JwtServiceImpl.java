package com.raiTech.service.impl;

import com.raiTech.entity.User;
import com.raiTech.service.add.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    private String SECRET_KEY="";

    public JwtServiceImpl() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sK = keyGen.generateKey();
            SECRET_KEY= Base64.getEncoder().encodeToString(sK.getEncoded());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRoles());
        claims.put("status", user.getStatus().getIsActive());

        String token = Jwts.builder().claims().add(claims)
                .subject(user.getEmail()).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*60*10))
                .and()
                .signWith(getKey())
                .compact();
        return token;
    }

    @Override
    public String extractUserName(String token) {
        Claims claims=extractAllClaims(token);

        return claims.getSubject();
    }

    public String role(String token) {
        Claims claims=extractAllClaims(token);
        return claims.get("role").toString();
    }
    private Claims extractAllClaims(String token) {

      Claims claims= Jwts.parser().verifyWith(decrytKey(SECRET_KEY)).build().parseSignedClaims(token).getPayload();
      return claims;
    }

    private SecretKey decrytKey(String secretKey) {

        byte[]keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {

        String userName=extractUserName(token);
        Boolean isExpired= isTokenExpired(token);
        if (userName.equalsIgnoreCase(userDetails.getUsername())&&!isExpired) {
            return true;
        }

        return false;
    }

    private Boolean isTokenExpired(String token) {
        Claims claims=extractAllClaims(token);
        Date expiration=claims.getExpiration();
        return expiration.before(new Date());//10th Dec today-expire 11 dec
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}


