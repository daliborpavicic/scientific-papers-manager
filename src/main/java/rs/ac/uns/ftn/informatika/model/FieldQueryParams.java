package rs.ac.uns.ftn.informatika.model;

import static rs.ac.uns.ftn.informatika.model.BoolQueryOccurrence.MUST;
import static rs.ac.uns.ftn.informatika.model.QueryType.DEFAULT;

public class FieldQueryParams {

	private String queryString;

	private QueryType queryType;

	private BoolQueryOccurrence boolOccurrence;

	public FieldQueryParams() {
		this("", DEFAULT, MUST);
	}
	
	public FieldQueryParams(String queryString) {
		this(queryString, DEFAULT, MUST);
	}

	public FieldQueryParams(String queryString, QueryType queryType, BoolQueryOccurrence boolOccurrence) {
		this.queryString = queryString;
		this.queryType = queryType;
		this.boolOccurrence = boolOccurrence;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public BoolQueryOccurrence getBoolOccurrence() {
		return boolOccurrence;
	}

	public void setBoolOccurrence(BoolQueryOccurrence boolOccurrence) {
		this.boolOccurrence = boolOccurrence;
	}

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}
	
	public boolean isValid() {
		return !this.queryString.isEmpty();
	}

}
