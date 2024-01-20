package com.webapp.ecomerce.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.webapp.ecomerce.api.model.SendEmailBody;

@Service
public class SendGridService {

	@Autowired
	ObjectMapper objectMapper;

	@Qualifier("kafkaTemplate")
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${kafka.consumer.topic}")
	private String topicName;

	@Value("${sendgrid.api.key}")
	private String sendgridApiKey;

	@Value("${from.email}")
	private String fromEmail;

	public String sendEmail(SendEmailBody sendEmailBody) throws IOException {
		if (sendEmailBody != null) {

			Email from = new Email(fromEmail);
			Email to = new Email(sendEmailBody.getTo());
			String subject = sendEmailBody.getEmailSubject();
			Content content = new Content("text/plain", sendEmailBody.getMessage());
			Mail mail = new Mail(from, subject, to, content);

			SendGrid sg = new SendGrid(sendgridApiKey);
			Request request = new Request();

			try {
				request.setMethod(Method.POST);
				request.setEndpoint("mail/send");
				request.setBody(mail.build());

				kafkaTemplate.send(topicName, objectMapper.writeValueAsString(sendEmailBody));
				
				Response response = sg.api(request);
				System.out.println(response.getStatusCode());
				System.out.println(response.getBody());
				System.out.println(response.getHeaders());

				return "email successfully sent";

			} catch (IOException ex) {
				throw ex;
			}
		} else {
			throw new RuntimeException("no object found");

		}
	}

}
