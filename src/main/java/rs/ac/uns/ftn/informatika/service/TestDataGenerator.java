package rs.ac.uns.ftn.informatika.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.model.ScientificPaper;

public interface TestDataGenerator {
	
	public static final String RESEARCH_PAPER = "Research paper";
	public static final String CASE_REPORT = "Case report";
	public static final String POSITION_PAPER = "Position paper";
	public static final String REVIEW_ARTICLE = "Review article";
	public static final String SPECIES_PAPER = "Species paper";
	public static final String TECHNICAL_PAPER = "Technical paper";
	
	public static final String[] ALL_CATEGORIES = {
			RESEARCH_PAPER, CASE_REPORT, POSITION_PAPER, REVIEW_ARTICLE, SPECIES_PAPER, TECHNICAL_PAPER
	};
	
	List<ScientificPaper> generateTestPapersWithText(String text, int numberOfPapers);
	
	void initDatabase();
}
