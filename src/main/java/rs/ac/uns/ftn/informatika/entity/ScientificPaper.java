package rs.ac.uns.ftn.informatika.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "papers", type = "scientific-paper", shards = 1, replicas = 0)
public class ScientificPaper {

	@Id
	Long id;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	String title;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	String anAbstract;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	String keywords;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	String categoryName;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = false)
	String text;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	String publishDate; // TODO: Change field type to date after date formatting
						// is implemented

	@Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
	String authorName;

	@Field(type = FieldType.Integer, index = FieldIndex.not_analyzed, store = true)
	Integer numberOfImages;

	@Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
	String fileName;

	public static Builder builder() {
		return new Builder();
	}

	public Long getId() {
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

	public String getPublishDate() {
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
	}

	public static class Builder {
		private Long id;
		private String title;
		private String anAbstract;
		private String keywords;
		private String categoryName;
		private String text;
		private String publishDate;
		private String authorName;
		private Integer numberOfImages;
		private String fileName;

		public Builder id(Long id) {
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

		public Builder publishDate(String publishDate) {
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

		public ScientificPaper build() {
			return new ScientificPaper(this);
		}
	}

}
