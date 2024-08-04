package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    public Cart findByUser(User user) {
        return this.cartRepository.findByUser(user);
    }
    public Cart save(Cart cart) {
        return this.cartRepository.save(cart);
    }
    public void handleDeleteCartById(long id) {
     this.cartRepository.deleteById(id);
    }
}
