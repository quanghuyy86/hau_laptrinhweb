package vn.edu.hau.cake.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.edu.hau.cake.model.Product;
import vn.edu.hau.cake.service.ProductService;

import java.util.List;
import java.util.Optional;

@Controller
public class DetailController {
    @Autowired
    private ProductService productService;
    @RequestMapping(value = "/detailproduct/{id}", method = RequestMethod.GET)
    public String detailProduct(final Model model,
                                final @PathVariable("id") Integer id){
        Optional<Product> detailProduct = productService.findById(id);
        List<Product> product4 = productService.findByCategoryId4();
        model.addAttribute("product", detailProduct);
        model.addAttribute("product4", product4);
        return "detail";
    }

}
