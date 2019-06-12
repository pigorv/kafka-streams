package com.test.dictionaryservice;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

//todo  Make one config file endpoint with import of every else
@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value("${kafka.group.id}")
    private String baseGroupId;

    public ConsumerFactory<String, String> consumerFactory(String groupId) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> baseContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        //todo  change group id to some type of service processing name
        containerFactory.setConsumerFactory(consumerFactory(baseGroupId));
        return containerFactory;
    }
}
