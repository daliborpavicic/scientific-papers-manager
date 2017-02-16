package rs.ac.uns.ftn.informatika.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.dto.AdvancedSearchData;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;

public interface ScientificPaperSearcher {

	List<ScientificPaper> searchSimple(String query);

	List<ScientificPaper> searchAdvanced(AdvancedSearchData advancedSearchData);
	
	List<ScientificPaper> searchMoreLikeThis(String documentId);

}