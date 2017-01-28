package rs.ac.uns.ftn.informatika.search;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.service.TestDataGenerator;

public class SimpleSearchIntegrationTests extends AbstractSearchIntegrationTests {
	
	@Test
	public void searchSimple_withEmptyQueryString() {
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchSimple("");
		
		assertThat(searchResults.size(), is(0));
	}

	@Test
	public void searchSimple_withCommonStringWhichOccuresInAllDocuments() {
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchSimple("Title");
		
		assertThat(searchResults.size(), is(10));
	}

	@Test
	public void searchSimple_withStringWhichDoesNotOccureInIndex() {
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchSimple("nonexisting");;
		
		assertThat(searchResults.size(), is(0));
	}

	@Test
	public void searchSimple_withPhraseWhichExistsInIndex() {
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchSimple("Title 1");
		
		assertThat(searchResults.size(), is(1));
	}
	
	@Test
	public void searchSimple_withExistingCategoryName() {
		List<ScientificPaper> searchResults = scientificPaperSearcher.searchSimple(TestDataGenerator.CASE_REPORT);
		
		assertThat(searchResults.size(), is(5));
	}

}
