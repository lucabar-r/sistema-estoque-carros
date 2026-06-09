package br.com.estoque.model;

import br.com.estoque.interfaces.Exibivel;

/**
 * Modelo de domínio: Carro.
 *
 * Requisitos cobertos:
 *  - Classe com encapsulamento (atributos private + getters/setters)
 *  - Implementa interface Exibivel
 */
public class Carro implements Exibivel {

    // ── Encapsulamento: atributos privados ──────────────────────────
    private int    id;
    private String modelo;
    private String marca;
    private int    ano;
    private double preco;
    private int    quantidade;

    // ── Construtores ────────────────────────────────────────────────

    /** Usado ao cadastrar (sem ID — o banco gera automaticamente) */
    public Carro(String modelo, String marca, int ano, double preco, int quantidade) {
        this.modelo     = modelo;
        this.marca      = marca;
        this.ano        = ano;
        this.preco      = preco;
        this.quantidade = quantidade;
    }

    /** Usado ao carregar do banco (com ID já conhecido) */
    public Carro(int id, String modelo, String marca, int ano, double preco, int quantidade) {
        this.id         = id;
        this.modelo     = modelo;
        this.marca      = marca;
        this.ano        = ano;
        this.preco      = preco;
        this.quantidade = quantidade;
    }

    // ── Getters ─────────────────────────────────────────────────────
    public int    getId()        { return id; }
    public String getModelo()    { return modelo; }
    public String getMarca()     { return marca; }
    public int    getAno()       { return ano; }
    public double getPreco()     { return preco; }
    public int    getQuantidade(){ return quantidade; }

    // ── Setters ─────────────────────────────────────────────────────
    public void setModelo(String modelo)       { this.modelo     = modelo; }
    public void setMarca(String marca)         { this.marca      = marca; }
    public void setAno(int ano)                { this.ano        = ano; }
    public void setPreco(double preco)         { this.preco      = preco; }
    public void setQuantidade(int quantidade)  { this.quantidade = quantidade; }

    // ── Interface Exibivel ──────────────────────────────────────────
    @Override
    public void exibir() {
        System.out.println("┌─────────────────────────────┐");
        System.out.printf( "│ ID        : %-16d │%n", id);
        System.out.printf( "│ Modelo    : %-16s │%n", modelo);
        System.out.printf( "│ Marca     : %-16s │%n", marca);
        System.out.printf( "│ Ano       : %-16d │%n", ano);
        System.out.printf( "│ Preço     : R$ %-13.2f │%n", preco);
        System.out.printf( "│ Estoque   : %-16d │%n", quantidade);
        System.out.println("└─────────────────────────────┘");
    }

    @Override
    public String toString() {
        return String.format("[%d] %s %s (%d) - R$ %.2f  |  Estoque: %d",
                id, marca, modelo, ano, preco, quantidade);
    }
}
