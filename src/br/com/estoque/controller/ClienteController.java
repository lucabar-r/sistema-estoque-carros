package br.com.estoque.controller;

import br.com.estoque.dao.ClienteDAO;
import br.com.estoque.model.Cliente;

import java.util.List;
import java.util.Scanner;

/**
 * Controller de Cliente.
 * Requisitos cobertos: switch/case, if/else, for, while, try/catch, return.
 */
public class ClienteController {

    private static final ClienteDAO dao     = new ClienteDAO();
    private static final Scanner    scanner = CarroController.getScanner();

    public static void menuClientes() {
        String opcao = "";

        while (!opcao.equals("0")) {

            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║    GESTÃO DE CLIENTES        ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║ 1. Cadastrar cliente         ║");
            System.out.println("║ 2. Listar clientes           ║");
            System.out.println("║ 3. Buscar por CPF            ║");
            System.out.println("║ 4. Atualizar cliente         ║");
            System.out.println("║ 5. Remover cliente           ║");
            System.out.println("║ 0. Voltar                    ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Opção: ");

            opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1": cadastrarCliente();  break;
                case "2": listarClientes();    break;
                case "3": buscarPorCpf();      break;
                case "4": atualizarCliente();  break;
                case "5": removerCliente();    break;
                case "0": System.out.println("Voltando..."); break;
                default:  System.out.println("Opção inválida.");
            }
        }
    }

    // ── Cadastrar ───────────────────────────────────────────────────
    private static void cadastrarCliente() {
        System.out.println("\n── Novo Cliente ──");

        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();

        // Validação: CPF não pode estar vazio
        String cpf;
        do {
            System.out.print("CPF (ex: 111.222.333-44): ");
            cpf = scanner.nextLine().trim();
            if (cpf.isEmpty()) System.out.println("CPF obrigatório.");
        } while (cpf.isEmpty());

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();

        Cliente cliente = new Cliente(nome, cpf, telefone);
        boolean ok = dao.inserir(cliente);

        if (ok) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Falha ao cadastrar (CPF já existe?).");
        }
    }

    // ── Listar ──────────────────────────────────────────────────────
    private static void listarClientes() {
        List<Cliente> lista = dao.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n── Clientes Cadastrados ──");
        for (Cliente c : lista) {
            c.exibir();
        }
        System.out.println("Total: " + lista.size() + " cliente(s).");
    }

    // ── Buscar por CPF ───────────────────────────────────────────────
    private static void buscarPorCpf() {
        System.out.print("CPF: ");
        String cpf = scanner.nextLine().trim();

        Cliente cliente = dao.buscarPorCpf(cpf);

        if (cliente != null) {
            cliente.exibir();
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    // ── Atualizar ───────────────────────────────────────────────────
    private static void atualizarCliente() {
        System.out.print("ID do cliente: ");
        int id = CarroController.lerInteiro();

        Cliente cliente = dao.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        cliente.exibir();
        System.out.println("Deixe em branco para manter.");

        System.out.print("Novo nome [" + cliente.getNome() + "]: ");
        String nome = scanner.nextLine().trim();
        if (!nome.isEmpty()) cliente.setNome(nome);

        System.out.print("Novo telefone [" + cliente.getTelefone() + "]: ");
        String tel = scanner.nextLine().trim();
        if (!tel.isEmpty()) cliente.setTelefone(tel);

        boolean ok = dao.atualizar(cliente);
        System.out.println(ok ? "Atualizado." : "Falha na atualização.");
    }

    // ── Remover ─────────────────────────────────────────────────────
    private static void removerCliente() {
        System.out.print("ID do cliente a remover: ");
        int id = CarroController.lerInteiro();

        Cliente cliente = dao.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        cliente.exibir();
        System.out.print("Confirmar remoção? (s/n): ");
        String conf = scanner.nextLine().trim();

        if (conf.equalsIgnoreCase("s")) {
            dao.deletar(id);
        } else {
            System.out.println("Cancelado.");
        }
    }

    /** Retorna o DAO para uso externo (ex: VendaController) */
    public static ClienteDAO getDao() { return dao; }
}
