/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaqueu.avaliacao_2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author zaqueu
 */
public class FileReader {
    public static void main(String[] args) {
        // Define o caminho do arquivo
        Path caminho = Paths.get("/home/zaqueu/Downloads/Dist5.txt");

        try {
            // Lê todas as linhas do arquivo de uma vez
            List<String> linhas = Files.readAllLines(caminho);

            System.out.println("Conteúdo do arquivo:");
            for (String linha : linhas) {
                System.out.println(linha);
            }
        } catch (IOException e) {
            // Tratamento de erro, como arquivo não encontrado
            e.printStackTrace();
        }
    }
}