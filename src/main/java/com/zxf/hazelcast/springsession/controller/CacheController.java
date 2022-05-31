package com.zxf.hazelcast.springsession.controller;

import com.zxf.hazelcast.springsession.cache.Caching;
import com.zxf.hazelcast.springsession.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/caches")
public class CacheController {
    @Autowired
    private CacheService cacheService;

    @GetMapping("/one")
    public String byCache(@RequestParam String id, @RequestParam String name) {
        return cacheService.byCache(id, name);
    }
}
