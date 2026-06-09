package br.com.estoque.controller;

import br.com.estoque.dao.CarroDAO;
import br.com.estoque.model.Carro;

import java.util.List;
import java.util.Scanner;

/**
 * Controller de Carro — conecta o menu ao CarroDAO.
 *
 * Requisitos cobertos aqui:
 *  - switch/case (menu)
 *  - if/else if/else (validações)
 *  - for (exibição da lista)
 *  - try/catch (parse de números)
 *  - break e continue (controle de fluxo)
 *  - do/while (repetir entrada inválida)
 */
public class CarroController {

    private static final CarroDAO dao     = new CarroDAO();
    private static final Scanner  scanner = new Scanner(System.in);

    // ── Menu principal de carros ────────────────────────────────────
    public static void menuCarros() {
        String opcao = "";

        while (!opcao.equals("0")) {

            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║    GESTÃO DE CARROS          ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║ 1. Cadastrar carro           ║");
            System.out.println("║ 2. Listar estoque            ║");
            System.out.println("║ 3. Buscar por modelo         ║");
            System.out.println("║ 4. Atualizar carro           ║");
            System.out.println("║ 5. Remover carro             ║");
            System.out.println("║ 0. Voltar                    ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Opção: ");

            opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1": cadastrarCarro();  break;
                case "2": listarCarros();    break;
                case "3": buscarPorModelo(); break;
                case "4": atualizarCarro();  break;
                case "5": removerCarro();    break;
                case "0": System.out.println("Voltando..."); break;
                default:  System.out.println("Opção inválida.");
            }
        }
    }

    // ── Cadastrar ───────────────────────────────────────────────────
    private static void cadastrarCarro() {
        System.out.println("\n── Novo Carro ──");

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine().trim();

        System.out.print("Marca: ");
        String marca = scanner.nextLine().trim();

        // do/while: repete até o ano ser válido
        int ano;
        do {
            System.out.print("Ano (ex: 2020): ");
            ano = lerInteiro();
            if (ano < 1900 || ano > 2100) {
                System.out.println("Ano inválido. Tente novamente.");
            }
        } while (ano < 1900 || ano > 2100);

        // Validação de preço com if/else
        double preco = -1;
        while (preco <= 0) {
            System.out.print("Preço (R$): ");
            preco = lerDouble();
            if (preco <= 0) System.out.println("Preço deve ser maior que zero.");
        }

        System.out.print("Quantidade em estoque: ");
        int quantidade = lerInteiro();

        Carro carro = new Carro(modelo, marca, ano, preco, quantidade);
        boolean ok = dao.inserir(carro);

        if (ok) {
            System.out.println("✔ Carro cadastrado com sucesso!");
        } else {
            System.out.println("✘ Falha ao cadastrar.");
        }
    }

    // ── Listar ──────────────────────────────────────────────────────
    private static void listarCarros() {
        List<Carro> lista = dao.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum carro cadastrado.");
            return;   // return como controle de fluxo
        }

        System.out.println("\n── Estoque de Carros ──");

        // for-each para percorrer a lista
        for (Carro c : lista) {
            c.exibir();   // usa a interface Exibivel
        }

        System.out.println("Total: " + lista.size() + " carro(s).");
    }

    // ── Buscar por modelo ───────────────────────────────────────────
    private static void buscarPorModelo() {
        System.out.print("Nome do modelo (parcial): ");
        String busca = scanner.nextLine().trim();

        List<Carro> resultado = dao.buscarPorModelo(busca);

        if (resultado.isEmpty()) {
            System.out.println("Nenhum carro encontrado para \"" + busca + "\".");
            return;
        }

        // for com índice explícito — exemplo de for clássico
        for (int i = 0; i < resultado.size(); i++) {
            System.out.println("\nResultado " + (i + 1) + ":");
            resultado.get(i).exibir();
        }
    }

    // ── Atualizar ───────────────────────────────────────────────────
    private static void atualizarCarro() {
        System.out.print("ID do carro a atualizar: ");
        int id = lerInteiro();

        Carro carro = dao.buscarPorId(id);

        if (carro == null) {
            System.out.println("Carro não encontrado.");
            return;
        }

        System.out.println("Dados atuais:");
        carro.exibir();

        System.out.println("Deixe em branco para manter o valor atual.");

        System.out.print("Novo modelo [" + carro.getModelo() + "]: ");
        String modelo = scanner.nextLine().trim();
        if (!modelo.isEmpty()) carro.setModelo(modelo);   // só atualiza se preenchido

        System.out.print("Nova marca [" + carro.getMarca() + "]: ");
        String marca = scanner.nextLine().trim();
        if (!marca.isEmpty()) carro.setMarca(marca);

        System.out.print("Novo preço [" + carro.getPreco() + "]: ");
        String precoStr = scanner.nextLine().trim();
        if (!precoStr.isEmpty()) {
            try {
                double novoPreco = Double.parseDouble(precoStr);
                carro.setPreco(novoPreco);
            } catch (NumberFormatException e) {
                System.out.println("Preço inválido, mantendo o anterior.");
            }
        }

        System.out.print("Nova quantidade [" + carro.getQuantidade() + "]: ");
        String qtdStr = scanner.nextLine().trim();
        if (!qtdStr.isEmpty()) {
            try {
                carro.setQuantidade(Integer.parseInt(qtdStr));
            } catch (NumberFormatException e) {
                System.out.println("Quantidade inválida, mantendo a anterior.");
            }
        }

        dao.atualizar(carro);
    }

    // ── Remover ─────────────────────────────────────────────────────
    private static void removerCarro() {
        System.out.print("ID do carro a remover: ");
        int id = lerInteiro();

        Carro carro = dao.buscarPorId(id);
        if (carro == null) {
            System.out.println("Carro não encontrado.");
            return;
        }

        System.out.println("Carro a remover:");
        carro.exibir();

        System.out.print("Confirmar remoção? (s/n): ");
        String conf = scanner.nextLine().trim();

        // if/else if/else para confirmação
        if (conf.equalsIgnoreCase("s")) {
            dao.deletar(id);
        } else if (conf.equalsIgnoreCase("n")) {
            System.out.println("Remoção cancelada.");
        } else {
            System.out.println("Resposta inválida. Remoção cancelada.");
        }
    }

    // ── Helpers de leitura segura ───────────────────────────────────

    /** Lê inteiro — captura NumberFormatException e retorna -1 se inválido */
    static int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
            return -1;
        }
    }

    static double lerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
            return -1;
        }
    }

    public static Scanner getScanner() { return scanner; }
}
