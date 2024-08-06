package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;





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
    public String postMethodName(@PathVariable long id, HttpServletRequest request, @RequestParam(defaultValue = "1") String quantity) {
        HttpSession session = request.getSession(false);
        long productId = id;
        int amountProduct = Integer.parseInt(quantity);
        String email = session.getAttribute("email").toString();
        this.productService.handleAddProductToCart(email,productId, session, amountProduct);
        
        return "redirect:/";
    }

    
    
    @GetMapping("/cart")
    public String getCartPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String email = session.getAttribute("email").toString();
        User user = this.userService.handleGetOneUserByEmail(email);
        Cart cart = this.cartService.findByUser(user);
        List<CartDetail> listCartDetails = cart==null? new ArrayList<CartDetail>():             this.cartDetailService.findAllByCart(cart);
        model.addAttribute("listCartDetails", listCartDetails);

        //total order
        double total = 0;
        for(CartDetail cartDetail: listCartDetails){
            total += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("total", total);
        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(HttpServletRequest request, @PathVariable long id){
        HttpSession session = request.getSession(false);
        CartDetail cartDetail = this.cartDetailService.findById(id);
        Cart cart = cartDetail.getCart();
        //delete cart detail
        if(cartDetail != null){
            this.cartDetailService.handleDeleteCartDetailById(cartDetail.getId());
        }
        //update sum of cart
        if(cart.getSum() > 1){
            cart.setSum(cart.getSum() - 1);
            //update session sum of cart
            session.setAttribute("sum", (int)session.getAttribute("sum") - 1);
            //save cart
            this.cartService.save(cart);
        }else{
            //update session sum of cart
            session.setAttribute("sum", 0);
            //delete cart
            if(cart != null){
                this.cartService.handleDeleteCartById(cart.getId());
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckoutOutPage(@ModelAttribute("cart") Cart cart){
        List<CartDetail> listCartDetails = cart==null? new ArrayList<CartDetail>(): cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(listCartDetails);
        return "redirect:/checkout";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = session.getAttribute("email").toString();
        User currentUser = this.userService.handleGetOneUserByEmail(email);

        Cart cart = this.cartService.findByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(HttpServletRequest request, @RequestParam("receiverName") String receiverName,@RequestParam("receiverAddress") String receiverAddress, @RequestParam("receiverPhone") String receiverPhone ){ 
        HttpSession session = request.getSession(false);
        String email = session.getAttribute("email").toString();
        User currentUser = this.userService.handleGetOneUserByEmail(email);
        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);
        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThanksPage(){
        return "client/cart/thanks";
    }
    
}
