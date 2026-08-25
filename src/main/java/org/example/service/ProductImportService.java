package com.pws.smartsolutions.service;

import com.pws.smartsolutions.model.Product;
import com.pws.smartsolutions.repository.ProductRepository;
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
                    // Divide a linha considerando vírgulas (ajuste se o seu separador for ponto e vírgula ';')
                    String[] colunas = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                    if (colunas.length < 5) {
                        System.out.println("Linha " + totalLinhas + " ignorada por tamanho insuficiente.");
                        continue;
                    }

                    // Limpa aspas e espaços dos campos
                    String productCode = colunas[1].replace("\"", "").trim();
                    String productName = colunas[2].replace("\"", "").trim();
                    String location = colunas[3].replace("\"", "").trim();

                    // Tratamento seguro para quantidade
                    int quantity = 0;
                    if (!colunas[4].replace("\"", "").trim().isEmpty()) {
                        quantity = Integer.parseInt(colunas[4].replace("\"", "").trim());
                    }

                    // Verifica se o produto já existe no banco (pelo productCode) para atualizar ou criar novo
                    Product product = productRepository.findByProductCode(productCode)
                            .orElse(new Product());

                    product.setProductCode(productCode);
                    product.setProductName(productName);
                    product.setProductLocation(location);
                    product.setQuantity(quantity);
                    product.setPrice(BigDecimal.ZERO); // Ajuste se houver coluna de preço no seu CSV

                    productRepository.save(product);
                    importadasComSucesso++;

                } catch (Exception e) {
                    // Se der erro em uma linha específica, exibe no console sem travar o sistema inteiro
                    System.err.println("Erro na linha " + totalLinhas + ": " + e.getMessage());
                }
            }

            System.out.println("Importação concluída! Total processado: " + importadasComSucesso + " de " + totalLinhas);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar o arquivo CSV: " + e.getMessage());
        }
    }
}