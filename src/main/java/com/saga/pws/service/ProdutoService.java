package com.saga.pws.service;

import com.saga.pws.dto.ProdutoCadastroDTO;
import com.saga.pws.model.Produto;
import com.saga.pws.model.StatusProduto;
import com.saga.pws.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto cadastrar(ProdutoCadastroDTO dto) {

        String partNumberOriginal = dto.getPartNumber().trim();
        String descricaoOriginal = dto.getDescricao().trim();

        String partNumberNormalizado = partNumberOriginal.toUpperCase();
        String descricaoNormalizada = descricaoOriginal.toUpperCase();

        String curva = dto.getCurva();

        if (curva == null || curva.isBlank()) {
            curva = "SEM_CLASSIFICACAO";
        }

        LocalDateTime agora = LocalDateTime.now();

        Produto produto = new Produto(
                partNumberOriginal,
                partNumberNormalizado,
                descricaoOriginal,
                descricaoNormalizada,
                dto.getValor(),
                curva,
                StatusProduto.ATIVO,
                agora,
                agora
        );

        boolean jaExiste = produtoRepository
                .existsByPartNumberNormalizadoAndDescricaoNormalizada(
                        partNumberNormalizado,
                        descricaoNormalizada
                );

        if (jaExiste) {
            throw new IllegalStateException("Produto já cadastrado.");
        }

        return produtoRepository.save(produto);
    }
}