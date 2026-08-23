package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // O Spring gera a busca usando o atributo Java "produtoReferencia" ignorando maiúsculas/minúsculas
    Optional<Product> findByProdutoReferenciaIgnoreCase(String produtoReferencia);
}