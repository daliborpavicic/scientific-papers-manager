package rs.ac.uns.ftn.informatika.service;

import org.apache.xmpbox.xml.XmpParsingException;
import rs.ac.uns.ftn.informatika.dto.ParsedScientificPaper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface DocumentParserService {
    ParsedScientificPaper extractMetadataFromDocument(InputStream documentInputStream) throws IOException, XmpParsingException;

    String extractTextFromDocument(InputStream documentInputStream) throws IOException;

    List<String> extractImageNamesFromDocument(InputStream documentInputStream) throws IOException;
}
