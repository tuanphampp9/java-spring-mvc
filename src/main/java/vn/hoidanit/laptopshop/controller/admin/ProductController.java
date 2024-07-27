package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;



@Controller
public class ProductController {
    private final UploadService uploadService;
    private final ProductService productService;
    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }
    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.handleGetAllProduct();
        model.addAttribute("products", products);
        return "admin/product/show";
    }
    @GetMapping("/admin/product/create")
    public String createProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/createProduct";
    }

    @PostMapping(value = "/admin/product/create")
    public String createProductPage(Model model,
     @ModelAttribute("newProduct") Product newProduct,
     @RequestParam("productFile") MultipartFile file
     ){
        if(!file.isEmpty()){
            //handle save file
            String productName = this.uploadService.handleSaveUploadFile(file, "product");
            newProduct.setImage(productName);
        }
        this.productService.handleSaveProduct(newProduct);
        return "redirect:/admin/product";
    }
    
    
}
