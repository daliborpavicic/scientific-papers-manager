package rs.ac.uns.ftn.informatika.search;

import static org.apache.commons.lang.RandomStringUtils.randomNumeric;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import rs.ac.uns.ftn.informatika.model.ScientificPaper;

public class MoreLikeThisSearchIntegrationTests extends AbstractSearchIntegrationTests {
	
	@Test
	public void searchMoreLikeThis_givenDocumentId_shouldFindSimilarDocuments() {
		ScientificPaper actualDocument = ScientificPaper.builder().id(randomNumeric(5)).text("Once upon a time").build();
		ScientificPaper firstSimilar = ScientificPaper.builder().id(randomNumeric(5)).text("That was once").build();
		ScientificPaper secondSimilar = ScientificPaper.builder().id(randomNumeric(5)).text("Time passes quickly").build();
		ScientificPaper unrelatedDocument = ScientificPaper.builder().id(randomNumeric(5)).text("This is unrelated to others").build();
		
		List<ScientificPaper> allDocuments = Arrays.asList(actualDocument, firstSimilar, secondSimilar, unrelatedDocument);

		List<IndexQuery> indexQueries = createIndexQueries(allDocuments);
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		List<ScientificPaper> similarDocuments = scientificPaperSearcher.searchMoreLikeThis(actualDocument.getId());
		
		assertThat(similarDocuments.size(), is(equalTo(2)));
	}

}
