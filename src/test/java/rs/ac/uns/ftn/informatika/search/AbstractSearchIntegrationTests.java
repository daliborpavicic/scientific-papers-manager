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
public class AbstractSearchIntegrationTests {

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
		elasticsearchTemplate.putMapping(ScientificPaper.class);
		addTestDocumentsToIndex();
	}

	private void addTestDocumentsToIndex() {
		List<IndexQuery> indexQueries = testDataGenerator.generateTestData().stream().map((testDocument) -> {
			
			return new IndexQueryBuilder()
					.withObject(testDocument)
					.withId(String.valueOf(testDocument.getId()))
					.withIndexName(ScientificPaper.INDEX_NAME)
					.withType(ScientificPaper.TYPE_NAME)
					.build();
		}).collect(Collectors.toList());
		
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
	}
}
