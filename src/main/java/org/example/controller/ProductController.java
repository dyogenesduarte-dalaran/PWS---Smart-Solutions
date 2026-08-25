package com.pws.smartsolutions.controller;

import com.pws.smartsolutions.service.ProductImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductImportService importService;

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
}