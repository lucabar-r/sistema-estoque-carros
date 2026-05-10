package br.com.estoque.view;

import java.util.Scanner;


 //Sprint 1 - Menu Principal do Sistema de Estoque de Carros
 //Atende aos requisitos de: Estrutura de Decisão (Switch/Case), 
 //Controle de Fluxo (Try/Catch) e Repetição (While).
 
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        System.out.println("SISTEMA DE ESTOQUE INICIALIZADO...");

        // Estrutura de repetição para o menu permanecer ativo
        while (opcao != 0) {
            System.out.println("\n=======================================");
            System.out.println("      CONTROLE DE ESTOQUE - VEÍCULOS");
            System.out.println("=======================================");
            System.out.println("1. GESTAO DE CARROS (Cadastrar/Listar)");
            System.out.println("2. GESTAO DE CLIENTES");
            System.out.println("3. REGISTRAR VENDA");
            System.out.println("4. RELATORIO DE VENDAS");
            System.out.println("0. SAIR");
            System.out.print("Escolha uma opçao: ");

            try {
                // Leitura da opção e tratamento de erro para letras/caracteres
                opcao = Integer.parseInt(scanner.nextLine());

                // Estrutura de decisão baseada no Diagrama de Casos de Uso
                switch (opcao) {
                    case 1:
                        menuCarros();
                        break;
                    case 2:
                        System.out.println("-> Módulo de Clientes (Sprint 2)");
                        break;
                    case 3:
                        System.out.println("-> Iniciando Fluxo de Venda...");
                        break;
                    case 4:
                        System.out.println("-> Gerando Relatório de Vendas (Sprint 2)");
                        break;
                    case 0:
                        System.out.println("Encerrando sistema... Até logo!");
                        break;
                    default:
                        System.out.println("Aviso: Opçao inexistente. Tente 0 a 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida! Digite apenas números.");
            }
        }
        scanner.close();
    }

    // Exemplo de sub-menu para atender ao requisito de CRUD do UML
    private static void menuCarros() {
        System.out.println("\n--- SUB-MENU: GESTAO DE CARROS ---");
        System.out.println("A. Cadastrar Carro");
        System.out.println("B. Listar Estoque");
        System.out.println("C. Buscar por Modelo");
        System.out.println("(Implementação de lógica na Sprint 2)");
    }
}