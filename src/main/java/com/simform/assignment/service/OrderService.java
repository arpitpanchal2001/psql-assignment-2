package com.simform.assignment.service;

import com.simform.assignment.entity.Order;
import com.simform.assignment.entity.Product;
import com.simform.assignment.entity.User;
import com.simform.assignment.exception.OrderNotFoundException;
import com.simform.assignment.repository.OderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {
    @Autowired
    UserService userService;
    @Autowired
    OderRepository oderRepository;

    public void create(Order order) {
        User user = order.getUser();
        user.addOrder(order);
        List<Product> products = order.getProducts();
        products.forEach(product -> product.addOrder(order));
        oderRepository.save(order);
    }

    public List<Order> getAll() {
        List<Order> orderList = oderRepository.findAll();
        if (orderList.size() == 0) {
            throw new OrderNotFoundException();
        } else {
            return orderList;
        }
    }

    public Order getById(Long id) {
        Optional<Order> orderById = oderRepository.findById(id);
        if (orderById.isEmpty()) {
            throw new OrderNotFoundException();
        } else {
            return orderById.get();
        }
    }

    public void deleteById(Long id) {
        Optional<Order> orderById = oderRepository.findById(id);
        if (orderById.isEmpty()) {
            throw new OrderNotFoundException();
        } else {
            oderRepository.deleteById(id);
        }
    }

    public void update(Long id, Order order) {
        Optional<Order> optionalOrder = oderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException();
        } else {
            Order exestingOrder = optionalOrder.get();
            exestingOrder.setUser(order.getUser());
            exestingOrder.setProducts(order.getProducts());
            oderRepository.save(exestingOrder);

        }

    }
}



