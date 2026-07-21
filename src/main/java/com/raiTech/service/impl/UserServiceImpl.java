package com.raiTech.service.impl;

import com.raiTech.dto.EmailRequest;
import com.raiTech.dto.PasswordChangeRequest;
import com.raiTech.dto.PasswordResetRequest;
import com.raiTech.entity.User;
import com.raiTech.exception.ResourceNotFoundException;
import com.raiTech.repository.UserRepository;
import com.raiTech.service.add.EmailService;
import com.raiTech.service.add.UserService;
import com.raiTech.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.module.ResolutionException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {

        User loggedInUser= CommonUtil.getLoggedInUser();
        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword())) {
            throw new IllegalArgumentException("Old password doesn't match!!");
        }
        String encodedPassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
        loggedInUser.setPassword(encodedPassword);
        userRepository.save(loggedInUser);

    }

    @Override
    public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception {
       User user = userRepository.findByEmail(email);
       if (ObjectUtils.isEmpty(user)) {
           throw new ResourceNotFoundException("invalid Email");
       }
       //Generate Unique password reset token
        String passwordResetToken= UUID.randomUUID().toString();
       user.getStatus().setPasswordResetToken(passwordResetToken);
       User updatedUser = userRepository.save(user);

       String url=CommonUtil.getUrl(request);
       EmailRequest emailRequest = emailSendForPasswordReset(updatedUser,url);
       emailService.sendEmail(emailRequest);

    }

    @Override
    public void verifyPswdResetLink(Integer uid, String code) throws Exception{

        User user=userRepository.findById(uid).orElseThrow(()-> new ResourceNotFoundException("Invalid User!"));
        verifyPasswordResetCode(user.getStatus().getPasswordResetToken(),code);

    }

    @Override
    public void resetPassword(PasswordResetRequest passwordChangeRequest) throws Exception{

          User user=userRepository.findById(passwordChangeRequest.getUId()).orElseThrow(()->
                  new ResourceNotFoundException("Invalid Link"));
          String encodedPassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
          user.setPassword(encodedPassword);
          user.getStatus().setPasswordResetToken(null);
          userRepository.save(user);

    }

    private void verifyPasswordResetCode(String existToken, String reqToken) {

        //request token not null
        if (StringUtils.hasText(reqToken)){

            //Password Already Reset
            if (!StringUtils.hasText(existToken)){
                throw new IllegalArgumentException("Already Password Reset!");
            }
            //User Request Token changes
            if (!existToken.equals(reqToken)){
                throw new IllegalArgumentException("Invalid url!");
            }
        }else {
            throw new IllegalArgumentException("Invalid token!");
        }


    }

    private EmailRequest emailSendForPasswordReset(User saveUser, String url) throws Exception {

        String resetLink = url + "/api/v1/home/verify-pswd-link?uid="
                + saveUser.getId()
                + "&code="
                + saveUser.getStatus().getPasswordResetToken();

        String message = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
        </head>
        <body style="font-family: Arial, Helvetica, sans-serif; background:#f5f5f5; margin:0; padding:30px;">
            <table width="600" align="center" cellpadding="0" cellspacing="0"
                   style="background:#ffffff; border-radius:10px; overflow:hidden;
                          box-shadow:0 2px 8px rgba(0,0,0,.1);">

                <tr>
                    <td style="background:#f39c12; color:white; padding:20px; text-align:center;">
                        <h2 style="margin:0;">Password Reset Request</h2>
                    </td>
                </tr>

                <tr>
                    <td style="padding:30px;">

                        <p style="font-size:16px;">
                            Hi <strong>%s</strong>,
                        </p>

                        <p>
                            We received a request to reset the password for your Enotes account.
                        </p>

                        <p>
                            Please click the button below to create a new password.
                        </p>

                        <div style="text-align:center; margin:35px 0;">
                            <a href="%s"
                               style="
                                   background:#f39c12;
                                   color:#ffffff;
                                   text-decoration:none;
                                   padding:14px 28px;
                                   border-radius:6px;
                                   display:inline-block;
                                   font-weight:bold;
                                   font-size:16px;">
                                Reset Password
                            </a>
                        </div>

                        <p>
                            If the button doesn't work, copy and paste the following link into your browser:
                        </p>

                        <p style="word-break:break-all;">
                            <a href="%s">%s</a>
                        </p>

                        <p style="color:#666;">
                            If you didn't request this password reset, you can safely ignore this email. Your password will remain unchanged.
                        </p>

                        <hr>

                        <p style="color:#666;">
                            Thanks,<br>
                            <strong>Enotes Team</strong>
                        </p>

                    </td>
                </tr>

            </table>
        </body>
        </html>
        """.formatted(
                saveUser.getFirstName(),
                resetLink,
                resetLink,
                resetLink
        );

        EmailRequest emailRequest = EmailRequest.builder()
                .to(saveUser.getEmail())
                .title("Password Reset Request")
                .subject("Reset Your Password")
                .message(message)
                .build();

        return emailRequest;
    }
}
