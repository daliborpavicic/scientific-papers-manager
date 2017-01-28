package rs.ac.uns.ftn.informatika.service;

import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;

public interface ScientificPaperIndexer {

	ScientificPaper index(NewScientificPaper newScientificPaper);

	Iterable<ScientificPaper> findAll();

	void deleteAll();

	ScientificPaper convertToDomainEntity(NewScientificPaper newScientificPaper);

}