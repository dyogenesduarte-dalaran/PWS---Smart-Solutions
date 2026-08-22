package org.example;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CsvLoader {

    public static void carregarCsvParaStaging(String caminhoCsv) {
        System.out.println("Limpando a staging_area antes da nova carga...");

        try (Connection conn = dataBaseConnector.getConnection()) {
            // 1. Limpa a staging_area
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("TRUNCATE TABLE staging_area;");
            }

            System.out.println("Executando carga de alta performance via COPY do PostgreSQL...");

            // 2. Utiliza o CopyManager para ler todas as 16 colunas do CSV nativamente
            BaseConnection baseConn = conn.unwrap(BaseConnection.class);
            CopyManager copyManager = new CopyManager(baseConn);

            try (BufferedReader fileReader = new BufferedReader(new FileReader(caminhoCsv))) {
                String sqlCopy = "COPY staging_area (" +
                        "produto_codigo, localizacao, descricao, referencia, referencia_ordenacao, " +
                        "tipo_produto, estoque_qtde, estoque_bloqueada, estoque_total, valor_unitario, " +
                        "valor_total, empresa_nome, estoque_descricao, prod_estoque_local_outros, scc_manual, clas_abc" +
                        ") FROM STDIN WITH CSV HEADER QUOTE '\"' DELIMITER ','";

                long linhasInseridas = copyManager.copyIn(sqlCopy, fileReader);
                System.out.println("SUCESSO! " + linhasInseridas + " linhas carregadas para a staging_area via COPY! 🚀");
            }

        } catch (IOException e) {
            System.out.println("Erro de IO ao ler o arquivo CSV:");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro de SQL / PostgreSQL no COPY:");
            e.printStackTrace();
        }
    }
}