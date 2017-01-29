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
    public ResponseEntity<String> publishScientificPaper(@RequestBody NewScientificPaper newScientificPaper) throws IOException {
        Resource resource = storageService.loadAsResource(newScientificPaper.fileName);
        newScientificPaper.text = documentParserService.extractTextFromDocument(resource.getInputStream());
        newScientificPaper.numberOfImages = documentParserService.extractImageNamesFromDocument(resource.getInputStream()).size();
        
        String documentId = scientificPaperIndexer.index(newScientificPaper);
        
        return ResponseEntity
        		.ok()
        		.body(documentId);
    }
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<Void> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
