package rs.ac.uns.ftn.informatika.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "papers", type = "scientific-paper", shards = 1, replicas = 0)
public class ScientificPaper {
	
	@Id
    Long id;

    String title;

    String anAbstract;

    String keywords;

    public ScientificPaper() {
    }
}
