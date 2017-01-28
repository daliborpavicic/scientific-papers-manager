package rs.ac.uns.ftn.informatika.controller.scientificpaper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.xmpbox.xml.XmpParsingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import rs.ac.uns.ftn.informatika.dto.ParsedScientificPaper;
import rs.ac.uns.ftn.informatika.service.DocumentParserService;
import rs.ac.uns.ftn.informatika.service.StorageService;

public class FileController {
	
    private final StorageService storageService;

    private final DocumentParserService documentParserService;
    
    @Autowired
	public FileController(StorageService storageService, DocumentParserService documentParserService) {
		this.storageService = storageService;
		this.documentParserService = documentParserService;
	}

	@ResponseBody
	@RequestMapping(value="upload", method = RequestMethod.GET)
	public ResponseEntity<List<String>> listUploadedFiles() throws IOException {
	
	    List<String> uploadedFiles = storageService
	            .loadAll()
	            .map(path ->
	                    MvcUriComponentsBuilder
	                            .fromMethodName(IndexController.class, "serveFile", path.getFileName().toString())
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

}
