package rs.ac.uns.ftn.informatika.service;

import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;

public interface ScientificPaperIndexer {

	String index(NewScientificPaper newScientificPaper);

	ScientificPaper convertToDocument(NewScientificPaper newScientificPaper);

}