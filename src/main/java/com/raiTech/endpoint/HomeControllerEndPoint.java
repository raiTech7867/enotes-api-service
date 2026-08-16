package com.raiTech.endpoint;

import com.raiTech.dto.PasswordResetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "HomeApi",description = "All the Home Api")
@RequestMapping("/api/v1/home")
public interface HomeControllerEndPoint {

    @Operation(summary = "Varify User",tags = {"HomeApi"},description = "Verify User Account Api")
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer id, @RequestParam String vc) throws Exception;

    @Operation(summary = "PasswordResetApi",tags = {"HomeApi"},description = "Send email to reset password")
    @GetMapping("/send-email-reset")
    public ResponseEntity<?>sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception;

    @Operation(summary = "VerifypasswordApi",tags = {"HomeApi"},description = "Verify Password Api")
    @GetMapping("/verify-pswd-link")
    public ResponseEntity<?>verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception;

    @Operation(summary = "ResetPasswordApi",tags = {"HomeApi"},description = "Reset Password Api")
    @PostMapping("/reset-pswd")
    public ResponseEntity<?>resetPassword(@RequestBody PasswordResetRequest passwordChangeRequest) throws Exception;


}
