package com.raiTech.service.add;

import org.springframework.web.bind.annotation.RequestParam;

public interface HomeService {
    public Boolean verifyUserAccount(@RequestParam Integer id, @RequestParam String vc) throws Exception;
}
