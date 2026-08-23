package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando o servidor da API PWS Smart Solutions...");
        SpringApplication.run(Main.class, args);
    }
}