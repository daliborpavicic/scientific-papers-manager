package rs.ac.uns.ftn.informatika.search;

import static org.apache.commons.lang.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static rs.ac.uns.ftn.informatika.model.BoolQueryOccurrence.*;
import static rs.ac.uns.ftn.informatika.model.QueryType.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import rs.ac.uns.ftn.informatika.dto.AdvancedSearchParams;
import rs.ac.uns.ftn.informatika.model.FieldQueryParams;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;

public class AdvancedSearchIntegrationTests extends AbstractSearchIntegrationTests {
	
	@Test(expected = IllegalArgumentException.class)
	public void searchAdvanced_withEmptySearchData_shouldThrowException() {
		AdvancedSearchParams emptySearchData = new AdvancedSearchParams();
		
		scientificPaperSearcher.searchAdvanced(emptySearchData);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void searchAdvanced_givenDefaultParamsForOneField_shouldThrowException() {
		AdvancedSearchParams searchData = createSearchDataForTextField(new FieldQueryParams());
		
		scientificPaperSearcher.searchAdvanced(searchData);
	}
	
	@Test
	public void searchAdvanced_givenOnlyQueryStringInParams_shouldUseDefaultsAndReturnMatches() {
		List<IndexQuery> indexQueries = createSamplePapersWithText("This is some text about information retrieval.", 5);
		
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);

		AdvancedSearchParams searchData = createSearchDataForTextField(new FieldQueryParams("information"));
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(5)));
	}
	
	@Test
	public void searchAdvanced_givenDefaultQuery_shouldReturnMatches() {
		List<ScientificPaper> testPapers = Arrays.asList(
				ScientificPaper.builder().id(randomNumeric(5)).text("Information retrieval").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("Retrieval of the information").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("Elasticsearch and Lucene").build());

		List<IndexQuery> indexQueries = createIndexQueries(testPapers);
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		
		AdvancedSearchParams searchData = createSearchDataForTextField(
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
	
	@Test
	public void searchAdvanced_givenFuzzyQuery_shouldReturnMatches() {
		List<ScientificPaper> testPapers = Arrays.asList(
				ScientificPaper.builder().id(randomNumeric(5)).text("There was a lot of foam").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("A loam is a type of soil").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("Elasticsearch and Lucene").build());

		List<IndexQuery> indexQueries = createIndexQueries(testPapers);
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		AdvancedSearchParams searchData = createSearchDataForTextField(
				new FieldQueryParams("foam", FUZZY, MUST));
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(2)));
		
		searchData = createSearchDataForTextField(
				new FieldQueryParams("foam", FUZZY, MUST_NOT));
		
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(1)));
		
		searchData = createSearchDataForTextField(
				new FieldQueryParams("foam", FUZZY, SHOULD));
		
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(2)));
	}
	
	@Test
	public void searchAdvanced_givenPhraseQuery_shouldReturnMatches() {
		List<ScientificPaper> testPapers = Arrays.asList(
				ScientificPaper.builder().id(randomNumeric(5)).text("The retrieval of the information").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("There are many papers about information retrieval").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("Retrieval is important for informations").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("Information retrieval is an interesting topic").build());

		List<IndexQuery> indexQueries = createIndexQueries(testPapers);
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		AdvancedSearchParams searchData = createSearchDataForTextField(
				new FieldQueryParams("information retrieval", PHRASE, MUST));
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(2)));
		
		searchData = createSearchDataForTextField(
				new FieldQueryParams("is important", PHRASE, MUST_NOT));
		
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(3)));
		
		searchData = createSearchDataForTextField(
				new FieldQueryParams("interesting topic", PHRASE, SHOULD));
		
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(1)));
	}
	
	@Test
	public void searchAdvanced_givenPrefixQuery_shouldReturnMatches() {
		List<ScientificPaper> testPapers = Arrays.asList(
				ScientificPaper.builder().id(randomNumeric(5)).text("Apache lucene").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("Lucky guy").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("Elasticsearch and other stuff").build());

		List<IndexQuery> indexQueries = createIndexQueries(testPapers);
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		AdvancedSearchParams searchData = createSearchDataForTextField(
				new FieldQueryParams("lu", PREFIX, MUST));
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(2)));
		
		searchData = createSearchDataForTextField(
				new FieldQueryParams("luc", PREFIX, MUST_NOT));
		
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(1)));
		
		searchData = createSearchDataForTextField(
				new FieldQueryParams("apach", PREFIX, SHOULD));
		
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(1)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void searchAdvanced_givenInvalidRangeQuery_shouldThrowException() {
		String invalidRangeQuery = "fromString#toString";
		
		AdvancedSearchParams searchData = createSearchDataForTextField(
				new FieldQueryParams(invalidRangeQuery, RANGE, MUST));
		
		scientificPaperSearcher.searchAdvanced(searchData);
	}

	@Test
	public void searchAdvanced_givenValidRangeQueryForStringField_shouldReturnMatches() {
		List<ScientificPaper> testPapers = Arrays.asList(
				ScientificPaper.builder().id(randomNumeric(5)).title("A").build(),
				ScientificPaper.builder().id(randomNumeric(5)).title("Ab").build(),
				ScientificPaper.builder().id(randomNumeric(5)).title("Abc").build(),
				ScientificPaper.builder().id(randomNumeric(5)).title("B").build(),
				ScientificPaper.builder().id(randomNumeric(5)).title("Cc").build());

		List<IndexQuery> indexQueries = createIndexQueries(testPapers);
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		String validRangeQuery = "a|b";
		
		AdvancedSearchParams searchData = new AdvancedSearchParams();
		searchData.title = new FieldQueryParams(validRangeQuery, RANGE, MUST);
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(4)));
		
		searchData.title = new FieldQueryParams(validRangeQuery, RANGE, MUST_NOT);
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(1)));
		
		searchData.title = new FieldQueryParams(validRangeQuery, RANGE, SHOULD);
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(4)));
	}
	
	@Test
	public void searchAdvanced_givenValidRangeQueryForDateField_shouldReturnMatches() {
		List<ScientificPaper> testPapers = Arrays.asList(
				ScientificPaper.builder().id(randomNumeric(5)).publishDate(createDate(2016, 10, 10)).build(),
				ScientificPaper.builder().id(randomNumeric(5)).publishDate(createDate(2016, 10, 21)).build(),
				ScientificPaper.builder().id(randomNumeric(5)).publishDate(createDate(2016, 11, 8)).build());

		List<IndexQuery> indexQueries = createIndexQueries(testPapers);
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		String validRangeQuery = "2016-10-10|2016-10-30";
		
		AdvancedSearchParams searchData = new AdvancedSearchParams();
		searchData.publishDate = new FieldQueryParams(validRangeQuery, RANGE, MUST);
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(2)));
		
		searchData.publishDate = new FieldQueryParams(validRangeQuery, RANGE, MUST_NOT);
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(1)));
		
		searchData.publishDate = new FieldQueryParams(validRangeQuery, RANGE, SHOULD);
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(2)));
	}
	
	@Test
	public void searchAdvanced_givenWildcardQuery_shouldReturnMatches() {
		List<ScientificPaper> testPapers = Arrays.asList(
				ScientificPaper.builder().id(randomNumeric(5)).text("Search test").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("Testing search").build(),
				ScientificPaper.builder().id(randomNumeric(5)).text("Elasticsearch stuff").build());

		List<IndexQuery> indexQueries = createIndexQueries(testPapers);
		elasticsearchTemplate.bulkIndex(indexQueries);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		AdvancedSearchParams searchData = createSearchDataForTextField(
				new FieldQueryParams("sea?ch", WILDCARD, MUST));
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(2)));
		
		searchData = createSearchDataForTextField(
				new FieldQueryParams("elastic*", WILDCARD, MUST_NOT));
		
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(2)));
		
		searchData = createSearchDataForTextField(
				new FieldQueryParams("s*uff", WILDCARD, SHOULD));
		
		searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.size(), is(equalTo(1)));
	}
	
	@Test
	public void searchAdvanced_givenQueryForFields_shouldReturnHighlightedText() {
		String paperAbstract = "This is an abstract text in a paper.";
		String paperText = "This is a content text in a paper.";
		String highlightedText = "This is an abstract <em>text</em> in a paper. This is a content <em>text</em> in a paper. ";
		
		ScientificPaper testPaper = ScientificPaper.builder()
				.id(randomNumeric(5))
				.anAbstract(paperAbstract)
				.text(paperText)
				.build();
		
		IndexQuery indexQuery = createIndexQuery(testPaper);
		elasticsearchTemplate.index(indexQuery);
		elasticsearchTemplate.refresh(ScientificPaper.class);
		
		AdvancedSearchParams searchData = new AdvancedSearchParams();
		searchData.anAbstract = new FieldQueryParams("text");
		searchData.text = new FieldQueryParams("text");
		
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchAdvanced(searchData);
		
		assertThat(searchResults.get(0).getHighlightedText(), is(highlightedText));
	}

	public AdvancedSearchParams createSearchDataForTextField(FieldQueryParams fieldQueryParams) {
		AdvancedSearchParams searchData = new AdvancedSearchParams();
		searchData.text = fieldQueryParams;
		
		return searchData;
	}
	
	public Date createDate(int year, int monthOfYear, int dayOfMonth) {
		return new DateTime()
				.withDate(year, monthOfYear, dayOfMonth)
				.toDate();
	}
}
