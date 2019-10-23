package org.service.cache.api;

import org.service.cache.data.CacheData;
import org.service.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CacheAPI {
    @Autowired
    CacheService cacheService;

    @GetMapping("/cache/{key}")
    public String getData(@PathVariable String key){
        CacheData cachedObject = cacheService.getCachedObject(key);
        return cachedObject!= null ? cachedObject.toString(): "not found";
    }

    @PostMapping("/cache")
    public void postData(@RequestBody Map<String, String> body){
        String key = body.get("Key");
        String value = body.get("Value");

        CacheData cacheData = new CacheData();
        cacheData.setResult(value);

        cacheService.populateCacheIfLeader(key, cacheData);
    }

    @DeleteMapping("/cache/{key}")
    public String deleteData(@PathVariable String key){
        cacheService.deleteKey(key);
        CacheData cachedObject = cacheService.getCachedObject(key);
        return cachedObject!= null ? cachedObject.toString(): "not found";
    }
}
