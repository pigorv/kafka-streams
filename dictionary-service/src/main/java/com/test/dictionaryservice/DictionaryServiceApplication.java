package com.test.dictionaryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DictionaryServiceApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(DictionaryServiceApplication.class, args);

        UserInfoRequest request = new UserInfoRequest();
        request.setUserId("Some User Id");

        MessageProducer producer = context.getBean(MessageProducer.class);
        producer.sendMessage(request);
    }

    @Bean
    public MessageProducer messageProducer() {
        return new MessageProducer();
    }

    @Bean
    public MessageListener messageListener() {
        return new MessageListener();
    }

    public static class MessageListener {
        private CountDownLatch countDownLatch = new CountDownLatch(3);

        @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.group.id}", containerFactory = "baseContainerFactory")
        public void listenBaseGroup(String message) {
            System.out.println(String.format("Received message: %s", message));
            countDownLatch.countDown();
        }
    }

    private static class MessageProducer {
        @Autowired
        private KafkaTemplate<String, UserInfoRequest> template;

        @Value(value = "${user.info.topic.name}")
        private String topicName;

        public void sendMessage(UserInfoRequest request) {
            template.send(topicName, request);
        }

    }
}
