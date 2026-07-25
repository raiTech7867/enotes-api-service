package com.raiTech.endpoint;

import com.raiTech.dto.PasswordChangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "UserApi",description = "All the User Api")
@RequestMapping("/api/v1/user")
public interface UserControllerEndPoint {


    @Operation(summary = "Get Profile ",tags = {"UserApi"},description = "Get profile Api")
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile();

    @Operation(summary = "ChangePassword",tags = {"UserApi"},description = "Get Change Password  Api")
    @PostMapping("/chng-pswd")
    public ResponseEntity<?> changePassword( PasswordChangeRequest passwordChangeRequest);


}
