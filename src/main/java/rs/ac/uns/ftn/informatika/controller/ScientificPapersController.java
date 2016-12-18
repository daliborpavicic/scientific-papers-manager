package rs.ac.uns.ftn.informatika.controller;

import org.apache.xmpbox.xml.XmpParsingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;
import rs.ac.uns.ftn.informatika.dto.ParsedScientificPaper;
import rs.ac.uns.ftn.informatika.exception.StorageFileNotFoundException;
import rs.ac.uns.ftn.informatika.service.DocumentParserService;
import rs.ac.uns.ftn.informatika.service.StorageService;
import rs.ac.uns.ftn.informatika.service.impl.ScientificPaperService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/paper")
public class ScientificPapersController {

    private final StorageService storageService;

    private final DocumentParserService documentParserService;

    private final ScientificPaperService scientificPaperService;

    @Autowired
    public ScientificPapersController(StorageService storageService, DocumentParserService documentParserService, ScientificPaperService scientificPaperService) {
        this.storageService = storageService;
        this.documentParserService = documentParserService;
        this.scientificPaperService = scientificPaperService;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<String>> listUploadedFiles() throws IOException {

        List<String> uploadedFiles = storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(ScientificPapersController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(uploadedFiles);
    }

    @ResponseBody
    @RequestMapping(value = "/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @ResponseBody
    @RequestMapping(value="upload", method = RequestMethod.POST)
    public ResponseEntity<ParsedScientificPaper> handlePaperPdfUpload(@RequestParam("paperFile") MultipartFile paperFile)
            throws IOException, XmpParsingException {
        ParsedScientificPaper parsedScientificPaper = documentParserService.extractMetadataFromDocument(paperFile.getInputStream());
        parsedScientificPaper.fileName = storageService.store(paperFile);

        return ResponseEntity
                .ok()
                .body(parsedScientificPaper);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void publishScientificPaper(@RequestBody NewScientificPaper newScientificPaper) throws IOException {
        Resource resource = storageService.loadAsResource(newScientificPaper.fileName);
        newScientificPaper.text = documentParserService.extractTextFromDocument(resource.getInputStream());

        // todo: add a new document to Elasticsearch index, retrieve the id from ES and save the document data to DB

        scientificPaperService.save(newScientificPaper);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
