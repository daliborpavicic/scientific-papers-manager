package rs.ac.uns.ftn.informatika;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
public class TestApplication {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("scientific-papers-manager is running with test configuration");
	}

	@Bean
	public JavaMailSenderImpl mockMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		javaMailSender.setProtocol("SMTP");
		javaMailSender.setHost("127.0.0.1");
		javaMailSender.setPort(25);

		return javaMailSender;
	}
}
