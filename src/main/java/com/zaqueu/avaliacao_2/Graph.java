package com.zaqueu.avaliacao_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 *
 * @author zaqueu
 */
public class Graph {
    int qtdVertex;
    int qtdEdge;
    int[][] distance;
    int[][] adjacent;

    /**
     * NOVO CONSTRUTOR - Esta é a correção principal.
     * Inicializa o grafo com um número definido de vértices.
     * @param numVertices O número total de vértices no grafo.
     */
    public Graph(int numVertices) {
        if (numVertices < 0) throw new IllegalArgumentException("O número de vértices não pode ser negativo.");
        this.qtdVertex = numVertices;
        this.qtdEdge = 0;
        this.adjacent = new int[numVertices][numVertices];
        this.distance = new int[numVertices][numVertices];
        
        // Preenche a matriz de distância com um valor "infinito"
        for (int i = 0; i < numVertices; i++) {
            Arrays.fill(this.distance[i], Integer.MAX_VALUE);
            this.distance[i][i] = 0; // Distância de um vértice para ele mesmo é 0
        }
    }
    
    public void insertEdge(int v1, int v2, int weight){
        if (v1 < 0 || v1 >= qtdVertex || v2 < 0 || v2 >= qtdVertex) {
            System.err.println("Erro ao inserir: Vértice inválido. Vértices devem estar entre 0 e " + (qtdVertex-1));
            return;
        }
        if (adjacent[v1][v2] == 0) {
            adjacent[v1][v2] = 1;
            adjacent[v2][v1] = 1;
            distance[v1][v2] = weight;
            distance[v2][v1] = weight;
            this.qtdEdge++;
        }
    }
    
    public void removeEdge(int v1, int v2){
        if (v1 < 0 || v1 >= qtdVertex || v2 < 0 || v2 >= qtdVertex) {
            System.err.println("Erro ao remover: Vértice inválido. Vértices devem estar entre 0 e " + (qtdVertex-1));
            return;
        }
        if (adjacent[v1][v2] == 1) {
            adjacent[v1][v2] = 0;
            adjacent[v2][v1] = 0;
            distance[v1][v2] = Integer.MAX_VALUE;
            distance[v2][v1] = Integer.MAX_VALUE;
            this.qtdEdge--;
        }
    }
    
    public void showGraph(){
        System.out.println("\n--- Informações do Grafo ---");
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
                if (adjacent[i][j] == 1 && i != j) {
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

    public void showDegrees() {
        System.out.println("\n--- Grau de Cada Vértice ---");
        for (int i = 0; i < this.qtdVertex; i++) {
            System.out.println("Grau do Vértice " + i + ": " + getDegree(i));
        }
        System.out.println("----------------------------");
    }

    public int getDegree(int vertex) {
        if (vertex < 0 || vertex >= this.qtdVertex) {
            throw new IllegalArgumentException("Vértice inválido: " + vertex + ". Válidos de 0 a " + (qtdVertex-1));
        }
        int degree = 0;
        for (int j = 0; j < this.qtdVertex; j++) {
             if(adjacent[vertex][j] == 1 && vertex != j) {
                degree++;
            }
        }
        return degree;
    }
    
    public static Graph fromFile(String filePath) {
        Graph graph;
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            System.err.println("Erro: Arquivo não encontrado em '" + filePath + "'");
            return null;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();
            int numVertices = Integer.parseInt(line.trim());
            
            // Esta linha agora funciona, pois o construtor existe!
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
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Erro ao ler ou processar o arquivo do grafo: " + e.getMessage());
            return null;
        }
        return graph;
    }
}