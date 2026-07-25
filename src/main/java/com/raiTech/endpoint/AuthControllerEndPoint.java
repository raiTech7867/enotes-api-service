package com.raiTech.endpoint;

import com.raiTech.dto.LoginRequest;
import com.raiTech.dto.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Authentication",description = "All the user Authentication Api")
@RequestMapping("api/v1/auth")
public interface AuthControllerEndPoint {

    @ApiResponses(value = {@ApiResponse(responseCode = "201",description = "Register Success"),
    @ApiResponse(responseCode = "500",description = "Internal Server Error")
    })
    @Operation(summary = "User Register Endpoint",tags = {"Authentication"})
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto, HttpServletRequest request) throws Exception;

    @Operation(summary = "User Login Endpoint",tags = {"Authentication"})
    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest loginRequest) throws Exception;
}
