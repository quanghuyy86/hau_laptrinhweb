package vn.edu.hau.teddy.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.edu.hau.teddy.model.Product;
import vn.edu.hau.teddy.service.ProductService;

import java.util.List;
import java.util.Optional;

@Controller
public class DetailController {

    private final ProductService productService;

    public DetailController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/detailproduct/{id}", method = RequestMethod.GET)
    public String detailProduct(final Model model,
                                final @PathVariable("id") Integer id){
        Optional<Product> detailProduct = productService.findById(id);
        List<Product> product4 = productService.findByCategoryId4();
        model.addAttribute("product", detailProduct);
        model.addAttribute("product4", product4);
        return "home/detail";
    }

}
