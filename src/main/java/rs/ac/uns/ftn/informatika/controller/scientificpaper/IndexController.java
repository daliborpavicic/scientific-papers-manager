package rs.ac.uns.ftn.informatika.controller.scientificpaper;

import static rs.ac.uns.ftn.informatika.controller.HomeController.BASE_API_URL;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;
import rs.ac.uns.ftn.informatika.dto.PublishPaperResponse;
import rs.ac.uns.ftn.informatika.exception.StorageFileNotFoundException;
import rs.ac.uns.ftn.informatika.model.security.JwtUser;
import rs.ac.uns.ftn.informatika.service.DocumentParserService;
import rs.ac.uns.ftn.informatika.service.EmailService;
import rs.ac.uns.ftn.informatika.service.ScientificPaperIndexer;
import rs.ac.uns.ftn.informatika.service.StorageService;

@RestController
@RequestMapping(BASE_API_URL + "/paper")
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final StorageService storageService;

    private final DocumentParserService documentParserService;

    private final ScientificPaperIndexer scientificPaperIndexer;
    
    private final EmailService emailService;

    @Autowired
    public IndexController(StorageService storageService, DocumentParserService documentParserService, 
    		ScientificPaperIndexer scientificPaperService, EmailService emailService) {
        this.storageService = storageService;
        this.documentParserService = documentParserService;
        this.scientificPaperIndexer = scientificPaperService;
        this.emailService = emailService;
    }

    @RequestMapping(value="publish", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PublishPaperResponse> publishScientificPaper(
    		@RequestBody NewScientificPaper newScientificPaper, Authentication authentication) throws IOException {
        Resource resource = storageService.loadAsResource(newScientificPaper.fileName);
        newScientificPaper.text = documentParserService.extractTextFromDocument(resource.getInputStream());
        newScientificPaper.numberOfImages = documentParserService.extractImageNamesFromDocument(resource.getInputStream()).size();
        
        JwtUser currentUser = (JwtUser) authentication.getPrincipal();
        newScientificPaper.authorName = currentUser.getUsername();
        
        PublishPaperResponse publishPaperResponse = new PublishPaperResponse();
        
        publishPaperResponse.documentId = scientificPaperIndexer.index(newScientificPaper);
        
        logger.info(String.format("Document [id = %s] is successfully added to an index.", publishPaperResponse.documentId));
        
        try {
			emailService.sendScientificPaperContent(newScientificPaper, currentUser.getEmail());
		} catch (Exception e) {
			logger.info("Error sending an email: " + e.getMessage());
		}
        
        return ResponseEntity
        		.ok()
        		.body(publishPaperResponse);
    }
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<Void> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
