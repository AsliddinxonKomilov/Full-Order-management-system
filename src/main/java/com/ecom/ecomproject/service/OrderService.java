package com.ecom.ecomproject.service;

import com.ecom.ecomproject.dto.CreateOrderRequest;
import com.ecom.ecomproject.dto.OrderResponse;
import com.ecom.ecomproject.dto.UpdateOrderRequest;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse getOrderById(Long id);

    List<OrderResponse> getAllOrders();

    void deleteOrder(Long id);

    OrderResponse updateOrder(Long id, UpdateOrderRequest request);
}