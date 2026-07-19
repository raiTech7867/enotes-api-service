package com.raiTech.exception;
import com.raiTech.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
      //  return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception e) {
      //  return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception e) {

       // return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e) {
       // return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        return CommonUtil.createErrorResponse(e.getError(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SuccessException.class)
    public ResponseEntity<?> handleSuccessExceptionException(SuccessException e) {
        // return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        return CommonUtil.createBuildResponse(e.getMessage(), HttpStatus.OK);
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e) {
        // return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleIBadCredentialsException(BadCredentialsException e) {
        return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
