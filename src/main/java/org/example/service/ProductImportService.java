package org.example.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Service
public class ProductImportService {

    @Autowired
    private ProductRepository productRepository;

    // Nomes exatos das colunas no cabeçalho do CSV de origem.
    // Se o layout do arquivo mudar, ajuste só aqui.
    private static final String COL_CODIGO      = "Produto_Referencia";
    private static final String COL_NOME        = "Produto_Descricao";
    private static final String COL_LOCALIZACAO = "LocalizacaoProduto_IdentIFicador";
    private static final String COL_CATEGORIA   = "TipoProduto_Descricao";
    private static final String COL_QUANTIDADE  = "ProdutoEstoque_Qtde";
    private static final String COL_PRECO       = "ValorUnitario";

    public void importarCsv(MultipartFile file) {
        int totalLinhas = 0;
        int importadasComSucesso = 0;

        try (Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setTrim(true)
                     .setIgnoreSurroundingSpaces(true)
                     .build()
                     .parse(reader)) {

            for (CSVRecord record : parser) {
                totalLinhas++;

                try {
                    String productCode = getCampo(record, COL_CODIGO);

                    if (productCode.isEmpty()) {
                        System.out.println("Linha " + record.getRecordNumber() + " ignorada: código do produto vazio.");
                        continue;
                    }

                    String productName = getCampo(record, COL_NOME);
                    String location = getCampo(record, COL_LOCALIZACAO);
                    String category = getCampo(record, COL_CATEGORIA);

                    int quantity = parseInteiro(getCampo(record, COL_QUANTIDADE));
                    BigDecimal price = parseDecimal(getCampo(record, COL_PRECO));

                    Product product = productRepository.findByProductCode(productCode)
                            .orElse(new Product());

                    product.setProductCode(productCode);
                    product.setProductName(productName);
                    product.setProductLocation(location);
                    product.setCategory(category);
                    product.setQuantity(BigDecimal.valueOf(quantity));
                    product.setPrice(price);

                    productRepository.save(product);
                    importadasComSucesso++;

                } catch (Exception e) {
                    System.err.println("Erro na linha " + record.getRecordNumber() + ": " + e.getMessage());
                }
            }

            System.out.println("Importação concluída! Total processado com sucesso: "
                    + importadasComSucesso + " de " + totalLinhas);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar o arquivo CSV: " + e.getMessage());
        }
    }

    // Lê um campo do registro com segurança, mesmo se a coluna não existir na linha.
    private String getCampo(CSVRecord record, String nomeColuna) {
        if (!record.isMapped(nomeColuna)) {
            return "";
        }
        String valor = record.get(nomeColuna);
        return valor == null ? "" : valor.trim();
    }

    private int parseInteiro(String valor) {
        if (valor == null || valor.isEmpty()) {
            return 0;
        }
        try {
            // remove separador de milhar, se houver, e ignora casas decimais
            String limpo = valor.replace(".", "").split(",")[0].trim();
            return Integer.parseInt(limpo);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private BigDecimal parseDecimal(String valor) {
        if (valor == null || valor.isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            // formato brasileiro: vírgula decimal, sem separador de milhar nos dados observados
            String normalizado = valor.replace(",", ".");
            return new BigDecimal(normalizado);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
}