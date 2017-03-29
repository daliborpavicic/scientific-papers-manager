package rs.ac.uns.ftn.informatika.controller.scientificpaper;

import static rs.ac.uns.ftn.informatika.controller.HomeController.BASE_API_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.dto.AdvancedSearchParams;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.service.ScientificPaperSearcher;

@RestController
@RequestMapping(BASE_API_URL + "/paper")
public class SearchController {

	private final ScientificPaperSearcher scientificPaperSearcher;

	@Autowired
	public SearchController(ScientificPaperSearcher scientificPaperSearcher) {
		this.scientificPaperSearcher = scientificPaperSearcher;
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public Iterable<ScientificPaper> searchSimple(@RequestParam(name = "q") String query) {
		Iterable<ScientificPaper> scientificPapers = scientificPaperSearcher.searchSimple(query);

		return scientificPapers;
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public Iterable<ScientificPaper> searchAdvanced(@RequestBody AdvancedSearchParams advancedSearchData) {
		Iterable<ScientificPaper> scientificPapers = scientificPaperSearcher.searchAdvanced(advancedSearchData);

		return scientificPapers;
	}
	
	@RequestMapping(value = "search/mlt", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public Iterable<ScientificPaper> searchMoreLikeThis(@RequestParam(name = "documentId") String documentId) {
		Iterable<ScientificPaper> scientificPapers = scientificPaperSearcher.searchMoreLikeThis(documentId);

		return scientificPapers;
	}

}
