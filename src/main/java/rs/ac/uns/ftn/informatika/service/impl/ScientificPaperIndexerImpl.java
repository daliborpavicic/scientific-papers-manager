package rs.ac.uns.ftn.informatika.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.repository.ScientificPaperRepository;
import rs.ac.uns.ftn.informatika.service.ScientificPaperIndexer;

@Service
public class ScientificPaperIndexerImpl implements ScientificPaperIndexer {
	
	@Autowired
	private ScientificPaperRepository scientificPaperRepository;

	@Override
	public ScientificPaper index(NewScientificPaper newScientificPaper) {
		ScientificPaper documentToIndex = convertToDomainEntity(newScientificPaper);
	
		return scientificPaperRepository.index(documentToIndex);
	}

	@Override
	public Iterable<ScientificPaper> findAll() {
		return scientificPaperRepository.findAll();
	}

	@Override
	public void deleteAll() {
		scientificPaperRepository.deleteAll();
	}

	@Override
	public ScientificPaper convertToDomainEntity(NewScientificPaper newScientificPaper) {
		ScientificPaper domainEntity = ScientificPaper.builder()
			.title(newScientificPaper.title)
			.anAbstract(newScientificPaper.anAbstract)
			.keywords(newScientificPaper.keywords)
			.categoryName("default_category") // TODO: Retrieve the category name from DB. Category ID will be provided in DTO which comes from the client
			.text(newScientificPaper.text)
			.publishDate(new Date()) // TODO: Set date to current date string
			.authorName("default_author") // TODO: Set author name to the name of currently logged user after security is implemented
			.numberOfImages(newScientificPaper.numberOfImages)
			.fileName(newScientificPaper.fileName)
			.build();
		
		return domainEntity;
	}

}
