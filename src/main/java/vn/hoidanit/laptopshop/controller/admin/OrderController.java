package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.service.OrderService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("/admin/order")
    public String getOrderPage(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page-1, 4);
        Page<Order> orders = this.orderService.handleGetAllOrder(pageable);
        List<Order> listOrder = orders.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("orders", listOrder);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/view/{id}")
    public String getViewOrderPage(@PathVariable Long id, Model model) {
        Order order = this.orderService.handleGetOrderById(id);
        Set<OrderDetail> orderDetails = order.getOrderDetails();
        model.addAttribute("listOrderDetails", orderDetails);
        model.addAttribute("order", order);
        return "admin/order/view";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderPage(@PathVariable Long id, Model model) {
        Order order = this.orderService.handleGetOrderById(id);
        model.addAttribute("newOrder", order);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String updateOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.handleUpdateOrder(order);    
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String deleteOrderPage(@PathVariable Long id, Model model) {
        Order order = this.orderService.handleGetOrderById(id);
        model.addAttribute("order", order);
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        Order order = this.orderService.handleGetOrderById(id);
        this.orderService.handleDeleteOrder(order);
        return "redirect:/admin/order";
    }
    
    
    
    
}
