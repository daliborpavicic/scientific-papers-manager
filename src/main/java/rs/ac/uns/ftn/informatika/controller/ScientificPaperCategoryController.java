package rs.ac.uns.ftn.informatika.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.entity.ScientificPaperCategory;
import rs.ac.uns.ftn.informatika.service.ScientificPaperCategoryService;

import java.util.List;

@RestController
@RequestMapping("/papers/")
public class ScientificPaperCategoryController {

    private ScientificPaperCategoryService scientificPaperCategoryService;

    @Autowired
    public ScientificPaperCategoryController(ScientificPaperCategoryService scientificPaperCategoryService) {
        this.scientificPaperCategoryService = scientificPaperCategoryService;
    }

    @RequestMapping(value = "categories", method = RequestMethod.GET)
    public List<ScientificPaperCategory> list() {
        return scientificPaperCategoryService.findAllCategories();
    }

    @RequestMapping(value = "categories", method = RequestMethod.POST)
    public ScientificPaperCategory create(@RequestBody ScientificPaperCategory scientificPaperCategory) {
        return scientificPaperCategoryService.createCategory(scientificPaperCategory);
    }

    @RequestMapping(value = "categories/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        scientificPaperCategoryService.deleteCategory(id);
    }

}
