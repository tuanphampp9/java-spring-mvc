package vn.hoidanit.laptopshop.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    public ProductService(ProductRepository productRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }
    public void handleSaveProduct(Product product) {
        this.productRepository.save(product);
    }
    public List<Product> handleGetAllProduct() {
        return this.productRepository.findAll();
    }
    public Optional<Product> handleGetProductById(long id) {
        return this.productRepository.findById(id);
    }
    public void handleDeleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        User user = this.userService.handleGetOneUserByEmail(email);
        if(user !=null){
            Cart cart = this.cartRepository.findByUser(user);
            //check user has cart? if not create new cart
            if(cart == null){
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(1);
                cart = this.cartRepository.save(otherCart);
            }
            //save cart_detail
            //find product by id
            Product product = this.productRepository.findById(productId).get();
            //check product exist in cart
            CartDetail cartDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);
            if(cartDetail != null){
                cartDetail.setQuantity(cartDetail.getQuantity() + 1);
                this.cartDetailRepository.save(cartDetail);
                return;
            }
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setPrice(product.getPrice());
            cartDetail.setQuantity(1);
            this.cartDetailRepository.save(cartDetail);

            //update cart total product
            int sum = cart.getSum()+1;
            cart.setSum(sum);
            this.cartRepository.save(cart);
            session.setAttribute("sum", sum);
        }

    }

    
}
