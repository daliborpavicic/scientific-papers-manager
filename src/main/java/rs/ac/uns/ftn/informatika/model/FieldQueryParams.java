package rs.ac.uns.ftn.informatika.model;

public class FieldQueryParams {

	private String queryString;

	private QueryType queryType = QueryType.DEFAULT;

	private BoolQueryOccurrence boolOccurrence = BoolQueryOccurrence.MUST;

	public FieldQueryParams() {
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
	
	public boolean isNotEmpty() {
		return !this.queryString.isEmpty();
	}

}
