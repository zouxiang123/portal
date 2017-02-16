/**   
 * @Title: KafkaCustomerConfig.java 
 * @Package com.xtt.platform.util.kafka
 * Copyright: Copyright (c) 2015
 * @author: bruce   
 * @date: 2017年1月13日 下午1:40:24 
 *
 */
package com.xtt.platform.util.kafka;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import com.xtt.platform.util.config.SpringUtil;

@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    @SuppressWarnings("unchecked")
    @Bean
    public ConsumerFactory<Integer, String> consumerFactory() {
        Map<String, Object> configs = (Map<String, Object>) SpringUtil.getBean("consumerProperties");
        return new DefaultKafkaConsumerFactory<>(configs);
    }
}
