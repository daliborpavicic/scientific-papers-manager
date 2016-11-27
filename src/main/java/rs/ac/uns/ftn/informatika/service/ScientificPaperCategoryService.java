package rs.ac.uns.ftn.informatika.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.entity.ScientificPaperCategory;
import rs.ac.uns.ftn.informatika.repository.ScientificPaperCategoryRepository;

import java.util.List;

@Service
public class ScientificPaperCategoryService {

    private ScientificPaperCategoryRepository scientificPaperCategoryRepository;

    @Autowired
    public ScientificPaperCategoryService(ScientificPaperCategoryRepository scientificPaperCategoryRepository) {
        this.scientificPaperCategoryRepository = scientificPaperCategoryRepository;
    }

    public List<ScientificPaperCategory> findAllCategories() {
        return scientificPaperCategoryRepository.findAll();
    }

    public ScientificPaperCategory createCategory(ScientificPaperCategory scientificPaperCategory) {
        return scientificPaperCategoryRepository.saveAndFlush(scientificPaperCategory);
    }

    public void deleteCategory(Integer categoryId) {
        scientificPaperCategoryRepository.delete(categoryId);
    }
}
