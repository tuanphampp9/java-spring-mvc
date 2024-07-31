package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class ItemController {

    private final ProductService productService;
    
    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getDetailProductPage(Model model, @PathVariable int id){
        Product product = productService.handleGetProductById(id).get();
        model.addAttribute("product", product);
        return "client/product/detail";
    }
    
    
}
