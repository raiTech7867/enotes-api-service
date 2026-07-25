package com.raiTech.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Caching",description = "All the Caching APIs")
@RequestMapping("/api/v1/cache")
public interface CacheEndPoint {

    @GetMapping("/")
    public ResponseEntity<?> getAllCache();

    @GetMapping("/{cache_name}")
    public ResponseEntity<?> getCache(@PathVariable String cache_name);

    public ResponseEntity<?>removeAllCache();
}
