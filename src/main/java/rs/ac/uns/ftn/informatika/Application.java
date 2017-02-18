package rs.ac.uns.ftn.informatika;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import rs.ac.uns.ftn.informatika.configuration.ElasticsearchConfiguration;
import rs.ac.uns.ftn.informatika.configuration.StorageProperties;
import rs.ac.uns.ftn.informatika.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableAutoConfiguration(exclude = {ElasticsearchConfiguration.class})
public class Application {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static final String NAME = "scientific-papers-manager";
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
            storageService.init();
            
			logger.info("scientific-papers-manager is up and running...");
		};
	}
}
