package rs.ac.uns.ftn.informatika;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import rs.ac.uns.ftn.informatika.configuration.ElasticsearchConfiguration;
import rs.ac.uns.ftn.informatika.configuration.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableAutoConfiguration(exclude = {ElasticsearchConfiguration.class})
public class Application {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return (args) -> {
			logger.info("scientific-papers-manager is up and running...");
		};
	}
}
