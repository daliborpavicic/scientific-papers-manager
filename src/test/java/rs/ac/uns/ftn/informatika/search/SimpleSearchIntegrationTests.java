package rs.ac.uns.ftn.informatika.search;

import static org.apache.commons.lang.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import rs.ac.uns.ftn.informatika.model.ScientificPaper;

public class SimpleSearchIntegrationTests extends AbstractSearchIntegrationTests {
	
	@Test
	public void searchSimple_givenEmptyQueryString_shouldReturnEmptyList() {
		List<IndexQuery> indexQueries = createSamplePapersWithText("randomText", 5);
		
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		int resultsCount = scientificPaperSearcher.searchSimple("").size();
		
		assertThat(resultsCount, is(equalTo(0)));
	}

	@Test
	public void searchSimple_givenSearchString_shouldReturnAllMatches() {
		List<ScientificPaper> testPapers = Arrays.asList(
				ScientificPaper.builder().id(randomNumeric(5)).text("Information retrieval").build(),
				ScientificPaper.builder().id(randomNumeric(5)).title("Retrieval systems").build(),
				ScientificPaper.builder().id(randomNumeric(5)).anAbstract("Some random text").build());
		
		List<IndexQuery> indexQueries = createIndexQueries(testPapers);
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchSimple("retrieval");
		
		assertThat(searchResults.size(), is(2));
		
		searchResults = scientificPaperSearcher.searchSimple("information");
		
		assertThat(searchResults.size(), is(1));
	}

	@Test
	public void searchSimple_givenNoMatchingSearchString_shouldReturnEmptyList() {
		ScientificPaper paper = ScientificPaper.builder().id(randomNumeric(5)).text("Document management").build();
		IndexQuery indexQuery = createIndexQuery(paper);
		
		elasticsearchTemplate.index(indexQuery);
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchSimple("NonMatching");
		
		assertThat(searchResults.size(), is(0));
	}

	@Test
	public void searchSimple_givenMultiWordSearchString_shouldMatchOnlyDocsContainingAllWords() {
		List<ScientificPaper> testPapers = Arrays.asList(
				ScientificPaper.builder().id(randomNumeric(5)).text("Information retrieval").build(),
				ScientificPaper.builder().id(randomNumeric(5)).title("Information").build(),
				ScientificPaper.builder().id(randomNumeric(5)).title("retrieval").build(),
				ScientificPaper.builder().id(randomNumeric(5)).title("Retrieval systems").build(),
				ScientificPaper.builder().id(randomNumeric(5)).anAbstract("Some random text").build());
		
		List<IndexQuery> indexQueries = createIndexQueries(testPapers);
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchSimple("information retrieval");
		
		assertThat(searchResults.size(), is(1));
		
		searchResults = scientificPaperSearcher.searchSimple("systems retrieval"); // notice different order of words comparing to matching document
		
		assertThat(searchResults.size(), is(1));
	}

	@Test
	public void searchSimple_givenQueryForFields_shouldReturnHighlightedText() {
		String paperText = "This is a content text in a paper.";
		String highlightedText = "This is a content <em>text</em> in a paper. ";
		
		ScientificPaper testPaper = ScientificPaper.builder()
				.id(randomNumeric(5))
				.text(paperText)
				.build();
		
		IndexQuery indexQuery = createIndexQuery(testPaper);
		elasticsearchTemplate.index(indexQuery);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchSimple("text");
		
		assertThat(searchResults.get(0).getHighlightedText(), is(highlightedText));
	}
}
