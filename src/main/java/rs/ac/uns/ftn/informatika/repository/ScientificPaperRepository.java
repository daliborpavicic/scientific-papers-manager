package rs.ac.uns.ftn.informatika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.entity.ScientificPaper;

public interface ScientificPaperRepository extends JpaRepository<ScientificPaper, Integer> {
}
