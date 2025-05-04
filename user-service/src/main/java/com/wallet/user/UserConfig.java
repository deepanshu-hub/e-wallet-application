package com.wallet.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Properties;

@Configuration
public class UserConfig {

    //Two steps req
    //1) add kafka config
    //2) to add kafka config to kafka template
    //3) publish the message
    //4) User will act only as producer
    //5) we need to add only producer config

    public Properties getProducerConfigs() {
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return prop;

    }

    ProducerFactory<String, String> getProducerFactory() {
        return new DefaultKafkaProducerFactory(getProducerConfigs());
    }

    @Bean
    KafkaTemplate<String, String> getKafkaTemplate() {
        return new KafkaTemplate<>(getProducerFactory());
    }

    @Bean
    ObjectMapper getMapper() {
        return new ObjectMapper();
    }
}
