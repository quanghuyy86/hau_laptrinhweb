package vn.edu.hau.cake.controller.customer;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.edu.hau.cake.model.Product;
import vn.edu.hau.cake.service.ProductService;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String Home(final Model model) throws IOException{
        List<Product> productList1 = productService.findByCategoryId1();
        List<Product> productList2 = productService.findByCategoryId2();
        List<Product> productList3 = productService.findByCategoryId3();
        List<Product> productList4 = productService.findByCategoryId4();
        List<Product> productList5 = productService.findByCategoryId5();
        model.addAttribute("product1", productList1);
        model.addAttribute("product2", productList2);
        model.addAttribute("product3", productList3);
        model.addAttribute("product4", productList4);
        model.addAttribute("product5", productList5);
        return "home";
    }

    @GetMapping("/")
    public String home(Principal principal) {
        if (principal != null) {
            Authentication authentication = (Authentication) principal;
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                return "redirect:/admin/productlist";
            }
        }
        return "redirect:/home";
    }

}
