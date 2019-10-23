package org.service.kafka.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.service.cache.data.CacheData;

public class CacheDataDeserializer implements Deserializer<CacheData> {
    @Override
    public CacheData deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        CacheData cacheData = null;
        try {
            cacheData = mapper.readValue(bytes, CacheData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheData;
    }
}
