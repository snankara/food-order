package io.snankara.github.food.order.kafka.producer.service.impl;

import io.snankara.github.food.order.kafka.producer.exception.KafkaProducerException;
import io.snankara.github.food.order.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;
import java.io.Serializable;

@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, K key, V message, ListenableFutureCallback<SendResult<K, V>> callback) {
        log.info("send topic: {}, key: {}, message: {}", topic, key, message);
        try {
            ListenableFuture<SendResult<K, V>> listenableFuture = kafkaTemplate.send(topic, message);
            listenableFuture.addCallback(callback);
        } catch (KafkaException e) {
            log.error("Error on kafka producer with key: {}, message: {} and exception: {}", key, message, e.getMessage());
            throw new KafkaProducerException("Error on kafka producer with key: " + key + ", message: " + message);
        }
    }

    @PreDestroy
    public void close(){
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer.");
            kafkaTemplate.destroy();
        }
    }
}
