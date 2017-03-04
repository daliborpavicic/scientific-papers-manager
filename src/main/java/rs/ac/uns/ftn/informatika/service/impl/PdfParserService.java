package rs.ac.uns.ftn.informatika.service.impl;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.AdobePDFSchema;
import org.apache.xmpbox.schema.DublinCoreSchema;
import org.apache.xmpbox.xml.DomXmpParser;
import org.apache.xmpbox.xml.XmpParsingException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.dto.ParsedScientificPaper;
import rs.ac.uns.ftn.informatika.service.DocumentParserService;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfParserService implements DocumentParserService {

    @Override
    public ParsedScientificPaper extractMetadataFromDocument(InputStream documentInputStream) throws IOException, XmpParsingException {
        ParsedScientificPaper parsedScientificPaper = new ParsedScientificPaper();
        PDDocument document = PDDocument.load(documentInputStream);
        PDDocumentCatalog catalog = document.getDocumentCatalog();
        PDMetadata meta = catalog.getMetadata();
        
        parsedScientificPaper.text = extractTextFromPDDocument(document);

        if (meta != null) {
            DomXmpParser xmpParser = new DomXmpParser();

            try {
                XMPMetadata metadata = xmpParser.parse(meta.createInputStream());

                DublinCoreSchema dc = metadata.getDublinCoreSchema();
                if (dc != null) {
                    parsedScientificPaper.title = dc.getTitle();
                }

                AdobePDFSchema pdf = metadata.getAdobePDFSchema();
                if (pdf != null) {
                    parsedScientificPaper.keywords = pdf.getKeywords();
                }

            } catch (XmpParsingException e) {
                System.err.println("An error ouccred when parsing the meta data: "
                        + e.getMessage());
            } finally {
				document.close();
			}
        } else {
            // The pdf doesn't contain any metadata, try to use the
            // document information instead
            PDDocumentInformation information = document.getDocumentInformation();
            if (information != null) {
                parsedScientificPaper.title = information.getTitle();
            }
            
            document.close();
        }

        return parsedScientificPaper;
    }

    @Override
    public String extractTextFromDocument(InputStream documentInputStream) throws IOException {
        PDDocument document = PDDocument.load(documentInputStream);

        return extractTextFromPDDocument(document);
    }
    
    private String extractTextFromPDDocument(PDDocument pdDocument) throws IOException {
    	PDFTextStripper textStripper = new PDFTextStripper();
        String pdfText = textStripper.getText(pdDocument);
        
        return pdfText;
    }

    @Override
    public List<String> extractImageNamesFromDocument(InputStream documentInputStream) throws IOException {
        PDDocument document = PDDocument.load(documentInputStream);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        List<String> imageNames = new ArrayList<>();

        PDPageTree pages = document.getPages();
        int imageCounter = 1;

        for (PDPage page : pages) {
            PDResources resources = page.getResources();
            Iterable<COSName> xObjectNames = resources.getXObjectNames();

            for (COSName xObjectName : xObjectNames) {
                PDXObject xObject = resources.getXObject(xObjectName);
                PDImageXObject pdImageXObject = (PDImageXObject) xObject;

                BufferedImage image = pdImageXObject.getImage();
                imageNames.add(xObjectName.getName() + imageCounter++);
                // TODO: Generate some meaningful image name
//                ImageIO.write(image, "JPEG", new File(String.format("%simg_%d.%s", IMAGE_FILE_PATH, imageCounter++, "jpg")));
            }
        }

        return imageNames;
    }
}
