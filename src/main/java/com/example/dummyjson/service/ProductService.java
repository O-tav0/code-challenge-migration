package com.example.dummyjson.service;

import com.example.dummyjson.dto.Product;
import com.example.dummyjson.dto.ResponseAllProductsJsonDummyDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    @Value("${integration.dummyjson.host}")
    private String BASE_URL;
    public List<Product> getAllProducts() {
        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .build();

        ResponseAllProductsJsonDummyDTO response = webClient.get()
                .retrieve()
                .bodyToMono(ResponseAllProductsJsonDummyDTO.class)
                .block();

        return Arrays.asList(response.getProducts());

    }

    public Product getProductById(Long id) {
        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .build();

        return webClient.get()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}
