package com.wallet.wallet;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.Properties;

@Configuration
public class WalletConfig {

    Properties getProducerConfigs() {
        Properties prop =  new Properties();
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

    Properties getConsumerConfigs() {
        Properties prop =  new Properties();
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return prop;
    }

    @Bean
    ConsumerFactory<String, String> getConsumerFactory() {
        return new DefaultKafkaConsumerFactory(getConsumerConfigs());
    }


}
