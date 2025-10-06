package com.zaqueu.avaliacao_2;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe principal que gerencia o menu interativo para manipulação de um grafo.
 * Ela depende da classe 'Graph' que deve estar no mesmo pacote.
 * * @author zaqueu
 */
public class MenuPrincipal {

    private static Graph graph = null; // O objeto grafo que será manipulado
    private static final Scanner scanner = new Scanner(System.in); // Scanner para entrada do usuário

    public static void main(String[] args) {
        int option;

        do {
            showMenu();
            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha pendente

                handleOption(option);

            } catch (InputMismatchException e) {
                System.out.println("\n>>> Erro: Por favor, digite um número válido. <<<\n");
                scanner.nextLine(); // Limpar o buffer do scanner
                option = -1; // Forçar a continuação do loop
            }

        } while (option != 0);

        System.out.println("Encerrando o programa. Até mais!");
        scanner.close();
    }

    /**
     * Exibe o menu de opções para o usuário.
     */
    private static void showMenu() {
        System.out.println("\n---------- MENU DE OPERAÇÕES COM GRAFO ----------");
        System.out.println("1. Carregar grafo a partir de um arquivo");
        System.out.println("2. Mostrar informações do grafo (lista de adjacência)");
        System.out.println("3. Mostrar grau de todos os vértices");
        System.out.println("4. Consultar grau de um vértice específico");
        System.out.println("5. Adicionar Aresta");
        System.out.println("6. Remover Aresta");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Direciona a ação do programa com base na escolha do usuário.
     * @param option A opção escolhida.
     */
    private static void handleOption(int option) {
        switch (option) {
            case 1:
                loadGraphFromFile();
                break;
            case 2:
                if (isGraphLoaded()) graph.showGraph();
                break;
            case 3:
                if (isGraphLoaded()) graph.showDegrees();
                break;
            case 4:
                if (isGraphLoaded()) showSpecificDegree();
                break;
            case 5:
                if (isGraphLoaded()) addEdge();
                break;
            case 6:
                if (isGraphLoaded()) removeEdge();
                break;
            case 0:
                break; // Apenas sai do switch, o loop principal irá terminar
            default:
                System.out.println("\n>>> Opção inválida! Tente novamente. <<<\n");
                break;
        }
    }

    /**
     * Verifica se o grafo já foi carregado e exibe uma mensagem caso não tenha sido.
     * @return true se o grafo estiver carregado, false caso contrário.
     */
    private static boolean isGraphLoaded() {
        if (graph == null) {
            System.out.println("\n>>> Nenhum grafo carregado. Use a opção 1 primeiro. <<<\n");
            return false;
        }
        return true;
    }

    /**
     * Solicita o caminho do arquivo e carrega o grafo.
     */
    private static void loadGraphFromFile() {
        System.out.print("Digite o caminho completo para o arquivo (ex: /home/zaqueu/Downloads/Dist5.txt): ");
        String path = scanner.nextLine();
        // Chama o método estático da classe Graph
        graph = Graph.fromFile(path);
        if (graph != null) {
            System.out.println("\n+++ Grafo carregado com sucesso! +++");
        } else {
            System.out.println("\n--- Falha ao carregar o grafo. Verifique o caminho e o formato do arquivo. ---");
        }
    }
    
    /**
     * Solicita um vértice ao usuário e exibe seu grau.
     */
    private static void showSpecificDegree() {
        try {
            System.out.print("Digite o número do vértice para consultar o grau: ");
            int vertex = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha
            int degree = graph.getDegree(vertex);
            System.out.println("-> O grau do vértice " + vertex + " é: " + degree);
        } catch (InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. Por favor, digite um número inteiro.");
            scanner.nextLine(); // Limpar buffer
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Solicita os dados de uma nova aresta e a insere no grafo.
     */
    private static void addEdge() {
        try {
            System.out.print("Digite o primeiro vértice: ");
            int v1 = scanner.nextInt();
            System.out.print("Digite o segundo vértice: ");
            int v2 = scanner.nextInt();
            System.out.print("Digite o peso da aresta: ");
            int weight = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            graph.insertEdge(v1, v2, weight);
            System.out.println("+++ Aresta entre " + v1 + " e " + v2 + " com peso " + weight + " adicionada (se não existia). +++");
        } catch (InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. Vértices e peso devem ser números inteiros.");
            scanner.nextLine(); // Limpar buffer
        }
    }

    /**
     * Solicita os dados de uma aresta e a remove do grafo.
     */
    private static void removeEdge() {
         try {
            System.out.print("Digite o primeiro vértice da aresta a ser removida: ");
            int v1 = scanner.nextInt();
            System.out.print("Digite o segundo vértice: ");
            int v2 = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            graph.removeEdge(v1, v2);
            System.out.println("--- Aresta entre " + v1 + " e " + v2 + " removida (se existia). ---");
        } catch (InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. Vértices devem ser números inteiros.");
            scanner.nextLine(); // Limpar buffer
        }
    }
}