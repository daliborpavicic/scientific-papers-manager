package rs.ac.uns.ftn.informatika.search;

import static org.apache.commons.lang.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static rs.ac.uns.ftn.informatika.model.BoolQueryOccurrence.*;
import static rs.ac.uns.ftn.informatika.model.QueryType.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import rs.ac.uns.ftn.informatika.dto.AdvancedSearchData;
import rs.ac.uns.ftn.informatika.model.FieldQueryParams;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;

public class AdvancedSearchIntegrationTests extends AbstractSearchIntegrationTests {
	
	@Test(expected = IllegalArgumentException.class)
	public void searchAdvanced_withEmptySearchData_shouldThrowException() {
		AdvancedSearchData emptySearchData = new AdvancedSearchData();
		
		scientificPaperSearcher.searchAdvanced(emptySearchData);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void searchAdvanced_givenDefaultParamsForOneField_shouldThrowException() {
		AdvancedSearchData searchData = createSearchDataForTextField(new FieldQueryParams());
		
		scientificPaperSearcher.searchAdvanced(searchData);
	}
	
	@Test
	public void searchAdvanced_givenOnlyQueryStringInParams_shouldUseDefaultsAndReturnMatches() {
		List<IndexQuery> indexQueries = createSamplePapersWithText("This is some text about information retrieval.", 5);
		
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);

		AdvancedSearchData searchData = createSearchDataForTextField(new FieldQueryParams("information"));
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(5)));
	}
	
	@Test
	public void searchAdvanced_givenDefaultQueryType_shouldReturnMatches() {
		List<ScientificPaper> testPapers = Arrays.asList(
				ScientificPaper.builder().id(randomNumeric(5)).text("Information retrieval").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("Retrieval of the information").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("Elasticsearch and Lucene").build());

		List<IndexQuery> indexQueries = createIndexQueries(testPapers);
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		
		AdvancedSearchData searchData = createSearchDataForTextField(
				new FieldQueryParams("information", DEFAULT, MUST));
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(2)));

		searchData = createSearchDataForTextField(
				new FieldQueryParams("information", DEFAULT, MUST_NOT));
		
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(1)));
		
		searchData = createSearchDataForTextField(
				new FieldQueryParams("information", DEFAULT, SHOULD));
		
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(2)));
	}
	
	
	public AdvancedSearchData createSearchDataForTextField(FieldQueryParams fieldQueryParams) {
		AdvancedSearchData searchData = new AdvancedSearchData();
		searchData.textParams = fieldQueryParams;
		
		return searchData;
	}

}
