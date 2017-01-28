package rs.ac.uns.ftn.informatika.service.impl;

import static rs.ac.uns.ftn.informatika.builder.scientificpaper.SearchQueryBuilder.buildAdvancedSearchQuery;
import static rs.ac.uns.ftn.informatika.builder.scientificpaper.SearchQueryBuilder.buildSimpleSearchQuery;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.dto.AdvancedSearchData;
import rs.ac.uns.ftn.informatika.model.FieldQueryParams;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.model.ScientificPaperFieldName;
import rs.ac.uns.ftn.informatika.service.ScientificPaperSearcher;

@Service
public class ScientificPaperSearcherImpl implements ScientificPaperSearcher {
	
	private static final Logger logger = LoggerFactory.getLogger(ScientificPaperSearcherImpl.class);

    private ElasticsearchTemplate elasticSearchTemplate;

	@Autowired
    public ScientificPaperSearcherImpl(ElasticsearchTemplate elasticSearchTemplate) {
		this.elasticSearchTemplate = elasticSearchTemplate;
	}
	
    @Override
	public List<ScientificPaper> searchSimple(String query) {
    	SearchQuery searchQuery = buildSimpleSearchQuery(query);
    	
    	logger.info(searchQuery.toString());
		
    	return elasticSearchTemplate.queryForList(searchQuery, ScientificPaper.class);
    }

	@Override
	public List<ScientificPaper> searchAdvanced(AdvancedSearchData advancedSearchData) {
    	HashMap<String, FieldQueryParams> queryParamsForFields = new HashMap<String, FieldQueryParams>();
    	
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.TITLE.getFieldName(), advancedSearchData.titleParams);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.ABSTRACT.getFieldName(), advancedSearchData.anAbstractParams);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.KEYWORDS.getFieldName(), advancedSearchData.keywordsParams);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.TEXT.getFieldName(), advancedSearchData.textParams);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.AUTHOR.getFieldName(), advancedSearchData.authorNameParams);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.CATEGORY.getFieldName(), advancedSearchData.categoryParams);
    	addIfValidParams(queryParamsForFields, ScientificPaperFieldName.PUBLISH_DATE.getFieldName(), advancedSearchData.publishDateParams);
    	
    	SearchQuery searchQuery = buildAdvancedSearchQuery(queryParamsForFields);
    	
    	logger.info(searchQuery.toString());
    	
		return elasticSearchTemplate.queryForList(searchQuery, ScientificPaper.class);
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
