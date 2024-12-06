package com.example.dummyjson.service;

import com.example.dummyjson.dto.Product;
import com.example.dummyjson.dto.ResponseAllProductsJsonDummyDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("integration.dummyjson.host", () -> mockWebServer.url("/").toString());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        ResponseAllProductsJsonDummyDTO response = new ResponseAllProductsJsonDummyDTO();

        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Product 2");

        Product[] productsMock = {product1, product2};

        response.setProducts(productsMock);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(response);

        mockWebServer.enqueue(new MockResponse()
                .setBody(json)
                .addHeader("Content-Type", "application/json"));

        List<Product> products = productService.getAllProducts();
        assertEquals(2, products.size());
        assertEquals("Product 1", products.get(0).getTitle());
        assertEquals("Product 2", products.get(1).getTitle());
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Product 1");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product1);

        mockWebServer.enqueue(new MockResponse()
                .setBody(json)
                .addHeader("Content-Type", "application/json"));

        Product product = productService.getProductById(1L);
        assertEquals("Product 1", product.getTitle());
    }
}
