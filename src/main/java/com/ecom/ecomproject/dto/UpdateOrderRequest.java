package com.ecom.ecomproject.dto;


import com.ecom.ecomproject.entity.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderRequest {
    private String customerName;
    private String productName;
    private Integer quantity;

    public OrderStatus getStatus() {
        return null;
    }
}