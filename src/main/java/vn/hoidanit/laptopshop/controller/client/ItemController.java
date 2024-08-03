package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.service.CartDetailService;
import vn.hoidanit.laptopshop.service.CartService;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;




@Controller
public class ItemController {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final CartDetailService cartDetailService;
    
    public ItemController(ProductService productService, CartService cartService, UserService userService, CartDetailService cartDetailService) {
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
        this.cartDetailService = cartDetailService;
    }

    @GetMapping("/product/{id}")
    public String getDetailProductPage(Model model, @PathVariable int id){
        Product product = productService.handleGetProductById(id).get();
        model.addAttribute("product", product);
        return "client/product/detail";
    }
    
    @PostMapping("/add-product-to-cart/{id}")
    public String postMethodName(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = session.getAttribute("email").toString();
        this.productService.handleAddProductToCart(email,productId, session);
        
        return "redirect:/";
    }
    
    @GetMapping("/cart")
    public String getCartPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String email = session.getAttribute("email").toString();
        User user = this.userService.handleGetOneUserByEmail(email);
        Cart cart = this.cartService.findByUser(user);
        List<CartDetail> listCartDetails = this.cartDetailService.findAllByCart(cart);
        model.addAttribute("listCartDetails", listCartDetails);

        //total order
        double total = 0;
        for(CartDetail cartDetail: listCartDetails){
            total += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("total", total);
        return "client/cart/show";
    }
}
