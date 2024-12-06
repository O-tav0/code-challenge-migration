package com.example.dummyjson.controller;

import com.example.dummyjson.dto.HealthDTO;
import com.example.dummyjson.dto.Product;
import com.example.dummyjson.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    @Autowired
    private ProductService productService;
    @GetMapping
    public HealthDTO getApplicationHealth() {
        HealthDTO healthDTO = new HealthDTO();
        Product product = productService.getProductById(1L);
        if(product != null) {
            healthDTO.setStatus("OK");
        } else {
            healthDTO.setStatus("KO");
        }
        return healthDTO;
    }
}
