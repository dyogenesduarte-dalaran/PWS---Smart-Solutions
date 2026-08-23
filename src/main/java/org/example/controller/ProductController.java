package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id) {
        System.out.println(">>> REQUISIÇÃO CHEGOU NO CONTROLLER PARA O ID: " + id);
        return "Produto com ID: " + id;
    }
}