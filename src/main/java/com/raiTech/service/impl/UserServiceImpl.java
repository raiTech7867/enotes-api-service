package com.raiTech.service.impl;


import com.raiTech.dto.EmailRequest;
import com.raiTech.dto.UserDto;
import com.raiTech.entity.AccountStatus;
import com.raiTech.entity.Role;
import com.raiTech.entity.User;
import com.raiTech.repository.RoleRepository;
import com.raiTech.repository.UserRepository;
import com.raiTech.service.add.UserService;
import com.raiTech.service.add.EmailService;
import com.raiTech.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Override
    public Boolean register(UserDto userDto, String url) throws Exception{

        validation.userValidation(userDto);
        User user= modelMapper.map(userDto,User.class);
        setRole(userDto,user);
        AccountStatus status=AccountStatus.builder()
                        .isActive(false)
                .verificationCode(UUID.randomUUID().toString())
                .build();
        user.setStatus(status);
        User saveUser= userRepository.save(user);
        if (!ObjectUtils.isEmpty(saveUser)) {
            //Send Email
            emailSend(saveUser,url);
            return true;
        }
        return false;
    }

    private void emailSend(User saveUser, String url) throws Exception {
        String verificationLink = url+"/api/v1/home/verify?id="
                + saveUser.getId()
                + "&vc="
                + saveUser.getStatus().getVerificationCode();
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
            <td style="background:#4CAF50; color:white; padding:20px; text-align:center;">
                <h2 style="margin:0;">Welcome to Enotes</h2>
            </td>
        </tr>

        <tr>
            <td style="padding:30px;">

                <p style="font-size:16px;">
                    Hi <strong>%s</strong>,
                </p>

                <p>
                    Your account has been successfully registered.
                </p>

                <p>
                    Please click the button below to verify your email address.
                </p>

                <div style="text-align:center; margin:35px 0;">
                    <a href="%s"
                       style="
                           background:#4CAF50;
                           color:#ffffff;
                           text-decoration:none;
                           padding:14px 28px;
                           border-radius:6px;
                           display:inline-block;
                           font-weight:bold;
                           font-size:16px;">
                        Verify Account
                    </a>
                </div>

                <p>
                    If the button doesn't work, copy and paste the following link into your browser:
                </p>

                <p style="word-break:break-all;">
                    <a href="%s">%s</a>
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
                verificationLink,
                verificationLink,
                verificationLink
        );

        EmailRequest emailRequest = EmailRequest.builder()
                .to(saveUser.getEmail())
                .title("Account Creation Confirmation")
                .subject("Account Created Successfully")
                .message(message)
                .build();

        emailService.sendEmail(emailRequest);
    }

    private void setRole(UserDto userDto, User user) {

        List<Integer> regRoleId=userDto.getRoles().stream().map(r->r.getId()).toList();
       List<Role>roles =roleRepository.findAllById(regRoleId);
       user.setRoles(roles);
    }
}
