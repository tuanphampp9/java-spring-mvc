package vn.hoidanit.laptopshop.service;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    public ProductService(ProductRepository productRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository, UserService userService, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }
    public void handleSaveProduct(Product product) {
        this.productRepository.save(product);
    }

    private Specification<Product> nameLike(String name){
        return (root, query, criteriaBuilder)
        -> criteriaBuilder.like(root.get(Product_.NAME), "%"+name+"%");
    }

    public Page<Product> handleGetAllProduct(String name, Pageable pageable) {
        return this.productRepository.findAll(this.nameLike(name), pageable);
    }
    public Optional<Product> handleGetProductById(long id) {
        return this.productRepository.findById(id);
    }
    public void handleDeleteProductById(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session, int amountProduct) {
        User user = this.userService.handleGetOneUserByEmail(email);
        if(user !=null){
            Cart cart = this.cartRepository.findByUser(user);
            //check user has cart? if not create new cart
            if(cart == null){
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                cart = this.cartRepository.save(otherCart);
            }
            //save cart_detail
            //find product by id
            Product product = this.productRepository.findById(productId).get();
            //check product exist in cart
            CartDetail cartDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);
            if(cartDetail != null){
                cartDetail.setQuantity(cartDetail.getQuantity() + amountProduct);
                this.cartDetailRepository.save(cartDetail);
                return;
            }
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setPrice(product.getPrice());
            cartDetail.setQuantity(amountProduct);
            this.cartDetailRepository.save(cartDetail);
            //update cart total product
            int sum = cart.getSum()+1;
            cart.setSum(sum);
            this.cartRepository.save(cart);
            session.setAttribute("sum", sum);
        }

    }


    public void handleUpdateCartBeforeCheckout(List<CartDetail> listCartDetails) {
        for(CartDetail cartDetail: listCartDetails){
            CartDetail cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if(cdOptional != null){
                cdOptional.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(cdOptional);
            } 
        }
    }

    public void handlePlaceOrder(User user, HttpSession session, String receiverName,String receiverAddress,String receiverPhone) {
        //create order
        Order order = new Order();
        order.setUser(user);
        order.setReceiverName(receiverName);
        order.setReceiverAddress(receiverAddress);
        order.setReceiverPhone(receiverPhone);
        order.setStatus("Pending");
        order = this.orderRepository.save(order);

        //create order detail
        //step1: get cart of user
        Cart cart = this.cartRepository.findByUser(user);
        if(cart != null){
            List<CartDetail> listCartDetails = cart.getCartDetails();
            double totalPrice = 0;
            for(CartDetail cartDetail: listCartDetails){
                //create order detail
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(cartDetail.getProduct());
                orderDetail.setPrice(cartDetail.getPrice());
                orderDetail.setQuantity(cartDetail.getQuantity());
                this.orderDetailRepository.save(orderDetail);

                //calculate total price
                totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
                //delete cart detail
                this.cartDetailRepository.deleteById(cartDetail.getId());
            }
            //update total price of order
            order.setTotalPrice(totalPrice);
            //delete cart
            this.cartRepository.deleteById(cart.getId());
            session.setAttribute("sum", 0);
        }
    }

    public long countProducts() {
        return this.productRepository.count();
    }
    
}
