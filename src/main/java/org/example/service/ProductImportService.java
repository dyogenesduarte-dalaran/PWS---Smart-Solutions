package org.example.service;

import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;

@Service
public class ProductImportService {

    @Autowired
    private ProductRepository productRepository;

    public void importarCsv(MultipartFile file) {
        int totalLinhas = 0;
        int importadasComSucesso = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                totalLinhas++;

                // Pula a primeira linha se for cabeçalho
                if (totalLinhas == 1 && linha.toLowerCase().contains("codigo")) {
                    continue;
                }

                try {
                    // Divide a linha pelas vírgulas
                    String[] colunas = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                    // Garante que a linha tem colunas suficientes para não dar erro de índice
                    if (colunas.length < 4) {
                        System.out.println("Linha " + totalLinhas + " ignorada por tamanho insuficiente.");
                        continue;
                    }

                    // ATENÇÃO: Altere os números entre colchetes [ ] conforme a posição real
                    // das colunas no seu arquivo CSV de origem:
                    // Exemplo ajustado com base na estrutura visual do seu CSV:
                    String productName = colunas[1].replace("\"", "").trim(); // Nome do produto
                    String location    = colunas[2].replace("\"", "").trim(); // Localização
                    String productCode = colunas[3].replace("\"", "").trim(); // Código do produto (ex: 92101R1000)

                    // Tratamento seguro para quantidade (se houver na coluna 4 ou outra)
                    int quantity = 0;
                    if (colunas.length > 4 && !colunas[4].replace("\"", "").trim().isEmpty()) {
                        try {
                            quantity = Integer.parseInt(colunas[4].replace("\"", "").trim());
                        } catch (NumberFormatException ignored) {
                            quantity = 0;
                        }
                    }

                    // Se o código estiver vazio, pula a linha
                    if (productCode.isEmpty()) {
                        continue;
                    }

                    // Verifica se o produto já existe no banco (pelo productCode) para atualizar ou criar novo
                    Product product = productRepository.findByProductCode(productCode)
                            .orElse(new Product());

                    product.setProductCode(productCode);
                    product.setProductName(productName);
                    product.setProductLocation(location);
                    product.setQuantity(BigDecimal.valueOf(quantity));
                    product.setPrice(BigDecimal.ZERO); // Ajuste caso tenha coluna de preço

                    productRepository.save(product);
                    importadasComSucesso++;

                } catch (Exception e) {
                    System.err.println("Erro na linha " + totalLinhas + ": " + e.getMessage());
                }
            }

            System.out.println("Importação concluída! Total processado com sucesso: " + importadasComSucesso + " de " + totalLinhas);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar o arquivo CSV: " + e.getMessage());
        }
    }
}