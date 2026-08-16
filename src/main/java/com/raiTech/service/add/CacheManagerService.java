package com.raiTech.service.add;

import org.springframework.cache.Cache;

import java.util.Collection;
import java.util.List;

public interface CacheManagerService {

    public Collection<String> getCache();

    public Cache getCacheName(String cache_name);

    public void removeAllCache();

    public void removeCacheNByName(List<String> cacheNames);


}
