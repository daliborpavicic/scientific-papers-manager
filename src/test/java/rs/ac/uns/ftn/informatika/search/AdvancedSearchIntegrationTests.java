package rs.ac.uns.ftn.informatika.search;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import rs.ac.uns.ftn.informatika.dto.AdvancedSearchData;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;

public class AdvancedSearchIntegrationTests extends AbstractSearchIntegrationTests {
	
	@Test
	public void searchAdvanced_withEmptySearchData() {
		AdvancedSearchData advancedSearchData = new AdvancedSearchData();
		List<ScientificPaper> searchAdvanced = scientificPaperSearcher.searchAdvanced(advancedSearchData);
		
		assertThat(searchAdvanced.size(), is(0));
	}

}
