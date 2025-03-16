package io.snankara.github.food.order.kafka.consumer;

import org.apache.avro.specific.SpecificRecordBase;

import java.util.List;

public interface KafkaConsumer<T extends SpecificRecordBase> {
    void receive(List<String> messages, List<Long> keys, List<Integer> partitions, List<Long> offsets);
}
