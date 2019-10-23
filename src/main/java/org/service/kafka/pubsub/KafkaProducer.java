package org.service.kafka.pubsub;

import org.service.cache.data.CacheData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private static final String TOPIC = "cache_test";

    @Autowired
    private KafkaTemplate<String, CacheData> kafkaTemplate;

    public void sendMessage(String key, CacheData message){
        kafkaTemplate.send(TOPIC,key, message);
    }


}
