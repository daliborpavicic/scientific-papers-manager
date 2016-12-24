package rs.ac.uns.ftn.informatika.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import rs.ac.uns.ftn.informatika.entity.ScientificPaper;

public interface ScientificPaperRepository extends ElasticsearchRepository<ScientificPaper, Integer> {
}
