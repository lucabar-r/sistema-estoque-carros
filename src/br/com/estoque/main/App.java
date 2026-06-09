package br.com.estoque.main;

import br.com.estoque.controller.CarroController;
import br.com.estoque.controller.ClienteController;
import br.com.estoque.controller.VendaController;
import br.com.estoque.util.Conexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Ponto de entrada do sistema.
 *
 * Requisitos cobertos:
 *  - while (loop principal do menu)
 *  - switch/case (opções)
 *  - try/catch (verificação da conexão ao iniciar)
 *  - break (saída do switch)
 */
public class App {

    public static void main(String[] args) {

        // ── Teste de conexão ao iniciar ────────────────────────────
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE ESTOQUE - CARROS      ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.print("Verificando conexão com o banco... ");

        try {
            Connection conn = Conexao.getConexao();
            conn.close();
            System.out.println("Conectado!");
        } catch (SQLException e) {
            System.out.println("\nFalha na conexão: " + e.getMessage());
            System.out.println("Verifique se o MySQL está rodando e se as credenciais na Classe Conexao.java estão corretas.");
            System.exit(1);   // encerra se não conectar
        }

        // ── Menu principal ─────────────────────────────────────────
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n╔═══════════════════════════════════╗");
            System.out.println("║   CONTROLE DE ESTOQUE - VEÍCULOS  ║");
            System.out.println("╠═══════════════════════════════════╣");
            System.out.println("║  1. Gestão de Carros              ║");
            System.out.println("║  2. Gestão de Clientes            ║");
            System.out.println("║  3. Registrar Venda               ║");
            System.out.println("║  4. Relatório de Vendas           ║");
            System.out.println("║  0. Sair                          ║");
            System.out.println("╚═══════════════════════════════════╝");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());

                switch (opcao) {
                    case 1:
                        CarroController.menuCarros();
                        break;
                    case 2:
                        ClienteController.menuClientes();
                        break;
                    case 3:
                        VendaController.menuVendas();
                        break;
                    case 4:
                        VendaController.menuVendas();   // redireciona para o sub-menu de vendas
                        break;
                    case 0:
                        System.out.println("Sistema encerrado. Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
            }
        }

        scanner.close();
    }
}
