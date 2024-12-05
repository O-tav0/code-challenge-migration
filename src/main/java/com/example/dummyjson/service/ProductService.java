package com.example.dummyjson.service;

import com.example.dummyjson.dto.Product;
import com.example.dummyjson.dto.ResponseAllProductsJsonDummyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class ProductService {
    @Value("${integration.dummyjson.host}")
    private String BASE_URL;

    @Autowired
    private RestTemplate restTemplate;

    public List<Product> getAllProducts() {
        WebClient client = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", BASE_URL))
                .build();

        ResponseAllProductsJsonDummyDTO response = client.get()
                .retrieve()
                .bodyToMono(ResponseAllProductsJsonDummyDTO.class)
                .block();

        return Arrays.asList(response.getProducts());

    }

    public Product getProductById(Long id) {
        WebClient client = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", BASE_URL))
                .build();

        return client.get()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}
