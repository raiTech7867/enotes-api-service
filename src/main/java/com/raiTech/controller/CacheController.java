package com.raiTech.controller;

import com.raiTech.endpoint.CacheEndPoint;
import com.raiTech.service.add.CacheManagerService;
import com.raiTech.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CacheController implements CacheEndPoint {

    @Autowired
    private CacheManagerService cacheManagerService;

    @Override
    public ResponseEntity<?> getAllCache() {
        Collection<String> cacheNames=cacheManagerService.getCache();
        return CommonUtil.createBuildResponse(cacheNames, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCache(String cache_name) {
        Cache cacheName=cacheManagerService.getCacheName(cache_name);
        return CommonUtil.createBuildResponse(cacheName, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> removeAllCache() {
        cacheManagerService.removeAllCache();
        return CommonUtil.createBuildResponseMessage("Removed All Cache", HttpStatus.OK);
    }
}
