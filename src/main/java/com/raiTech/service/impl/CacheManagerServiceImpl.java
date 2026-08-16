package com.raiTech.service.impl;


import com.raiTech.service.add.CacheManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class CacheManagerServiceImpl implements CacheManagerService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Collection<String> getCache(){
        Collection<String>cacheNames=cacheManager.getCacheNames();
        for(String cacheName:cacheNames){
            Cache cache=cacheManager.getCache(cacheName);
            log.info("Cache name: {}",cache);
        }
        return cacheNames;
    }

    @Override
    public Cache getCacheName(String cacheName) {
        Cache cache=cacheManager.getCache(cacheName);
        log.info("Cache name: {}",cache);
        return cache;
    }

    @Override
    public void removeAllCache() {
        Collection<String>cacheNames=cacheManager.getCacheNames();
        for(String cacheName:cacheNames){
            Cache cache=cacheManager.getCache(cacheName);
            log.info("Cache name: {}",cache);
            cache.clear();
        }
    }

    @Override
    public void removeCacheNByName(List<String> cacheNames) {
        for(String cacheName:cacheNames){
            Cache cache=cacheManager.getCache(cacheName);
            log.info("Cache name: {}",cache);
            cache.clear();
        }
    }
}
