package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // Query nativa direta no banco: ignora regras de nomes do JPA e vai certeira na coluna product_code
    @Query(value = "SELECT * FROM products WHERE product_code = :code", nativeQuery = true)
    Optional<Product> findByProductCode(@Param("code") String code);
}