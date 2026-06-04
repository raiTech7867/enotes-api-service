package com.raiTech.util;

import com.raiTech.handler.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtil {

    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status).status("Success").message("message").data(data).build();
        return response.create();
    }

    public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status).status("Success").message(message).build();
        return response.create();
    }

    public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status) {

        GenericResponse response = GenericResponse.builder()
                .responseStatus(status).status("Error").message("Failed").build();
        return response.create();
    }

    public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status) {
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status).status("Error").message(message).build();
        return response.create();
    }
}
