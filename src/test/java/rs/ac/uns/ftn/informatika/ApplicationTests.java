package rs.ac.uns.ftn.informatika;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.fuzzyQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.prefixQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import rs.ac.uns.ftn.informatika.model.ScientificPaper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private ElasticsearchOperations esOperations;

	@Before
	public void before() {
		esOperations.deleteIndex(ScientificPaper.class);
		esOperations.createIndex(ScientificPaper.class);
		esOperations.putMapping(ScientificPaper.class);
		
	}
	
	
	public void simpleSearchTest() {
		TermQueryBuilder termQuery = termQuery("title", "programming");
		
	}
	
	@Test
	public void test() {
		ScientificPaper paper1 = ScientificPaper.builder().id(1L).title("Javascript Programming").anAbstract("ab1").keywords("kw1 kw2 kw3")
				.categoryName("default_category").text("some text of the paper").publishDate("2017-01-01")
				.authorName("default_author").numberOfImages(5).fileName("paper1.pdf").build();

		
		IndexQuery indexQuery = new IndexQueryBuilder().withObject(paper1)
				.withIndexName("test-paper-test").withId(String.valueOf(paper1.getId()))
				.withType("paper-test-type").build();
		
		esOperations.index(indexQuery);
		esOperations.refresh("test-paper-test");
		
		BoolQueryBuilder queryBuilder = boolQuery();
		queryBuilder.must(termQuery("title", "t1")); // exact term
		queryBuilder.must(matchQuery("title", "t1")); // analyzed query
		queryBuilder.should(prefixQuery("", ""));
		queryBuilder.mustNot(fuzzyQuery("", ""));
		queryBuilder.should(rangeQuery("").from(10).to(20));
		queryBuilder.must(matchPhraseQuery("", "")); // phrase query
		
		// TODO: Check how to implement suggestions and more like this with ES
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(queryBuilder).build();
		
		
		List<String> ids = esOperations.queryForIds(searchQuery);
		
		assertThat(ids.size(), is(1));
	}

}
