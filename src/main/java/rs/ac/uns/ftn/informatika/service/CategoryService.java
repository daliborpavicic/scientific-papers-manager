package rs.ac.uns.ftn.informatika.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.model.Category;

public interface CategoryService {

	void init();
	
	void deleteAll();

	List<Category> findAllCategories();

	Category createCategory(Category category);

	void deleteCategory(Integer categoryId);

	Category findCategoryByName(String categoryName);

}