package com.hospital.service;

import org.springframework.stereotype.Service;


@Service
public class MailService {
	
//	Logger log = Logger.getLogger(MailService.class.getName());
//
//	@Autowired
//	private MailSender mailSender;
//
////	@Autowired
////	private VelocityEngine velocityEngine;
//
//	@Async("myExecutor")
//	public void sendSimpleEmail(String from, String to, String subject, String msg)
//			throws MailException {
//
//		SimpleMailMessage message = new SimpleMailMessage();
//
//		message.setFrom(from);
//		message.setTo(to);
//		message.setSubject(subject);
//		message.setText(msg);
//		mailSender.send(message);
//		log.info("send email done!");
//	}
//
//	// with template velocity and attachment
//	@Async
//	public Future<Boolean> sendEmail(String from, String to, String subject, String msg, File tmpl, File attachment)
//			throws MailException {
//		SimpleMailMessage message = new SimpleMailMessage();
//
//		message.setFrom(from);
//		message.setTo(to);
//		message.setSubject(subject);
//
//		Map<String, Object> model = new HashMap<>();
//		model.put("title", "Student Subcription");
//		model.put("content", msg);
//////		String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "mail-admin.vm", "UTF-8", model);
////
////		message.setText(emailText);
////		mailSender.send(message);
////		log.info("send email done!");
//		return new AsyncResult<>(true);
//	}
//
}
