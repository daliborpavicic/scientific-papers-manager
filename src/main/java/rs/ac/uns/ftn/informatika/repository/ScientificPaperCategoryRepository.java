package rs.ac.uns.ftn.informatika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.entity.ScientificPaperCategory;

public interface ScientificPaperCategoryRepository extends JpaRepository<ScientificPaperCategory, Integer> {
}
