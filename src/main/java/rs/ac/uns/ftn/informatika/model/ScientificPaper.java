package rs.ac.uns.ftn.informatika.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(indexName = ScientificPaper.INDEX_NAME, type = ScientificPaper.TYPE_NAME, shards = 1, replicas = 0)
public class ScientificPaper {

	public static final String INDEX_NAME = "papers";
	public static final String TYPE_NAME = "scientific-paper";
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	@Id
	String id;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	String title;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	String anAbstract;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	String keywords;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	String categoryName;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	String text;

	@Field(type = FieldType.Date, format = DateFormat.year_month_day, index = FieldIndex.not_analyzed, store = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
	Date publishDate;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	String authorName;

	@Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
	Integer numberOfImages;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	String fileName;
	
	String highlightedText;

	public static Builder builder() {
		return new Builder();
	}
	
	public ScientificPaper() {}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAnAbstract() {
		return anAbstract;
	}

	public String getKeywords() {
		return keywords;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getText() {
		return text;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public String getAuthorName() {
		return authorName;
	}

	public Integer getNumberOfImages() {
		return numberOfImages;
	}

	public String getFileName() {
		return fileName;
	}
	
	public String getHighlightedText() {
		return highlightedText;
	}

	private ScientificPaper(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.anAbstract = builder.anAbstract;
		this.keywords = builder.keywords;
		this.categoryName = builder.categoryName;
		this.text = builder.text;
		this.publishDate = builder.publishDate;
		this.authorName = builder.authorName;
		this.numberOfImages = builder.numberOfImages;
		this.fileName = builder.fileName;
		this.highlightedText = builder.highlightedText;
	}

	public static class Builder {
		private String id;
		private String title;
		private String anAbstract;
		private String keywords;
		private String categoryName;
		private String text;
		private Date publishDate;
		private String authorName;
		private Integer numberOfImages;
		private String fileName;
		private String highlightedText;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder anAbstract(String anAbstract) {
			this.anAbstract = anAbstract;
			return this;
		}

		public Builder keywords(String keywords) {
			this.keywords = keywords;
			return this;
		}

		public Builder categoryName(String categoryName) {
			this.categoryName = categoryName;
			return this;
		}

		public Builder text(String text) {
			this.text = text;
			return this;
		}

		public Builder publishDate(Date publishDate) {
			this.publishDate = publishDate;
			return this;
		}

		public Builder authorName(String authorName) {
			this.authorName = authorName;
			return this;
		}

		public Builder numberOfImages(Integer numberOfImages) {
			this.numberOfImages = numberOfImages;
			return this;
		}

		public Builder fileName(String fileName) {
			this.fileName = fileName;
			return this;
		}
		
		public Builder highlightedText(String highlightedText) {
			this.highlightedText = highlightedText;
			return this;
		}

		public ScientificPaper build() {
			return new ScientificPaper(this);
		}
	}

}
