package rs.ac.uns.ftn.informatika.model;

public enum ScientificPaperFieldName {
	TITLE("title"),
	ABSTRACT("anAbstract"),
	KEYWORDS("keywords"),
	CATEGORY("categoryName"),
	TEXT("text"),
	PUBLISH_DATE("publishDate"),
	AUTHOR("authorName");
	
    public static final String[] scientificPaperQueryFields = {
    		TITLE.getFieldName(),
    		ABSTRACT.getFieldName(),
    		KEYWORDS.getFieldName(),
    		CATEGORY.getFieldName(),
    		TEXT.getFieldName(),
    		PUBLISH_DATE.getFieldName(),
    		AUTHOR.getFieldName()
    };
	
	private String fieldName;

	private ScientificPaperFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}
	
}
