package org.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "produto_codigo")
    private String produtoCodigo; // ID original da API (Coluna A)

    @Column(name = "referencia")
    private String produtoReferencia; // O código que o usuário digita (Coluna D - ex: 495001S000)

    private String localizacao;
    private String descricao;

    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    @Column(name = "estoque_total")
    private BigDecimal estoqueTotal;

    @Column(name = "tipo_produto")
    private String tipoProduto;

    // Getters e Setters
    public String getProdutoCodigo() { return produtoCodigo; }
    public void setProdutoCodigo(String produtoCodigo) { this.produtoCodigo = produtoCodigo; }

    public String getProdutoReferencia() { return produtoReferencia; }
    public void setProdutoReferencia(String produtoReferencia) { this.produtoReferencia = produtoReferencia; }

    public String getLocalizacao() { return localizacao; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValorUnitario() { return valorUnitario; }
    public BigDecimal getEstoqueTotal() { return estoqueTotal; }
    public String getTipoProduto() { return tipoProduto; }
}