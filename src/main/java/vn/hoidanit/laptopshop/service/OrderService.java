package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }
    public Page<Order> handleGetAllOrder(Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }
    public Order handleGetOrderById(Long id) {
        return this.orderRepository.findById(id).orElse(null);
    }
    public Order handleUpdateOrder(Order order) {
        Order newOrder = this.orderRepository.findById(order.getId()).orElse(null);
        if (newOrder == null) {
            return null;
        }
        newOrder.setStatus(order.getStatus());
        return this.orderRepository.save(newOrder);
    }

    public void handleDeleteOrder(Order order) {
        Set<OrderDetail> orderDetails = order.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
           this.orderDetailRepository.deleteById(orderDetail.getId());
        }
        this.orderRepository.deleteById(order.getId());
    }

    public long countOrders() {
        return this.orderRepository.count();
    }

    public List<Order> handleGetOrderByUser(User user) {
        return this.orderRepository.findAllByUser(user);
    }
}
