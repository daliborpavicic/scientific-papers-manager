package rs.ac.uns.ftn.informatika;

import java.util.ArrayList;
import java.util.List;

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
import rs.ac.uns.ftn.informatika.model.security.Account;
import rs.ac.uns.ftn.informatika.model.security.Authority;
import rs.ac.uns.ftn.informatika.model.security.AuthorityName;
import rs.ac.uns.ftn.informatika.repository.AccountRepository;
import rs.ac.uns.ftn.informatika.repository.AuthorityRepository;
import rs.ac.uns.ftn.informatika.service.CategoryService;
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
	CommandLineRunner init(
			StorageService storageService, 
			CategoryService categoryService, 
			AccountRepository accountRepository,
			AuthorityRepository authorityRepository) {
		
		return (args) -> {
			storageService.deleteAll();
			categoryService.deleteAll();
			
            storageService.init();
            categoryService.init();
            
            accountRepository.deleteAll();
            
            Account account = new Account();
            account.setUsername("admin");
            account.setPassword("$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi");

            Authority authority = new Authority(AuthorityName.ROLE_ADMIN);
            
            accountRepository.save(account);
            authorityRepository.save(authority);

            List<Authority> accountAuthorities = new ArrayList<>();
            accountAuthorities.add(authority);
            
			account.setAuthorities(accountAuthorities);
			
			accountRepository.saveAndFlush(account);
            
			logger.info("scientific-papers-manager is up and running...");
		};
	}
}
