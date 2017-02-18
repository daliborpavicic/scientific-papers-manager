package rs.ac.uns.ftn.informatika.configuration;

import static rs.ac.uns.ftn.informatika.utils.FilePathConstants.ELASTIC_SEARCH_INDEX_FOLDER;

import java.net.InetSocketAddress;

import javax.annotation.Resource;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
@PropertySource(value = "classpath:elasticsearch.properties")
public class ElasticsearchConfiguration {

	@Resource
	private Environment environment;

//	@Bean
	/**
	 * Configures a transport client for the Elastic search cluster.
	 * This configuration should be used when the Elastic search cluster is already up and running
	 * 
	 * @return {@link Client}
	 */
	public Client transportClient() {
		String host = environment.getProperty("elasticsearch.host");
		int port = Integer.parseInt(environment.getProperty("elasticsearch.port"));
		
		TransportClient transportClient = new TransportClient.Builder().build();
		transportClient.addTransportAddress(
				new InetSocketTransportAddress(new InetSocketAddress(host, port)));

		return transportClient;
	}
	
	/**
	 * Starts the local Elastic search node and obtains a client from it.
	 * It will only look for other cluster nodes within the same JVM process
	 * 
	 * @return {@link Client}
	 */
	@Bean
	public Client nodeClient() {
		Settings settings = Settings.builder()
				.put("path.home", ELASTIC_SEARCH_INDEX_FOLDER)
				.build();
		
		final Node node = new NodeBuilder()
				.settings(settings)
				.local(true)
		        .build().start();
		
		return node.client();
	}
	
	@Bean
	public ElasticsearchTemplate elasticsearchTemplate() {
		return new ElasticsearchTemplate(nodeClient());
	}

}
