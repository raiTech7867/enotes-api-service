package com.raiTech.exception;
import com.raiTech.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("GlobalExceptionHandler :handleException() :{}", e.getMessage());
      //  return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        log.error("GlobalExceptionHandler :handleAccessDeniedException() :{}", e.getMessage());
        //  return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(Exception e) {
        log.error("GlobalExceptionHandler :handleNullPointerException() :{}", e.getMessage());
      //  return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception e) {
        log.error("GlobalExceptionHandler :handleResourceNotFoundException() :{}", e.getMessage());

       // return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e) {
        log.error("GlobalExceptionHandler :handleValidationException() :{}", e.getMessage());
       // return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        return CommonUtil.createErrorResponse(e.getError(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SuccessException.class)
    public ResponseEntity<?> handleSuccessExceptionException(SuccessException e) {
        log.error("GlobalExceptionHandler :handleSuccessException() :{}", e.getMessage());
        // return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.OK);
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e) {
        log.error("GlobalExceptionHandler :handleFileNotFoundException() :{}", e.getMessage());
        // return new ResponseEntity<>(e.getError(),HttpStatus.BAD_REQUEST);
        return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("GlobalExceptionHandler :handleIllegalArgumentException() :{}", e.getMessage());
        return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleIBadCredentialsException(BadCredentialsException e) {
        log.error("GlobalExceptionHandler :handleBadCredentialsException() :{}", e.getMessage());
        return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
