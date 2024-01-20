package com.webapp.ecomerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.ecomerce.api.model.SendEmailBody;

@Service
public class UserServiceConsumer {

	@Autowired
	ObjectMapper objectMapper;

	@KafkaListener(topics = "${kafka.consumer.topic}", containerFactory = "ecomerceKafkaListenerContainerFactory")
	public void consumedUsedMessage(@Payload String msg, Acknowledgment ack)
			throws JsonMappingException, JsonProcessingException {

		if (!ObjectUtils.isEmpty(msg)) {
			ack.acknowledge();
			SendEmailBody body = objectMapper.readValue(msg, SendEmailBody.class);

			System.out.println("-----Consumed msg");
		}else {
			System.out.println("null found");
		}

		

	}

}
