package com.webapp.ecomerce.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableKafka
@Configuration
@ConditionalOnProperty(prefix = "kafka.consumer", name = "enable", havingValue = "true")
public class ConsumerConfiguration {

	@Autowired
	private ConsumerProperties kafkaProperties;

	@Bean
	public Map<String, Object> kafkaConsumerConfig() {
		log.info("-----Server URL : {}", kafkaProperties.getBootstrapServers());
		Map<String, Object> properties = new LinkedHashMap<>();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getAutoOffsetReset());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.getEnableAutoCommit());
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaProperties.getAutoCommitInterval());
		properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaProperties.getSessionTimeout());
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		return properties;
	}

	@Bean
	public ConsumerFactory<String, String> payloadConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(new HashMap<>(kafkaConsumerConfig()));
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> ecomerceKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(payloadConsumerFactory());
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
		factory.setConcurrency(kafkaProperties.getConcurrency());
		return factory;
	}

}
