package com.raiTech.util;

import com.raiTech.handler.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    public static  String getContentType(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        switch (extension) {
            case "pdf":
                return "application/pdf";
                case "text":
                    return "text/plain";
            case "png":
                return "image/png";
                case "jpeg":
                    return "image/jpeg";
            default:
                return "application/octet-stream";

        }
    }

    public static String getUrl(HttpServletRequest request) {
        String apiUrl=request.getRequestURL().toString();//http:localhost:8080/api/v1/auth
        apiUrl=apiUrl.replace(request.getServletPath(),"");//http:localhost:8080
        return apiUrl;
    }
}
