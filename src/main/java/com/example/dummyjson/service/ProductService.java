package com.example.dummyjson.service;

import com.example.dummyjson.dto.Product;
import com.example.dummyjson.dto.ResponseAllProductsJsonDummyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Value("${integration.dummyjson.host}")
    private String BASE_URL;

    @Autowired
    private RestTemplate restTemplate;

    public List<Product> getAllProducts() {
        ResponseAllProductsJsonDummyDTO response = restTemplate.getForObject(BASE_URL, ResponseAllProductsJsonDummyDTO.class);
        return Arrays.asList(response.getProducts());
    }

    public Product getProductById(Long id) {
        String url = BASE_URL + "/" + id;
        return restTemplate.getForObject(url, Product.class);
    }
}
