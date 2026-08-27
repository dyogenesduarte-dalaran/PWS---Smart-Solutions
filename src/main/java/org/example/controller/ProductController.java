package org.example.controller;

import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.example.service.ProductImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductImportService importService;

    @Autowired
    private ProductRepository productRepository;

    // Rota para importar o CSV (POST)
    @PostMapping("/import")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Por favor, envie um arquivo CSV válido.");
        }

        try {
            importService.importarCsv(file);
            return ResponseEntity.ok("CSV importado e processado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao importar: " + e.getMessage());
        }
    }

    // Rota para buscar o produto pelo código, ex: /api/products/92101R1000 (GET)
    @GetMapping("/{code}")
    public ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        return productRepository.findByProductCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}