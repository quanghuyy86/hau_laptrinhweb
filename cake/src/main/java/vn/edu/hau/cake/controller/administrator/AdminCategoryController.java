package vn.edu.hau.cake.controller.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.edu.hau.cake.model.Categories;
import vn.edu.hau.cake.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping(value = "/admin/addcategory", method = RequestMethod.GET)
    public String addCategory(final Model model){
        model.addAttribute("category", new Categories());
        return "addcategory";
    }
    @RequestMapping(value = "/admin/addcategory", method = RequestMethod.POST)
    public String addCategory_(final Model model,
                               final @ModelAttribute("category") Categories categories){
        categoryService.save(categories);

        return "redirect:/admin/categorieslist";
    }
    @RequestMapping(value = "/admin/categorieslist", method = RequestMethod.GET)
    public String categoriesList(final Model model){
        List<Categories> categoriesList = categoryService.findAll();
        model.addAttribute("categories", categoriesList);
        return "categorieslist";
    }
    @RequestMapping(value = "/admin/categoryedit/{id}", method = RequestMethod.GET)
    public String categoryEdit(final Model model,
                               final @PathVariable("id") Integer id){
        Optional<Categories> categories = categoryService.findById(id);
        model.addAttribute("categoy", categories);
        return "categoryedit";
    }
    @RequestMapping(value = "/admin/categoryedit/{id}", method = RequestMethod.POST)
    public String categoryUpdate(final Model model,
                                 final @PathVariable("id") Integer id,
                                 final Categories categories){
        categoryService.updateCategory(id, categories);
        return "redirect:/admin/categorieslist";
    }

    @RequestMapping(value = "/admin/deletecategory/{id}", method = RequestMethod.GET)
    String deleteProduct(final @PathVariable("id") Integer id){
        categoryService.deleteById(id);
        return "redirect:/admin/categorieslist";
    }


}
