package com.wallet.notification;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.InputStream;
import java.util.Properties;

@Configuration
public class NotificationConfig {

    Properties getConsumerConfigs(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return properties;
    }

    @Bean
    ConsumerFactory<String, String> getConsumerFactory() {
        return new DefaultKafkaConsumerFactory(getConsumerConfigs());
    }

    @Bean
    SimpleMailMessage getMailMsg(){
        return new SimpleMailMessage();
    }

    @Bean
    JavaMailSender getMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("rsdd04@gmail.com");
        ////update pasword check in file ----- removing due security purpose
        javaMailSender.setPassword("jpot isph hcxz jknc");


        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.debug", true);

        return javaMailSender;
    }
}
