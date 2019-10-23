package org.service.cache.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.service.cache.data.CacheData;
import org.service.kafka.pubsub.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@ComponentScan(basePackages = {"org.service.kafka"})
//@CacheConfig(cacheNames = {"generic_cache"})
public class CacheService {

    @Value("${server.leader}")
    boolean isLeader;

    private static final Logger LOG = LoggerFactory.getLogger(CacheService.class);


    @Autowired
    Cache<String, CacheData> cache;

    @Autowired
    KafkaProducer kafkaProducer;

    //@Cacheable
    public CacheData getCachedObject(String key){
        CacheData ifPresent = cache.getIfPresent(key);
        populateCacheIfLeader(key, ifPresent);
        return ifPresent;
    }

    public void populateCacheIfLeader(String key, CacheData data) {
        if(data != null && isLeader) {
            cache.put(key, data);
            kafkaProducer.sendMessage(key, data);
        }
    }

    public void populateCache(String key, CacheData data){
        cache.put(key, data);
    }

    public void deleteKey(String key){
        cache.invalidate(key);
    }
}
