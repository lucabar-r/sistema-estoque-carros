package br.com.estoque.controller;

import br.com.estoque.dao.CarroDAO;
import br.com.estoque.dao.ClienteDAO;
import br.com.estoque.dao.VendaDAO;
import br.com.estoque.model.Carro;
import br.com.estoque.model.Cliente;
import br.com.estoque.model.Venda;

import java.util.List;
import java.util.Scanner;

/**
 * Controller de Venda.
 * Requisitos cobertos: switch/case, if/else, for, while, try/catch, return, continue.
 */
public class VendaController {

    private static final VendaDAO   vendaDao   = new VendaDAO();
    private static final CarroDAO   carroDao   = new CarroDAO();
    private static final ClienteDAO clienteDao = new ClienteDAO();
    private static final Scanner    scanner    = CarroController.getScanner();

    public static void menuVendas() {
        String opcao = "";

        while (!opcao.equals("0")) {

            System.out.println("\n╔═══════════════════════════════╗");
            System.out.println("║    GESTÃO DE VENDAS           ║");
            System.out.println("╠═══════════════════════════════╣");
            System.out.println("║ 1. Registrar venda            ║");
            System.out.println("║ 2. Relatório de vendas        ║");
            System.out.println("║ 3. Vendas por cliente         ║");
            System.out.println("║ 4. Cancelar venda             ║");
            System.out.println("║ 0. Voltar                     ║");
            System.out.println("╚═══════════════════════════════╝");
            System.out.print("Opção: ");

            opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1": registrarVenda();    break;
                case "2": relatorioVendas();   break;
                case "3": vendasPorCliente();  break;
                case "4": cancelarVenda();     break;
                case "0": System.out.println("Voltando..."); break;
                default:  System.out.println("Opção inválida.");
            }
        }
    }

    // ── Registrar venda ─────────────────────────────────────────────
    private static void registrarVenda() {
        System.out.println("\n── Registrar Venda ──");

        // Escolher carro
        System.out.print("ID do carro: ");
        int carroId = CarroController.lerInteiro();

        Carro carro = carroDao.buscarPorId(carroId);
        if (carro == null) {
            System.out.println("Carro não encontrado.");
            return;
        }
        if (carro.getQuantidade() == 0) {
            System.out.println("Carro sem estoque disponível.");
            return;
        }
        carro.exibir();

        // Escolher cliente
        System.out.print("ID do cliente: ");
        int clienteId = CarroController.lerInteiro();

        Cliente cliente = clienteDao.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        cliente.exibir();

        // Quantidade
        int qtd;
        do {
            System.out.print("Quantidade (máx " + carro.getQuantidade() + "): ");
            qtd = CarroController.lerInteiro();

            if (qtd <= 0) {
                System.out.println("Quantidade deve ser maior que zero.");
                continue;   // continue — pula direto para a próxima iteração
            }
            if (qtd > carro.getQuantidade()) {
                System.out.println("Quantidade maior que o estoque disponível.");
                qtd = 0;    // força repetição
            }
        } while (qtd <= 0);

        // Confirmar
        double total = carro.getPreco() * qtd;
        System.out.printf("\nTotal da venda: R$ %.2f%n", total);
        System.out.print("Confirmar? (s/n): ");
        String conf = scanner.nextLine().trim();

        if (!conf.equalsIgnoreCase("s")) {
            System.out.println("Venda cancelada.");
            return;
        }

        Venda venda = new Venda(carro, cliente, qtd);
        vendaDao.registrar(venda);
    }

    // ── Relatório geral ─────────────────────────────────────────────
    private static void relatorioVendas() {
        List<Venda> vendas = vendaDao.listarTodas();

        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        System.out.println("\n══ RELATÓRIO DE VENDAS ══");

        double totalGeral = 0;

        for (Venda v : vendas) {
            v.exibir();
            totalGeral += v.getValorTotal();
        }

        System.out.printf("%n▶ Total geral de vendas: R$ %.2f%n", totalGeral);
        System.out.println("▶ Número de vendas: " + vendas.size());
    }

    // ── Vendas por cliente ──────────────────────────────────────────
    private static void vendasPorCliente() {
        System.out.print("ID do cliente: ");
        int id = CarroController.lerInteiro();

        Cliente cliente = clienteDao.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        List<Venda> vendas = vendaDao.listarPorCliente(id);

        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda para " + cliente.getNome() + ".");
            return;
        }

        System.out.println("\nVendas de " + cliente.getNome() + ":");
        double total = 0;
        for (Venda v : vendas) {
            v.exibir();
            total += v.getValorTotal();
        }
        System.out.printf("Total comprado: R$ %.2f%n", total);
    }

    // ── Cancelar venda ──────────────────────────────────────────────
    private static void cancelarVenda() {
        System.out.print("ID da venda a cancelar: ");
        int id = CarroController.lerInteiro();

        System.out.print("Confirmar cancelamento? (s/n): ");
        String conf = scanner.nextLine().trim();

        if (conf.equalsIgnoreCase("s")) {
            boolean ok = vendaDao.cancelar(id);
            System.out.println(ok ? "✔ Venda cancelada." : "✘ Venda não encontrada.");
        } else {
            System.out.println("Operação abortada.");
        }
    }
}
