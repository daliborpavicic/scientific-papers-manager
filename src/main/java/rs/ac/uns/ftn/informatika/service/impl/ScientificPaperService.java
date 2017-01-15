package rs.ac.uns.ftn.informatika.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;
import java.util.HashMap;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.dto.AdvancedSearchData;
import rs.ac.uns.ftn.informatika.dto.NewScientificPaper;
import rs.ac.uns.ftn.informatika.model.FieldQueryParams;
import rs.ac.uns.ftn.informatika.model.ScientificPaper;
import rs.ac.uns.ftn.informatika.model.ScientificPaperFieldName;
import rs.ac.uns.ftn.informatika.repository.ScientificPaperRepository;

@Service
public class ScientificPaperService {

    private ScientificPaperRepository scientificPaperRepository;

    @Autowired
    public ScientificPaperService(ScientificPaperRepository scientificPaperRepository) {
        this.scientificPaperRepository = scientificPaperRepository;
    }

    public ScientificPaper index(NewScientificPaper newScientificPaper) {
    	ScientificPaper documentToIndex = convertToDomainEntity(newScientificPaper);

    	return scientificPaperRepository.index(documentToIndex);
    }
    
    public Iterable<ScientificPaper> findAll() {
    	return scientificPaperRepository.findAll();
    }
    
    public void deleteAll() {
    	scientificPaperRepository.deleteAll();
    }
    
    public Iterable<ScientificPaper> searchSimple(String query) {
    	QueryStringQueryBuilder queryStringQuery = queryStringQuery(query);
    	queryStringQuery.defaultOperator(Operator.AND);
    	
		return scientificPaperRepository.search(queryStringQuery);
    }
    
    public Iterable<ScientificPaper> searchAdvanced(AdvancedSearchData advancedSearchData) {
    	HashMap<String, FieldQueryParams> queryParamsForFields = new HashMap<String, FieldQueryParams>();
    	
    	queryParamsForFields.put(ScientificPaperFieldName.TITLE.getFieldName(), advancedSearchData.titleParams);
    	queryParamsForFields.put(ScientificPaperFieldName.ABSTRACT.getFieldName(), advancedSearchData.anAbstractParams);
    	queryParamsForFields.put(ScientificPaperFieldName.KEYWORDS.getFieldName(), advancedSearchData.keywordsParams);
    	queryParamsForFields.put(ScientificPaperFieldName.TEXT.getFieldName(), advancedSearchData.textParams);
    	queryParamsForFields.put(ScientificPaperFieldName.AUTHOR.getFieldName(), advancedSearchData.authorNameParams);
    	
    	BoolQueryBuilder boolQueryBuilder = buildAdvancedSearchQuery(queryParamsForFields);
    	
		return scientificPaperRepository.search(boolQueryBuilder);
    }
    
	public BoolQueryBuilder buildAdvancedSearchQuery(HashMap<String, FieldQueryParams> queryParamsForFields) {
		BoolQueryBuilder boolQuery = boolQuery();

		for (String fieldName : queryParamsForFields.keySet()) {
			FieldQueryParams queryParamsForSingleField = queryParamsForFields.get(fieldName);
			
			switch (queryParamsForSingleField.getBoolOccurrence()) {
			case MUST:
				boolQuery.must(getQueryBuilderForField(fieldName, queryParamsForSingleField));
				break;
			case MUST_NOT:
				boolQuery.mustNot(getQueryBuilderForField(fieldName, queryParamsForSingleField));
				break;
			case SHOULD:
				boolQuery.should(getQueryBuilderForField(fieldName, queryParamsForSingleField));
				break;
			}
		}
		
		return boolQuery;
	}
    
    public QueryBuilder getQueryBuilderForField(String fieldName, FieldQueryParams fieldQueryParams) {
    	QueryBuilder queryBuilder = null;
    	String queryString = fieldQueryParams.getQueryString();
    	
    	switch (fieldQueryParams.getQueryType()) {
		case DEFAULT:
			queryBuilder = termQuery(fieldName, queryString);
			break;
		case FUZZY:
			queryBuilder = fuzzyQuery(fieldName, queryString);
			break;
		case PHRASE:
			queryBuilder = matchPhraseQuery(fieldName, queryString);
			break;
		case PREFIX:
			queryBuilder = prefixQuery(fieldName, queryString);
			break;
		case RANGE:
			// TODO: implement the logic for a range query
			break;
		case WILDCARD:
			queryBuilder = wildcardQuery(fieldName, queryString);
			break;
		default:
			break;
		}
    	
    	return queryBuilder;
    }
    
    public ScientificPaper convertToDomainEntity(NewScientificPaper newScientificPaper) {
    	ScientificPaper domainEntity = ScientificPaper.builder()
    		.title(newScientificPaper.title)
    		.anAbstract(newScientificPaper.anAbstract)
    		.keywords(newScientificPaper.keywords)
    		.categoryName("default_category") // TODO: Retrieve the category name from DB. Category ID will be provided in DTO which comes from the client
    		.text(newScientificPaper.text)
    		.publishDate("2017-01-01") // TODO: Set date to current date string
    		.authorName("default_author") // TODO: Set author name to the name of currently logged user after security is implemented
    		.numberOfImages(newScientificPaper.numberOfImages)
    		.fileName(newScientificPaper.fileName)
    		.build();
    	
    	return domainEntity;
    }
}
