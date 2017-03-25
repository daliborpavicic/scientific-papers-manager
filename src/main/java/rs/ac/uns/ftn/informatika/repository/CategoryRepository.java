package rs.ac.uns.ftn.informatika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Category findByName(String name);
}
