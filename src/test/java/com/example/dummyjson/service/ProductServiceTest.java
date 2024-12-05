package com.example.dummyjson.service;

import com.example.dummyjson.dto.Product;
import com.example.dummyjson.dto.ResponseAllProductsJsonDummyDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WebClient webClient;

    @Value("${integration.dummyjson.host}")
    private String BASE_URL;

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Product 2");

        ResponseAllProductsJsonDummyDTO response = new ResponseAllProductsJsonDummyDTO();
        Product[] products = {product1, product2};
        response.setProducts(products);

        when(restTemplate.getForObject(BASE_URL, ResponseAllProductsJsonDummyDTO.class)).thenReturn(response);

        List<Product> result = productService.getAllProducts();
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getTitle());
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product 1");

        when(restTemplate.getForObject(BASE_URL + "/1", Product.class)).thenReturn(product);

        Product result = productService.getProductById(1L);
        assertEquals("Product 1", result.getTitle());
    }
}
