package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class CartController {
    private final UserService userService;
    private final OrderService orderService;

    public CartController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }
    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = session.getAttribute("email").toString();
        User currentUser = this.userService.handleGetOneUserByEmail(email);
        List<Order> orders = this.orderService.handleGetOrderByUser(currentUser);
        model.addAttribute("orders", orders);
        return "client/cart/order-history";
    }
}
