package com.ecom.ecomproject.service;

import com.ecom.ecomproject.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
}