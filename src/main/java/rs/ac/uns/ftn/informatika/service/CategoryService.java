package rs.ac.uns.ftn.informatika.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.entity.Category;
import rs.ac.uns.ftn.informatika.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        return categoryRepository.saveAndFlush(category);
    }

    public void deleteCategory(Integer categoryId) {
        categoryRepository.delete(categoryId);
    }
}
