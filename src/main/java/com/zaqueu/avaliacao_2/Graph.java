/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zaqueu.avaliacao_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author zaqueu
 */
public class Graph {
    int qtdVertex = 0;
    int qtdEdge = 0;
    
    int[][] distance;
    int[][] adjacent;
    
    // gera matriz adjascente
    public void generateAdjacentMatrix(int qtdLines, int qtdColumns, int[][] matrix){
        this.adjacent = new int[qtdLines][qtdColumns];
        
        for(int i = 0; i < qtdLines; i++){
            for(int j = 0; j < qtdColumns; j++){
                if(matrix[i][j] > 0){
                    adjacent[i][j] = 1;
                }else{
                    adjacent[i][j] = 0;
                }
            }
        }
    }
    
    // contador de arestas do grafo
    public void howManyEdges(int qtdLines, int qtdColumns){
        int count = 0;
        for(int i = 0; i < qtdLines; i++){
            for(int j = 0; j < qtdColumns; j++){
                if(i < j && adjacent[i][j] == 1)
                    count++;
            }
        }
        
        this.qtdEdge = count;
    }
    
    public void insertEdge(int firstVertex, int secondVertex, int weight){
        if(adjacent[firstVertex][secondVertex] == 0){
            adjacent[firstVertex][secondVertex] = 1;
            adjacent[secondVertex][firstVertex] = 1;
            
            distance[firstVertex][secondVertex] = weight;
            distance[secondVertex][firstVertex] = weight;
            
            this.qtdEdge++;
        }
    }
    
    public void removeEdge(int firstVertex, int secondVertex){
        if(adjacent[firstVertex][secondVertex] == 1){
            adjacent[firstVertex][secondVertex] = 0;
            adjacent[secondVertex][firstVertex] = 0;
            
            distance[firstVertex][secondVertex] = Integer.MAX_VALUE;
            distance[secondVertex][firstVertex] = Integer.MAX_VALUE;
            
            this.qtdEdge--;
        }
    }
    
    public void showGraph(){
        System.out.println("--- Informações do Grafo ---");
        // Usa o tamanho da matriz como a fonte da verdade para a quantidade de vértices
        this.qtdVertex = (adjacent != null) ? adjacent.length : 0;
        
        System.out.println("Número de Vértices: " + this.qtdVertex);
        System.out.println("Número de Arestas: " + this.qtdEdge);
        System.out.println("\nLista de Adjacência (Vértice -> Vizinho(Peso)):");
        
        if (this.qtdVertex == 0) {
            System.out.println("O grafo está vazio.");
            System.out.println("----------------------------");
            return;
        }

        for (int i = 0; i < this.qtdVertex; i++) {
            System.out.print(i + ": ");
            StringBuilder neighbors = new StringBuilder();
            for (int j = 0; j < this.qtdVertex; j++) {
                if (adjacent[i][j] == 1) {
                    neighbors.append(j).append("(").append(distance[i][j]).append(") ");
                }
            }
            if (neighbors.length() == 0) {
                System.out.print("Nenhuma conexão");
            } else {
                System.out.print(neighbors.toString());
            }
            System.out.println();
        }
        System.out.println("----------------------------");
    }
    
    /**
     * Calcula e retorna o grau de um vértice específico.
     * O grau é o número de arestas incidentes ao vértice.
     * @param vertex O vértice para o qual o grau será calculado.
     * @return O grau do vértice.
     * @throws IllegalArgumentException se o vértice for inválido.
     */
    public int getDegree(int vertex) {
        if (vertex < 0 || vertex >= this.qtdVertex) {
            throw new IllegalArgumentException("Vértice inválido: " + vertex);
        }
        
        int degree = 0;
        for (int j = 0; j < this.qtdVertex; j++) {
            // Como adjacent[vertex][j] é 1 se houver uma aresta e 0 caso contrário,
            // podemos simplesmente somar os valores da linha.
            degree += adjacent[vertex][j];
        }
        return degree;
    }

    /**
     * Exibe o grau de cada um dos vértices do grafo.
     */
    public void showDegrees() {
        System.out.println("--- Grau de Cada Vértice ---");
        if (this.qtdVertex == 0) {
            System.out.println("O grafo está vazio.");
            System.out.println("----------------------------");
            return;
        }

        for (int i = 0; i < this.qtdVertex; i++) {
            System.out.println("Grau do Vértice " + i + ": " + getDegree(i));
        }
        System.out.println("----------------------------");
    }
    
     public static Graph fromFile(String filePath) {
        Graph graph = null;
        // 1. Usamos a classe Paths para obter um objeto Path a partir da String do caminho.
        Path path = Paths.get(filePath);

        // 2. O try-with-resources agora usa Files.newBufferedReader(path).
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            // A lógica interna de leitura permanece exatamente a mesma.
            String line = reader.readLine();
            int numVertices = Integer.parseInt(line.trim());

            graph = new Graph(numVertices);

            for (int i = 0; i < numVertices; i++) {
                line = reader.readLine();
                if (line == null) {
                    throw new IOException("Arquivo incompleto: faltam linhas da matriz.");
                }
                String[] parts = line.trim().split("\\s+");
                
                for (int j = i + 1; j < numVertices; j++) {
                    int weight = Integer.parseInt(parts[j]);
                    if (weight > 0) {
                        graph.insertEdge(i, j, weight);
                    }
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao ler ou processar o arquivo do grafo: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        
        return graph;
    }
}