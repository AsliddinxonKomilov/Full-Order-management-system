package com.ecom.ecomproject.dto;

import com.ecom.ecomproject.entity.Order;
import com.ecom.ecomproject.entity.OrderStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private List<OrderItemResponse> items;

    public static OrderResponse from(Order order) {
        return null;
    }
}