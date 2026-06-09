package br.com.estoque.model;

import br.com.estoque.interfaces.Exibivel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Modelo de domínio: Venda.
 *
 * Representa o relacionamento entre Cliente e Carro.
 * Requisitos cobertos:
 *  - Composição (Venda possui Cliente e Carro)
 *  - Implementa interface Exibivel
 */
public class Venda implements Exibivel {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private int       id;
    private Carro     carro;
    private Cliente   cliente;
    private int       quantidade;
    private double    valorTotal;
    private LocalDate dataVenda;

    /** Construtor sem ID (para novo registro) */
    public Venda(Carro carro, Cliente cliente, int quantidade) {
        this.carro      = carro;
        this.cliente    = cliente;
        this.quantidade = quantidade;
        this.valorTotal = carro.getPreco() * quantidade;
        this.dataVenda  = LocalDate.now();
    }

    /** Construtor completo (leitura do banco) */
    public Venda(int id, Carro carro, Cliente cliente,
                 int quantidade, double valorTotal, LocalDate dataVenda) {
        this.id         = id;
        this.carro      = carro;
        this.cliente    = cliente;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.dataVenda  = dataVenda;
    }

    // ── Getters ─────────────────────────────────────────────────────
    public int       getId()        { return id; }
    public Carro     getCarro()     { return carro; }
    public Cliente   getCliente()   { return cliente; }
    public int       getQuantidade(){ return quantidade; }
    public double    getValorTotal(){ return valorTotal; }
    public LocalDate getDataVenda() { return dataVenda; }

    // ── Interface Exibivel ──────────────────────────────────────────
    @Override
    public void exibir() {
        System.out.println("┌───────────────────────────────────────┐");
        System.out.printf( "│ Venda ID   : %-24d │%n", id);
        System.out.printf( "│ Data       : %-24s │%n", dataVenda.format(FMT));
        System.out.printf( "│ Cliente    : %-24s │%n", cliente.getNome());
        System.out.printf( "│ Carro      : %-24s │%n", carro.getMarca() + " " + carro.getModelo());
        System.out.printf( "│ Quantidade : %-24d │%n", quantidade);
        System.out.printf( "│ Total      : R$ %-21.2f │%n", valorTotal);
        System.out.println("└───────────────────────────────────────┘");
    }

    @Override
    public String toString() {
        return String.format("[%d] %s → %s %s  x%d  R$ %.2f  (%s)",
                id, cliente.getNome(),
                carro.getMarca(), carro.getModelo(),
                quantidade, valorTotal,
                dataVenda.format(FMT));
    }
}
