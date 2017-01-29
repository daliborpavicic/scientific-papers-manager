package rs.ac.uns.ftn.informatika.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.service.ScientificPaperIndexer;

@Service
public class ScientificPaperIndexerImpl implements ScientificPaperIndexer {

	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	public ScientificPaperIndexerImpl(ElasticsearchTemplate elasticsearchTemplate) {
		this.elasticsearchTemplate = elasticsearchTemplate;
	}

	@Override
	public String index(NewScientificPaper newScientificPaper) {
		ScientificPaper documentToIndex = convertToDocument(newScientificPaper);
		
		IndexQuery indexQuery = new IndexQueryBuilder()
				.withObject(documentToIndex)
				.withId(String.valueOf(documentToIndex.getId()))
				.build();
		
		String documentId = elasticsearchTemplate.index(indexQuery);
	
		return documentId;
	}

	@Override
	public ScientificPaper convertToDocument(NewScientificPaper newScientificPaper) {
		ScientificPaper domainEntity = ScientificPaper.builder()
			.title(newScientificPaper.title)
			.anAbstract(newScientificPaper.anAbstract)
			.keywords(newScientificPaper.keywords)
			.categoryName(newScientificPaper.categoryName) // TODO: Retrieve the category name from DB. Category ID will be provided in DTO which comes from the client
			.text(newScientificPaper.text)
			.publishDate(new Date()) // TODO: Set date to current date string
			.authorName("default_author") // TODO: Set author name to the name of currently logged user after security is implemented
			.numberOfImages(newScientificPaper.numberOfImages)
			.fileName(newScientificPaper.fileName)
			.build();
		
		return domainEntity;
	}

}
