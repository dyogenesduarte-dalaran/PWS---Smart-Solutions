package org.example.controller;

import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{referencia}")
    public ResponseEntity<Product> getProductByReferencia(@PathVariable String referencia) {
        System.out.println(">>> Buscando produto pela referência: " + referencia);

        return productRepository.findByProdutoReferenciaIgnoreCase(referencia)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}