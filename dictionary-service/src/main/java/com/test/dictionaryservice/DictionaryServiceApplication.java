package com.test.dictionaryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DictionaryServiceApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(DictionaryServiceApplication.class, args);

        MessageListener listener = context.getBean(MessageListener.class);
        listener.countDownLatch.await(1, TimeUnit.SECONDS);
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

}
