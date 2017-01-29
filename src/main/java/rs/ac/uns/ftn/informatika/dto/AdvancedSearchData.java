package rs.ac.uns.ftn.informatika.dto;

import rs.ac.uns.ftn.informatika.model.FieldQueryParams;

public class AdvancedSearchData {
	public FieldQueryParams titleParams;
	public FieldQueryParams anAbstractParams;
	public FieldQueryParams keywordsParams;
	public FieldQueryParams textParams;
	public FieldQueryParams authorNameParams;
	public FieldQueryParams categoryParams;
	public FieldQueryParams publishDateParams;
	
	@Override
	public String toString() {
		return "AdvancedSearchData [titleParams=" + titleParams + ", anAbstractParams=" + anAbstractParams
				+ ", keywordsParams=" + keywordsParams + ", textParams=" + textParams + ", authorNameParams="
				+ authorNameParams + ", categoryParams=" + categoryParams + ", publishDateParams=" + publishDateParams
				+ "]";
	}
	
}
