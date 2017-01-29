package rs.ac.uns.ftn.informatika.search;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rs.ac.uns.ftn.informatika.TestApplication;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.service.ScientificPaperSearcher;
import rs.ac.uns.ftn.informatika.service.TestDataGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestApplication.class})
public abstract class AbstractSearchIntegrationTests {

	@Autowired
	protected ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	protected ScientificPaperSearcher scientificPaperSearcher;
	
	@Autowired
	protected TestDataGenerator testDataGenerator;
	
	@Before
	public void before() {
		elasticsearchTemplate.deleteIndex(ScientificPaper.class);
		elasticsearchTemplate.createIndex(ScientificPaper.class);
		elasticsearchTemplate.refresh(ScientificPaper.class);
	}
	
	protected IndexQuery createIndexQuery(ScientificPaper scientificPaper) {
		return new IndexQueryBuilder()
				.withId(scientificPaper.getId())
				.withObject(scientificPaper)
				.build();
	}
	
	protected List<IndexQuery> createIndexQueries(List<ScientificPaper> scientificPapers) {
		return scientificPapers.stream().map((scientificPaper) -> {
			return new IndexQueryBuilder()
					.withId(scientificPaper.getId())
					.withObject(scientificPaper)
					.build();
		}).collect(Collectors.toList());
	}
	
	protected List<IndexQuery> createSamplePapersWithText(String text, int numberOfPapers) {
		List<ScientificPaper> testPapersWithText = testDataGenerator.generateTestPapersWithText(text, numberOfPapers);
		
		return createIndexQueries(testPapersWithText);
	}
}
