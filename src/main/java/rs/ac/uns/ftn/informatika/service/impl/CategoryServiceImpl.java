package rs.ac.uns.ftn.informatika.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.model.Category;
import rs.ac.uns.ftn.informatika.repository.CategoryRepository;
import rs.ac.uns.ftn.informatika.service.CategoryService;
import rs.ac.uns.ftn.informatika.service.TestDataGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @Override
	public void init() {
    	List<Category> categories = Arrays.asList(TestDataGenerator.ALL_CATEGORIES).stream().map((categoryName) -> {
    		return new Category(categoryName);
    	}).collect(Collectors.toList());
    	
    	categoryRepository.save(categories);
    	
    	logger.info(String.format("Succesfully saved %d categories to database", categories.size()));
    }

    @Override
	public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
	public Category createCategory(Category category) {
        return categoryRepository.saveAndFlush(category);
    }

	@Override
	public Category findCategoryByName(String categoryName) {
		return categoryRepository.findByName(categoryName);
	}

    @Override
	public void deleteCategory(Integer categoryId) {
        categoryRepository.delete(categoryId);
    }

	@Override
	public void deleteAll() {
		categoryRepository.deleteAll();
	}
    
}
