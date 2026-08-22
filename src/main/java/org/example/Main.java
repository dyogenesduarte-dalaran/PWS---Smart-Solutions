package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando pipeline de dados do PWS...");

        // Caminho absoluto do arquivo CSV na sua máquina
        String caminhoDoArquivo = "/home/diogenes/cienciaDeDados/Projetos/PWS---Smart-Solutions/data/RPR003_ITENSLOCACAOPRODUTO170826161742.xlsm - Dados.csv";

        // Chama o CsvLoader para processar o arquivo e popular a staging_area
        CsvLoader.carregarCsvParaStaging(caminhoDoArquivo);
    }
}