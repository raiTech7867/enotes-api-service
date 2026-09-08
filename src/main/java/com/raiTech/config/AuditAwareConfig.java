package com.raiTech.config;

import com.raiTech.entity.User;
import com.raiTech.util.CommonUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        User currentUser = CommonUtil.getLoggedInUser();
        return Optional.of(currentUser.getId());
    }
}
