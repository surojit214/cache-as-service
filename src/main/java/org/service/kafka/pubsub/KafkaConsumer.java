package org.service.kafka.pubsub;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.service.cache.service.CacheService;
import org.service.cache.data.CacheData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    CacheService cacheService;

    @Value("${server.leader}")
    boolean isLeader;

    @KafkaListener(topics = "cache_test")
    public void consume(ConsumerRecord<String, CacheData> data){
        logger.info(String.format("Consumed Message -> %s, %s",data.key(), data.value()));
        if( !isLeader ) {
            cacheService.populateCache(data.key(), data.value());
        }
    }

}
