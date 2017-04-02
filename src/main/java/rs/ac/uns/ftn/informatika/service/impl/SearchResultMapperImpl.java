package rs.ac.uns.ftn.informatika.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.model.ScientificPaperFieldName;

@Service
public final class SearchResultMapperImpl implements SearchResultMapper {
	
	private static final DateTimeFormatter formatter = DateTimeFormat.forPattern(ScientificPaper.DATE_PATTERN);
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Page<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
		List<ScientificPaper> results = new ArrayList<ScientificPaper>();

		response.getHits().forEach((searchHit) -> {
			ScientificPaper scientificPaper = ScientificPaper.builder().id(searchHit.getId())
					.title((String) searchHit.getSource().get(ScientificPaperFieldName.TITLE.getFieldName()))
					.anAbstract((String) searchHit.getSource().get(ScientificPaperFieldName.ABSTRACT.getFieldName()))
					.keywords((String) searchHit.getSource().get(ScientificPaperFieldName.KEYWORDS.getFieldName()))
					.categoryName((String) searchHit.getSource().get(ScientificPaperFieldName.CATEGORY.getFieldName()))
					.publishDate(parseDate(searchHit))
					.authorName((String) searchHit.getSource().get(ScientificPaperFieldName.AUTHOR.getFieldName()))
					.fileName((String) searchHit.getSource().get(ScientificPaperFieldName.FILE_NAME.getFieldName()))
					.numberOfImages((Integer) searchHit.getSource().get(ScientificPaperFieldName.NUMBER_OF_IMAGES.getFieldName()))
					.highlightedText(getHighlightText(searchHit))
					.build();

			results.add(scientificPaper);
		});

		return new PageImpl<T>((List<T>) results);
	}
	
	private Date parseDate(SearchHit searchHit) {
		String dateString = (String) searchHit.getSource().get(ScientificPaperFieldName.PUBLISH_DATE.getFieldName());
		Date parsedDate = null;
		
		if (dateString != null) {
			DateTime parsedDateTime = formatter.parseDateTime(dateString);
			parsedDate = parsedDateTime.toDate();
		}
		
		return parsedDate;
	}
	
	private String getHighlightText(SearchHit searchHit) {
		StringBuilder sb = new StringBuilder();
		String highlightText;
		Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
		
		for (String fieldName : ScientificPaperFieldName.highlightFieldNames) {
			if (highlightFields.containsKey(fieldName)) {
				Text[] fragments = highlightFields.get(fieldName).fragments();
				
				if (fragments != null && fragments.length > 0) {
					sb.append(String.format("%s ", fragments[0].toString()));
				}
			}
		}
		
		highlightText = sb.toString();
		
		return highlightText;
	}
}