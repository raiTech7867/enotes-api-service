package com.raiTech.endpoint;

import com.raiTech.dto.PasswordResetRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/home")
public interface HomeControllerEndPoint {

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer id, @RequestParam String vc) throws Exception;

    @GetMapping("/send-email-reset")
    public ResponseEntity<?>sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception;

    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?>verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception;

    @PostMapping("/reset-pswd")
    public ResponseEntity<?>resetPassword(@RequestBody PasswordResetRequest passwordChangeRequest) throws Exception;


}
