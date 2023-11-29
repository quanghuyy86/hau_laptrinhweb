package vn.edu.hau.teddy.controller.administrator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.edu.hau.teddy.model.Categories;
import vn.edu.hau.teddy.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminCategoryController {

    private final CategoryService categoryService;

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/admin/addcategory", method = RequestMethod.GET)
    public String addCategory(final Model model){
        try {
            model.addAttribute("category", new Categories());
            return "admin/category/addcategory";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error/error";
        }
    }

    @RequestMapping(value = "/admin/addcategory", method = RequestMethod.POST)
    public String addCategory_(final Model model,
                               final @ModelAttribute("category") Categories categories){
        try {
            categoryService.save(categories);
            return "redirect:/admin/categorieslist";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error/error";
        }

    }
    @RequestMapping(value = "/admin/categorieslist", method = RequestMethod.GET)
    public String categoriesList(final Model model){
        try {
            List<Categories> categoriesList = categoryService.findAll();
            model.addAttribute("categories", categoriesList);
            return "admin/category/categorieslist";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error/error";
        }
    }
    @RequestMapping(value = "/admin/categoryedit/{id}", method = RequestMethod.GET)
    public String categoryEdit(final Model model,
                               final @PathVariable("id") Integer id){
        try {
            Optional<Categories> categories = categoryService.findById(id);
            model.addAttribute("categoy", categories);
            return "admin/category/categoryedit";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error/error";
        }
    }
    @RequestMapping(value = "/admin/categoryedit/{id}", method = RequestMethod.POST)
    public String categoryUpdate(final Model model,
                                 final @PathVariable("id") Integer id,
                                 final Categories categories){
        try {
            categoryService.updateCategory(id, categories);
            return "redirect:/admin/categorieslist";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error/error";
        }
    }

    @RequestMapping(value = "/admin/deletecategory/{id}", method = RequestMethod.GET)
    String deleteProduct(final @PathVariable("id") Integer id,
                         final Model model){

        try {
            categoryService.deleteById(id);
            return "redirect:/admin/categorieslist";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error/error";
        }
    }


}
