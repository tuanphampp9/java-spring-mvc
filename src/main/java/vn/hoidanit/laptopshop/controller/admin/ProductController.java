package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
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
    public String getProductPage(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page-1, 4);
        Page<Product> products = this.productService.handleGetAllProduct("",pageable);
        List<Product> listProduct = products.getContent();
        model.addAttribute("products", listProduct);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "admin/product/show";
    }
    @GetMapping("/admin/product/create")
    public String createProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/createProduct";
    }

    @PostMapping(value = "/admin/product/create")
    public String createProductPage(Model model,
     @ModelAttribute("newProduct") @Valid Product newProduct,
     BindingResult newProductBindingResult,
     @RequestParam("productFile") MultipartFile file
     ){
        if(!file.isEmpty()){
            //handle save file
            String productName = this.uploadService.handleSaveUploadFile(file, "product");
            newProduct.setImage(productName);
        }

         //validate
        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for(FieldError error: errors){
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        if(newProductBindingResult.hasErrors()){
            return "/admin/product/createProduct";
        }
        this.productService.handleSaveProduct(newProduct);
        return "redirect:/admin/product";
    }
    
    @GetMapping("/admin/product/view/{id}")
    public String viewDetailPage(Model model, @PathVariable("id") long id) {
        Product product = this.productService.handleGetProductById(id).get();
        model.addAttribute("product", product);
        return "admin/product/view";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProductPage(Model model, @PathVariable("id") long id) {
        Product product = this.productService.handleGetProductById(id).get();
        model.addAttribute("product", product);
        return "/admin/product/delete";
    }

    @PostMapping("/admin/product/delete/{id}")
    public String deleteProductPage(@PathVariable("id") long id) {
        this.productService.handleDeleteProductById(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(@PathVariable("id") long id, Model model) {
        Optional<Product> product = this.productService.handleGetProductById(id);
        model.addAttribute("newProduct", product.get());
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update/{id}")
    public String updateProductPage(@PathVariable("id") long id, @ModelAttribute("newProduct") @Valid Product product, BindingResult newProductBindingResult , @RequestParam("productFile") MultipartFile file) {
        if(newProductBindingResult.hasErrors()){
            return "/admin/product/update";
        }
        //finding product by id
        Product productUpdate = this.productService.handleGetProductById(id).get();
        if(productUpdate!=null){
            if(!file.isEmpty()){
                //handle save file
                String productName = this.uploadService.handleSaveUploadFile(file, "product");
                productUpdate.setImage(productName);
            }
            productUpdate.setName(product.getName());
            productUpdate.setPrice(product.getPrice());
            productUpdate.setDetailDesc(product.getDetailDesc());
            productUpdate.setShortDesc(product.getShortDesc());
            productUpdate.setQuantity(product.getQuantity());
            productUpdate.setFactory(product.getFactory());
            productUpdate.setTarget(product.getTarget());
            this.productService.handleSaveProduct(productUpdate);
        }
        return "redirect:/admin/product";
    }
    
    
}
