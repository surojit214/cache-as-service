package org.service.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.service.cache.data.CacheData;
import org.service.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class CacheApplication {
    @Autowired
    CacheService cacheService;

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

    @Bean
    public @NonNull Cache<String, CacheData> getCache(){
        return Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(100).build();
    }
}
