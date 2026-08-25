package org.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "produto_codigo")
    private String produtoCodigo;

    @Column(name = "referencia")
    private String produtoReferencia; // O código original com formatação

    @Column(name = "referencia_limpa")
    private String referenciaLimpa; // O código normalizado (só letras e números maiúsculos)

    private String localizacao;
    private String descricao;

    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    @Column(name = "estoque_total")
    private BigDecimal estoqueTotal;

    @Column(name = "tipo_produto")
    private String tipoProduto;

    // Método utilitário para limpar a referência automaticamente antes de salvar
    public void setProdutoReferencia(String produtoReferencia) {
        this.produtoReferencia = produtoReferencia;
        if (produtoReferencia != null) {
            // Remove tudo o que não for letra ou número e transforma em maiúsculo
            this.referenciaLimpa = produtoReferencia.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        }
    }

    // Getters e Setters
    public String getProdutoCodigo() { return produtoCodigo; }
    public void setProdutoCodigo(String produtoCodigo) { this.produtoCodigo = produtoCodigo; }

    public String getProdutoReferencia() { return produtoReferencia; }

    public String getReferenciaLimpa() { return referenciaLimpa; }
    public void setReferenciaLimpa(String referenciaLimpa) { this.referenciaLimpa = referenciaLimpa; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }

    public BigDecimal getEstoqueTotal() { return estoqueTotal; }
    public void setEstoqueTotal(BigDecimal estoqueTotal) { this.estoqueTotal = estoqueTotal; }

    public String getTipoProduto() { return tipoProduto; }
    public void setTipoProduto(String tipoProduto) { this.tipoProduto = tipoProduto; }
}