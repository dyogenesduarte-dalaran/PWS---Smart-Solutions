package org.example.controller;

import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{codigo}")
    public ResponseEntity<Product> getProductByCode(@PathVariable String codigo) {
        System.out.println(">>> Código digitado pelo usuário: " + codigo);

        return productRepository.findByProductCode(codigo)
                .map(product -> {
                    System.out.println(">>> Sucesso: Produto encontrado no banco!");
                    return ResponseEntity.ok(product);
                })
                .orElseGet(() -> {
                    System.out.println(">>> ALERTA: Produto NÃO encontrado para o código: " + codigo);
                    return ResponseEntity.notFound().build();
                });
    }
}