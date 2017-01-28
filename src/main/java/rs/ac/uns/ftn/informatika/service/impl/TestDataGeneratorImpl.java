package rs.ac.uns.ftn.informatika.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.model.ScientificPaper.Builder;
import rs.ac.uns.ftn.informatika.service.TestDataGenerator;

@Service
public class TestDataGeneratorImpl implements TestDataGenerator {

	@Override
	public List<ScientificPaper> generateTestData() {
		List<ScientificPaper> scientificPapers = new ArrayList<ScientificPaper>();
		
		for (int i = 0; i < 50; i++) {
			Builder builder = ScientificPaper.builder();
			
			builder
			.id(Long.valueOf(i))
			.title("Title " + i)
			.anAbstract("Abstract " + i)
			.keywords("Keywords " + i)
			.text("Text " + i)
			.publishDate(new DateTime().minusDays(i).toDate())
			.authorName("Author " + i)
			.numberOfImages(i)
			.fileName("Filename " + i);
			
			if (i < 5) {
				builder.categoryName(RESEARCH_PAPER);
				
			} else if (i >= 5 && i < 10) {
				builder.categoryName(CASE_REPORT);
				
			} else if (i >= 10 && i < 20) {
				builder.categoryName(POSITION_PAPER);
			
			} else if (i >= 20 && i < 30) {
				builder.categoryName(REVIEW_ARTICLE);
			} else {
				builder.categoryName(SPECIES_PAPER);
			}
			
			scientificPapers.add(builder.build());
		}
		
		return scientificPapers;
	}
	
	

}
