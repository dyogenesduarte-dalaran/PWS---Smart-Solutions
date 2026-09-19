package com.saga.pws.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "produto",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_produto_identidade",
                        columnNames = {
                                "part_number_normalizado",
                                "descricao_normalizada"
                        }
                )
        }
)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "part_number_original", nullable = false, length = 255)
    private String partNumberOriginal;

    @Column(name = "part_number_normalizado", nullable = false, length = 255)
    private String partNumberNormalizado;

    @Column(name = "descricao_original", nullable = false, length = 500)
    private String descricaoOriginal;

    @Column(name = "descricao_normalizada", nullable = false, length = 500)
    private String descricaoNormalizada;

    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "curva", nullable = false, length = 50)
    private String curva;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_produto", nullable = false, length = 20)
    private StatusProduto statusProduto;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    public Produto() {
    }

    public Produto(
            String partNumberOriginal,
            String partNumberNormalizado,
            String descricaoOriginal,
            String descricaoNormalizada,
            BigDecimal valor,
            String curva,
            StatusProduto statusProduto,
            LocalDateTime criadoEm,
            LocalDateTime atualizadoEm
    ) {
        this.partNumberOriginal = partNumberOriginal;
        this.partNumberNormalizado = partNumberNormalizado;
        this.descricaoOriginal = descricaoOriginal;
        this.descricaoNormalizada = descricaoNormalizada;
        this.valor = valor;
        this.curva = curva;
        this.statusProduto = statusProduto;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }

    public Long getId() {
        return id;
    }

    public String getPartNumberOriginal() {
        return partNumberOriginal;
    }

    public void setPartNumberOriginal(String partNumberOriginal) {
        this.partNumberOriginal = partNumberOriginal;
    }

    public String getPartNumberNormalizado() {
        return partNumberNormalizado;
    }

    public void setPartNumberNormalizado(String partNumberNormalizado) {
        this.partNumberNormalizado = partNumberNormalizado;
    }

    public String getDescricaoOriginal() {
        return descricaoOriginal;
    }

    public void setDescricaoOriginal(String descricaoOriginal) {
        this.descricaoOriginal = descricaoOriginal;
    }

    public String getDescricaoNormalizada() {
        return descricaoNormalizada;
    }

    public void setDescricaoNormalizada(String descricaoNormalizada) {
        this.descricaoNormalizada = descricaoNormalizada;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCurva() {
        return curva;
    }

    public void setCurva(String curva) {
        this.curva = curva;
    }

    public StatusProduto getStatusProduto() {
        return statusProduto;
    }

    public void setStatusProduto(StatusProduto statusProduto) {
        this.statusProduto = statusProduto;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}