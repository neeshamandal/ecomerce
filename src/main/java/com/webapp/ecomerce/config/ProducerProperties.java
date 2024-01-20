package com.webapp.ecomerce.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka.producer")
public class ProducerProperties implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bootstarpServers;
	private String keySerializer;
	private String valueSerialzier;
	private String acks;
	private String retries;
	private String batchSize;
	private String bufferMemory;

}
