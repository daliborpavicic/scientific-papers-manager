package rs.ac.uns.ftn.informatika.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;
import rs.ac.uns.ftn.informatika.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	@Override
	@Async
	public void sendScientificPaperContent(NewScientificPaper scientificPaper, String mailTo) throws MailException, InterruptedException {
		logger.info("Sending an email...");
				
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mailTo);
		mailMessage.setFrom("daliborp282@gmail.com"); // TODO: Create an email account for testing
		mailMessage.setSubject(scientificPaper.title);
		mailMessage.setText(scientificPaper.text);
		
		javaMailSender.send(mailMessage);

		logger.info("Email sent!");
	}

}
