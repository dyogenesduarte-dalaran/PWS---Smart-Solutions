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

        // Limpa a referência digitada
        String referenciaLimpa = referencia.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        System.out.println(">>> Buscando no banco pela referência limpa: " + referenciaLimpa);

        // Chamada com logs detalhados para rastreamento
        return productRepository.findByReferenciaLimpa(referenciaLimpa)
                .map(product -> {
                    System.out.println(">>> Sucesso: Produto encontrado no banco!");
                    return ResponseEntity.ok(product);
                })
                .orElseGet(() -> {
                    System.out.println(">>> ALERTA: Produto NÃO encontrado no banco para a referência: " + referenciaLimpa);
                    return ResponseEntity.notFound().build();
                });
    }
}