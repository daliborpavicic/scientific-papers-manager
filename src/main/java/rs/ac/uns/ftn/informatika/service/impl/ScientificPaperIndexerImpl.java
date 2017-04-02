package rs.ac.uns.ftn.informatika.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;
import rs.ac.uns.ftn.informatika.model.Category;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.service.CategoryService;
import rs.ac.uns.ftn.informatika.service.ScientificPaperIndexer;

@Service
public class ScientificPaperIndexerImpl implements ScientificPaperIndexer {

	private ElasticsearchTemplate elasticsearchTemplate;
	
	private CategoryService categoryService;
	
	@Autowired
	public ScientificPaperIndexerImpl(ElasticsearchTemplate elasticsearchTemplate, CategoryService categoryService) {
		this.elasticsearchTemplate = elasticsearchTemplate;
		this.categoryService = categoryService;
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
		Category paperCategory = categoryService.findCategoryByName(newScientificPaper.categoryName);
		
		ScientificPaper domainEntity = ScientificPaper.builder()
			.title(newScientificPaper.title)
			.anAbstract(newScientificPaper.anAbstract)
			.keywords(newScientificPaper.keywords)
			.categoryName(paperCategory != null ? paperCategory.getName() : "default_category") // TODO: Use validation to prevent null value
			.text(newScientificPaper.text)
			.publishDate(new Date())
			.authorName(newScientificPaper.authorName)
			.numberOfImages(newScientificPaper.numberOfImages)
			.fileName(newScientificPaper.fileName)
			.build();
		
		return domainEntity;
	}

}
