package rs.ac.uns.ftn.informatika.dto;

import rs.ac.uns.ftn.informatika.model.FieldQueryParams;

public class AdvancedSearchParams {
	public FieldQueryParams title;
	public FieldQueryParams anAbstract;
	public FieldQueryParams keywords;
	public FieldQueryParams text;
	public FieldQueryParams authorName;
	public FieldQueryParams category;
	public FieldQueryParams publishDate;
	
	@Override
	public String toString() {
		return "AdvancedSearchData [titleParams=" + title + ", anAbstractParams=" + anAbstract
				+ ", keywordsParams=" + keywords + ", textParams=" + text + ", authorNameParams="
				+ authorName + ", categoryParams=" + category + ", publishDateParams=" + publishDate
				+ "]";
	}
	
}
