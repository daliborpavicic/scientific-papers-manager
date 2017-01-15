package rs.ac.uns.ftn.informatika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.dto.AdvancedSearchData;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.service.impl.ScientificPaperService;

@RestController
@RequestMapping("/paper")
public class ScientificPapersSearchController {

	private final ScientificPaperService scientificPaperService;

	@Autowired
	public ScientificPapersSearchController(ScientificPaperService scientificPaperService) {
		this.scientificPaperService = scientificPaperService;
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public Iterable<ScientificPaper> searchSimple(@RequestParam(name = "q") String query) {
		Iterable<ScientificPaper> scientificPapers = scientificPaperService.searchSimple(query);

		return scientificPapers;
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public Iterable<ScientificPaper> searchAdvanced(@RequestBody AdvancedSearchData advancedSearchData) {
		Iterable<ScientificPaper> scientificPapers = scientificPaperService.searchAdvanced(advancedSearchData);

		return scientificPapers;
	}

}
