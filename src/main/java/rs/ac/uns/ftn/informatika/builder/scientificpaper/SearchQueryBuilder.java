package rs.ac.uns.ftn.informatika.builder.scientificpaper;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.fuzzyQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.prefixQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import java.util.HashMap;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import rs.ac.uns.ftn.informatika.model.FieldQueryParams;
import rs.ac.uns.ftn.informatika.model.ScientificPaperFieldName;

public class SearchQueryBuilder {

	private static final String RANGE_QUERY_REGEX = "\\s?\\|\\s?";

	public static SearchQuery buildSimpleSearchQuery(String query) {
		QueryStringQueryBuilder stringQuery = buildSimpleStringQuery(query);
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(stringQuery)
				.withHighlightFields(getHighlightFields())
				.build();

		return searchQuery;
	}

	public static SearchQuery buildAdvancedSearchQuery(HashMap<String, FieldQueryParams> queryParamsForFields) {
		BoolQueryBuilder booleanQuery = buildBooleanQuery(queryParamsForFields);
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(booleanQuery)
				.withHighlightFields(getHighlightFields())
				.build();

		return searchQuery;
	}

	private static QueryStringQueryBuilder buildSimpleStringQuery(String query) {
		QueryStringQueryBuilder queryStringQuery = queryStringQuery(query);
		queryStringQuery.defaultOperator(Operator.AND);
		
		for (String fieldName : ScientificPaperFieldName.queryFieldNames) {
			queryStringQuery.field(fieldName);
		}
		
		return queryStringQuery;
	}

	private static BoolQueryBuilder buildBooleanQuery(HashMap<String, FieldQueryParams> queryParamsForFields) {
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

	private static QueryBuilder getQueryBuilderForField(String fieldName, FieldQueryParams fieldQueryParams) {
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
			String[] queryTokens = queryString.split(RANGE_QUERY_REGEX);
			boolean isValidQueryString = queryTokens.length == 2;
			
			if (isValidQueryString) {
				String fromString = queryTokens[0];
				String toString = queryTokens[1];
				
				queryBuilder = rangeQuery(fieldName).gte(fromString).lte(toString);
			} else {
				throw new IllegalArgumentException(String.format("%s is not valid range query. Required format is fromString|toString", queryString));
			}
			
			break;
		case WILDCARD:
			queryBuilder = wildcardQuery(fieldName, queryString);
			break;
		default:
			break;
		}

		return queryBuilder;
	}
	
	private static HighlightBuilder.Field[] getHighlightFields() {
		int highlightFieldsCount = ScientificPaperFieldName.highlightFieldNames.length;
		HighlightBuilder.Field[] highlightFields = new HighlightBuilder.Field[highlightFieldsCount];
		
		for (int i = 0; i < highlightFieldsCount; i++) {
			highlightFields[i] = new HighlightBuilder.Field(ScientificPaperFieldName.highlightFieldNames[i]);
		}
		
		return highlightFields;
	}
	
}
