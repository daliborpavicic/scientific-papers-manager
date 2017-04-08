package rs.ac.uns.ftn.informatika.service;

import org.springframework.mail.MailException;

import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;

public interface EmailService {

	void sendScientificPaperContent(NewScientificPaper scientificPaper, String mailTo) throws MailException, InterruptedException;

}