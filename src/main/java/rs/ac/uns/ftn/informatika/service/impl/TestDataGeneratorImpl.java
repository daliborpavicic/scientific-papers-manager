package rs.ac.uns.ftn.informatika.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.model.ScientificPaper.Builder;
import rs.ac.uns.ftn.informatika.model.security.Account;
import rs.ac.uns.ftn.informatika.model.security.Authority;
import rs.ac.uns.ftn.informatika.model.security.AuthorityName;
import rs.ac.uns.ftn.informatika.repository.AccountRepository;
import rs.ac.uns.ftn.informatika.repository.AuthorityRepository;
import rs.ac.uns.ftn.informatika.service.CategoryService;
import rs.ac.uns.ftn.informatika.service.StorageService;
import rs.ac.uns.ftn.informatika.service.TestDataGenerator;

@Service
public class TestDataGeneratorImpl implements TestDataGenerator {
	
	private StorageService storageService;
	private CategoryService categoryService;
	private AccountRepository accountRepository;
	private AuthorityRepository authorityRepository;
	
	public TestDataGeneratorImpl(
			StorageService storageService, 
			CategoryService categoryService,
			AccountRepository accountRepository, 
			AuthorityRepository authorityRepository) {
		this.storageService = storageService;
		this.categoryService = categoryService;
		this.accountRepository = accountRepository;
		this.authorityRepository = authorityRepository;
	}

	@Override
	public List<ScientificPaper> generateTestPapersWithText(String text, int numberOfPapers) {
		List<ScientificPaper> scientificPapers = new ArrayList<ScientificPaper>();
		
		for (int i = 0; i < numberOfPapers; i++) {
			Builder builder = ScientificPaper.builder();
			
			builder
			.id(UUID.randomUUID().toString())
			.title("Title " + i)
			.anAbstract("Abstract " + i)
			.keywords("Keywords " + i)
			.text(text)
			.publishDate(new DateTime().minusDays(i).toDate())
			.authorName("Author " + i)
			.numberOfImages(i)
			.fileName("Filename " + i);
			
			if (i < 5) {
				builder.categoryName(RESEARCH_PAPER);
				
			} else if (i >= 5 && i < 10) {
				builder.categoryName(CASE_REPORT);
				
			} else if (i >= 10 && i < 20) {
				builder.categoryName(POSITION_PAPER);
			
			} else if (i >= 20 && i < 30) {
				builder.categoryName(REVIEW_ARTICLE);
			} else {
				builder.categoryName(SPECIES_PAPER);
			}
			
			scientificPapers.add(builder.build());
		}
		
		return scientificPapers;
	}

	
	@Override
	public void initDatabase() {
		storageService.deleteAll();
		categoryService.deleteAll();
		
        storageService.init();
        categoryService.init();
        
        accountRepository.deleteAll();
        
        createAccount("admin", "$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi", AuthorityName.ROLE_ADMIN, "");
        createAccount("user", "$2a$04$HpOjsVkMMvk479D1ZGw1BOYmUGGXQJu3/mk.wohMdypVVQM2J0xaS", AuthorityName.ROLE_USER, "dalibor-pavicic@hotmail.com");
	}

	private void createAccount(String username, String password, AuthorityName authorityName, String email) {
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		account.setEmail(email);

		Authority authority = new Authority(authorityName);
        
        accountRepository.save(account);
        authorityRepository.save(authority);

        List<Authority> accountAuthorities = new ArrayList<>();
        accountAuthorities.add(authority);
        
		account.setAuthorities(accountAuthorities);
		
		accountRepository.saveAndFlush(account);
	}

}
