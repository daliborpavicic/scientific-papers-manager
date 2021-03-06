package rs.ac.uns.ftn.informatika.controller.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.informatika.model.Category;
import rs.ac.uns.ftn.informatika.service.CategoryService;

import static rs.ac.uns.ftn.informatika.controller.HomeController.BASE_API_URL;

import java.util.List;

@RestController
@RequestMapping(BASE_API_URL + "/paper")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "categories", method = RequestMethod.GET)
    public List<Category> list() {
        return categoryService.findAllCategories();
    }

    @RequestMapping(value = "categories", method = RequestMethod.POST)
    public Category create(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @RequestMapping(value = "categories/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }

}
