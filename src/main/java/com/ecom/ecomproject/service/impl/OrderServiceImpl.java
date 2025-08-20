package com.ecom.ecomproject.service.impl;

import com.ecom.ecomproject.dto.CreateOrderRequest;
import com.ecom.ecomproject.dto.OrderResponse;
import com.ecom.ecomproject.dto.UpdateOrderRequest;
import com.ecom.ecomproject.entity.Order;
import com.ecom.ecomproject.entity.OrderItem;
import com.ecom.ecomproject.entity.OrderStatus;
import com.ecom.ecomproject.entity.Product;
import com.ecom.ecomproject.exception.InsufficientStockException;
import com.ecom.ecomproject.exception.OrderNotFoundException;
import com.ecom.ecomproject.exception.ProductNotFoundException;
import com.ecom.ecomproject.repository.OrderItemRepository;
import com.ecom.ecomproject.repository.OrderRepository;
import com.ecom.ecomproject.repository.ProductRepository;
import com.ecom.ecomproject.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setStatus(OrderStatus.Pending);

        List<OrderItem> items = request.getItems().stream().map(reqItem -> {
            Product product = productRepository.findById(reqItem.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + reqItem.getProductId()));

            if (product.getStock() < reqItem.getQuantity()) {
                throw new InsufficientStockException("Not enough stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - reqItem.getQuantity());
            productRepository.save(product);

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(reqItem.getQuantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);
        orderRepository.save(order);
        orderItemRepository.saveAll(items);

        return OrderResponse.from(order);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        return OrderResponse.from(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrder(Long id, UpdateOrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        if (request.getStatus() != null) {
            order.setStatus(request.getStatus());
        }

        orderRepository.save(order);
        return OrderResponse.from(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }
}