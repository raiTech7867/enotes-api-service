package com.raiTech.service.add;

import com.raiTech.dto.PasswordChangeRequest;
import com.raiTech.dto.PasswordResetRequest;
import com.raiTech.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    public void changePassword(PasswordChangeRequest passwordChangeRequest);

    void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception;

    void verifyPswdResetLink(Integer uid, String code) throws Exception;

    void resetPassword(PasswordResetRequest passwordChangeRequest) throws Exception;
}
