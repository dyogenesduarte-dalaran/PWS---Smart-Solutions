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

    @GetMapping("/{referencia}")
    public ResponseEntity<Product> getProductByReferencia(@PathVariable String referencia) {
        System.out.println(">>> Referência digitada pelo usuário: " + referencia);

        // Aplica a mesma limpeza: remove hífens, pontos, espaços e converte para maiúsculo
        String referenciaLimpa = referencia.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        System.out.println(">>> Buscando no banco pela referência limpa: " + referenciaLimpa);

        return productRepository.findByReferenciaLimpa(referenciaLimpa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}