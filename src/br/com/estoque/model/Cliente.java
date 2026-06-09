package br.com.estoque.model;

import br.com.estoque.interfaces.Exibivel;

/**
 * Modelo de domínio: Cliente.
 *
 * Requisitos cobertos:
 *  - Encapsulamento (atributos private + getters/setters)
 *  - Implementa interface Exibivel
 */
public class Cliente implements Exibivel {

    private int    id;
    private String nome;
    private String cpf;
    private String telefone;

    /** Construtor sem ID (para cadastro novo) */
    public Cliente(String nome, String cpf, String telefone) {
        this.nome     = nome;
        this.cpf      = cpf;
        this.telefone = telefone;
    }

    /** Construtor com ID (para leitura do banco) */
    public Cliente(int id, String nome, String cpf, String telefone) {
        this.id       = id;
        this.nome     = nome;
        this.cpf      = cpf;
        this.telefone = telefone;
    }

    // ── Getters ─────────────────────────────────────────────────────
    public int    getId()      { return id; }
    public String getNome()    { return nome; }
    public String getCpf()     { return cpf; }
    public String getTelefone(){ return telefone; }

    // ── Setters ─────────────────────────────────────────────────────
    public void setNome(String nome)        { this.nome     = nome; }
    public void setCpf(String cpf)          { this.cpf      = cpf; }
    public void setTelefone(String telefone){ this.telefone = telefone; }

    // ── Interface Exibivel ──────────────────────────────────────────
    @Override
    public void exibir() {
        System.out.println("┌─────────────────────────────┐");
        System.out.printf( "│ ID        : %-16d │%n", id);
        System.out.printf( "│ Nome      : %-16s │%n", nome);
        System.out.printf( "│ CPF       : %-16s │%n", cpf);
        System.out.printf( "│ Telefone  : %-16s │%n", telefone);
        System.out.println("└─────────────────────────────┘");
    }

    @Override
    public String toString() {
        return String.format("[%d] %s  CPF: %s  Tel: %s", id, nome, cpf, telefone);
    }
}
