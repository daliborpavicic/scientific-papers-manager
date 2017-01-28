package rs.ac.uns.ftn.informatika.controller.scientificpaper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;
import rs.ac.uns.ftn.informatika.exception.StorageFileNotFoundException;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.service.DocumentParserService;
import rs.ac.uns.ftn.informatika.service.ScientificPaperIndexer;
import rs.ac.uns.ftn.informatika.service.StorageService;

@RestController
@RequestMapping("/paper")
public class IndexController {

    private final StorageService storageService;

    private final DocumentParserService documentParserService;

    private final ScientificPaperIndexer scientificPaperIndexer;

    @Autowired
    public IndexController(StorageService storageService, DocumentParserService documentParserService, ScientificPaperIndexer scientificPaperService) {
        this.storageService = storageService;
        this.documentParserService = documentParserService;
        this.scientificPaperIndexer = scientificPaperService;
    }

    @RequestMapping(value="publish", method = RequestMethod.POST)
    public ResponseEntity<ScientificPaper> publishScientificPaper(@RequestBody NewScientificPaper newScientificPaper) throws IOException {
        Resource resource = storageService.loadAsResource(newScientificPaper.fileName);
        newScientificPaper.text = documentParserService.extractTextFromDocument(resource.getInputStream());
        newScientificPaper.numberOfImages = documentParserService.extractImageNamesFromDocument(resource.getInputStream()).size();
        
        ScientificPaper indexedDocument = scientificPaperIndexer.index(newScientificPaper);
        
        return ResponseEntity
        		.ok()
        		.body(indexedDocument);
    }
    
    @RequestMapping(value="", method = RequestMethod.GET)
    public Iterable<ScientificPaper> listAllPapers() {
    	Iterable<ScientificPaper> allScientificPapers = scientificPaperIndexer.findAll();
		
    	return allScientificPapers;
    }
    
    @RequestMapping(value="", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAllScientificPapers() {
    	scientificPaperIndexer.deleteAll();
    	
    	return ResponseEntity
    			.ok()
    			.body("Successfully deleted all scientific papers from index!");
    }
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<Void> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
