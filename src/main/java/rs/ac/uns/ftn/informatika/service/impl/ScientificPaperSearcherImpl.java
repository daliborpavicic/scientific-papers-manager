package rs.ac.uns.ftn.informatika.service.impl;

import static rs.ac.uns.ftn.informatika.builder.scientificpaper.SearchQueryBuilder.buildAdvancedSearchQuery;
import static rs.ac.uns.ftn.informatika.builder.scientificpaper.SearchQueryBuilder.buildMoreLikeThisSearchQuery;
import static rs.ac.uns.ftn.informatika.builder.scientificpaper.SearchQueryBuilder.buildSimpleSearchQuery;

import java.util.HashMap;
import java.util.List;

import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.dto.AdvancedSearchParams;
import rs.ac.uns.ftn.informatika.model.FieldQueryParams;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.model.ScientificPaperFieldName;
import rs.ac.uns.ftn.informatika.service.ScientificPaperSearcher;

@Service
public class ScientificPaperSearcherImpl implements ScientificPaperSearcher {
	
	private static final Logger logger = LoggerFactory.getLogger(ScientificPaperSearcherImpl.class);

    private ElasticsearchTemplate elasticSearchTemplate;
    
    private SearchResultMapper searchResultMapper;

	@Autowired
	public ScientificPaperSearcherImpl(ElasticsearchTemplate elasticSearchTemplate,
			SearchResultMapper searchResultMapper) {
		this.elasticSearchTemplate = elasticSearchTemplate;
		this.searchResultMapper = searchResultMapper;
	}
	
    @Override
	public List<ScientificPaper> searchSimple(String query) {
    	SearchQuery searchQuery = buildSimpleSearchQuery(query);
    	
    	logger.info(searchQuery.getQuery().toString());
    	
    	Page<ScientificPaper> searchResults = elasticSearchTemplate.queryForPage(searchQuery, ScientificPaper.class, searchResultMapper);
		
    	return searchResults.getContent();
    }

	@Override
	public List<ScientificPaper> searchAdvanced(AdvancedSearchParams advancedSearchData) {
    	HashMap<String, FieldQueryParams> queryParamsForFields = new HashMap<String, FieldQueryParams>();
    	
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.TITLE.getFieldName(), advancedSearchData.title);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.ABSTRACT.getFieldName(), advancedSearchData.anAbstract);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.KEYWORDS.getFieldName(), advancedSearchData.keywords);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.TEXT.getFieldName(), advancedSearchData.text);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.AUTHOR.getFieldName(), advancedSearchData.authorName);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.CATEGORY.getFieldName(), advancedSearchData.category);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.PUBLISH_DATE.getFieldName(), advancedSearchData.publishDate);
    	
    	if (queryParamsForFields.isEmpty()) {
    		throw new IllegalArgumentException(String.format("%s \n doesn't have any populated query strings.", advancedSearchData.toString()));
    	}
    	
    	SearchQuery searchQuery = buildAdvancedSearchQuery(queryParamsForFields);
    	
    	logger.info(searchQuery.getQuery().toString());
    	
		Page<ScientificPaper> searchResults = elasticSearchTemplate.queryForPage(searchQuery, ScientificPaper.class, searchResultMapper);
		
		return searchResults.getContent();
    }
	
	@Override
	public List<ScientificPaper> searchMoreLikeThis(String documentId) {
		Item moreLikeThisItem = new MoreLikeThisQueryBuilder.Item(ScientificPaper.INDEX_NAME, ScientificPaper.TYPE_NAME, documentId);
		SearchQuery searchQuery = buildMoreLikeThisSearchQuery(moreLikeThisItem);
		
		Page<ScientificPaper> searchResults = elasticSearchTemplate.queryForPage(searchQuery, ScientificPaper.class, searchResultMapper);
		
		return searchResults.getContent();
	}
	
	private void addIfValidParams(HashMap<String, FieldQueryParams> queryParamsForFields, String fieldName,
			FieldQueryParams fieldQueryParams) {
		if (isFieldParamsValid(fieldQueryParams)) {
			queryParamsForFields.put(fieldName, fieldQueryParams);
		}
	}

	private boolean isFieldParamsValid(FieldQueryParams fieldQueryParams) {
		if (fieldQueryParams == null) {
			return false;
		}
		
		return fieldQueryParams.isValid();
	}

}
