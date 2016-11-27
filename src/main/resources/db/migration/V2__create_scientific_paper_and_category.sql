CREATE TABLE scientific_paper_category (
	category_id INT AUTO_INCREMENT,
	category_name VARCHAR(255),
	PRIMARY KEY (category_id)
);

CREATE TABLE scientific_paper (
	paper_id INT AUTO_INCREMENT,
	paper_title VARCHAR(255),
	paper_abstract VARCHAR (2000),
	ref_category_id INT,
	paper_publish_date TIMESTAMP,
	paper_file_path VARCHAR (255),
	paper_elastic_id VARCHAR (255),
	PRIMARY KEY (paper_id),
	FOREIGN KEY (ref_category_id) REFERENCES scientific_paper_category(category_id)
);