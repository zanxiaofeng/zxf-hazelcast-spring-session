package com.zxf.hazelcast.springsession.service;

import com.zxf.hazelcast.springsession.cache.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CacheService {
    @Caching(keyTemplate = "by-cache_#{id}-#{name}", ttl = 120)
    public String byCache(String id, String name) {
        return String.format("%s-%s-%s", id, name, LocalDateTime.now());
    }
}
