package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // JPQL: consulta orientada a objetos do JPA (mapeia automaticamente para a entidade Product)
    @Query("SELECT p FROM Product p WHERE p.productCode = :code")
    Optional<Product> findByProductCode(@Param("code") String code);
}