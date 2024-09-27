package com.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.logging.Logger;


@Service
public class MailService {
	
	Logger log = Logger.getLogger(MailService.class.getName());

	@Autowired
	private MailSender mailSender;

//	@Autowired
//	private VelocityEngine velocityEngine;

	@Async("myExecutor")
	public void sendSimpleEmail(String to, String subject, String msg)
			throws MailException {

		SimpleMailMessage message = new SimpleMailMessage();

//		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
		log.info("send email done!");
	}

}
