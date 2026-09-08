package com.raiTech.exception;

import java.util.Map;

public class ValidationException extends RuntimeException {

    private Map<String, Object> error;
    public ValidationException(Map<String, Object> errors) {
        super("Validation Failed");
        this.error = errors;
    }

    public Map<String, Object> getError() {
        return error;
    }

}
