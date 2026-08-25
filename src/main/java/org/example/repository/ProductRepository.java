package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // Altere para findByProductCode (reflete a coluna product_code do banco)
    Optional<Product> findByProductCode(String productCode);
}