package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.RoleService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService  roleService;
    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }
    @GetMapping("/")
    public String getHomePage(Model model,@RequestParam(name = "page", defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page-1, 10);
        Page<Product> products = this.productService.handleGetAllProduct("",pageable);
        List<Product> listProduct = products.getContent();
        model.addAttribute("products", listProduct);
        return "/client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerUser, BindingResult bindingResult) {
        User user = this.userService.registerDTOtoUser(registerUser);

        //validate
        List<FieldError> errors = bindingResult.getFieldErrors();
        for(FieldError error: errors){
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        //notify validate
        if(bindingResult.hasErrors()){
            return "/auth/register";
        }

        //hash password
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        
        user.setPassword(hashPassword);
        //find role
        Role role = this.roleService.handleGetRoleByName("USER");
        if(role!=null){
            user.setRole(role);
        }
        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/auth/login";
    }

    @GetMapping("/access-denied")
    public String getAccessDeniedPage() {
        return "/auth/accessDenied";
    }

    @GetMapping("/products")
    public String getProductsPage(Model model,@RequestParam(name = "page", defaultValue = "1") int page
    ,@RequestParam(name = "name", defaultValue = "") String name
    ) {
        Pageable pageable = PageRequest.of(page-1, 4);
        Page<Product> products = this.productService.handleGetAllProduct(name, pageable);
        List<Product> listProduct = products.getContent();
        model.addAttribute("products", listProduct);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "client/homepage/products";
    }
}
