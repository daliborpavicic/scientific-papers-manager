package rs.ac.uns.ftn.informatika.model;

public enum ScientificPaperFieldName {
	TITLE("title"),
	ABSTRACT("anAbstract"),
	KEYWORDS("keywords"),
	CATEGORY("categoryName"),
	TEXT("text"),
	PUBLISH_DATE("publishDate"),
	AUTHOR("authorName");
	
    public static final String[] queryFieldNames = {
    		TITLE.getFieldName(),
    		ABSTRACT.getFieldName(),
    		KEYWORDS.getFieldName(),
    		CATEGORY.getFieldName(),
    		TEXT.getFieldName(),
    		PUBLISH_DATE.getFieldName(),
    		AUTHOR.getFieldName()
    };
    
    public static final String[] simpleSearchFieldNames = {
    		TITLE.getFieldName(),
    		ABSTRACT.getFieldName(),
    		KEYWORDS.getFieldName(),
    		CATEGORY.getFieldName(),
    		TEXT.getFieldName(),
    		AUTHOR.getFieldName()
    };

    public static final String[] highlightFieldNames = {
    		ABSTRACT.getFieldName(),
    		TEXT.getFieldName()
    };
	
	private String fieldName;

	private ScientificPaperFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}
	
}
