package org.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "produto_codigo")
    private String produtoCodigo;

    private String localizacao;
    private String descricao;

    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    @Column(name = "estoque_total")
    private BigDecimal estoqueTotal;

    @Column(name = "tipo_produto")
    private String tipoProduto;

    // Getters
    public String getProdutoCodigo() { return produtoCodigo; }
    public String getLocalizacao() { return localizacao; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValorUnitario() { return valorUnitario; }
    public BigDecimal getEstoqueTotal() { return estoqueTotal; }
    public String getTipoProduto() { return tipoProduto; }
}