package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // Busca exata pela referência limpa
    Optional<Product> findByReferenciaLimpa(String referenciaLimpa);
}