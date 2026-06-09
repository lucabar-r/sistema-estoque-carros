package br.com.estoque.dao;

import br.com.estoque.model.Carro;
import br.com.estoque.model.Cliente;
import br.com.estoque.model.Venda;
import br.com.estoque.util.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
  DAO de Venda — CRUD completo. 
  O método registrar() usa transação (commit/rollback) para garantir que
  o INSERT na venda E o UPDATE do estoque aconteçam juntos ou nenhum dos dois.
 */
public class VendaDAO {

    private final CarroDAO carroDAO = new CarroDAO();

    // ── CREATE — com transação ──────────────────────────────────────
    public boolean registrar(Venda venda) {
        String sqlVenda = "INSERT INTO venda (carro_id, cliente_id, quantidade, valor_total, data_venda) "
                        + "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            conn.setAutoCommit(false);   // inicia transação

            // 1. Verificar se há estoque suficiente
            Carro carro = venda.getCarro();
            int novaQtd = carro.getQuantidade() - venda.getQuantidade();

            if (novaQtd < 0) {
                System.out.println("Estoque insuficiente! Disponível: " + carro.getQuantidade());
                return false;
            }

            // 2. Inserir a venda
            PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda);
            stmtVenda.setInt   (1, carro.getId());
            stmtVenda.setInt   (2, venda.getCliente().getId());
            stmtVenda.setInt   (3, venda.getQuantidade());
            stmtVenda.setDouble(4, venda.getValorTotal());
            stmtVenda.setDate  (5, Date.valueOf(venda.getDataVenda()));
            stmtVenda.executeUpdate();

            // 3. Atualizar estoque do carro
            String sqlEstoque = "UPDATE carro SET quantidade=? WHERE id=?";
            PreparedStatement stmtEstoque = conn.prepareStatement(sqlEstoque);
            stmtEstoque.setInt(1, novaQtd);
            stmtEstoque.setInt(2, carro.getId());
            stmtEstoque.executeUpdate();

            conn.commit();   // tudo certo: confirma transação
            System.out.println("Venda registrada com sucesso! Valor: R$ " + venda.getValorTotal());
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao registrar venda: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();   // erro: desfaz tudo
            } catch (SQLException ex) {
                System.err.println("Erro no rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (conn != null) { conn.setAutoCommit(true); conn.close(); }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // ── READ — listar todas as vendas ───────────────────────────────
    public List<Venda> listarTodas() {
        String sql = "SELECT v.*, "
                   + "  c.modelo, c.marca, c.ano, c.preco, c.quantidade AS estoque, "
                   + "  cl.nome, cl.cpf, cl.telefone "
                   + "FROM venda v "
                   + "JOIN carro   c  ON v.carro_id   = c.id "
                   + "JOIN cliente cl ON v.cliente_id = cl.id "
                   + "ORDER BY v.data_venda DESC";

        List<Venda> lista = new ArrayList<>();
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearVenda(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas: " + e.getMessage());
        } finally {
            fecharConexao(conn);
        }

        return lista;
    }

    // ── READ — vendas por cliente ───────────────────────────────────
    public List<Venda> listarPorCliente(int clienteId) {
        String sql = "SELECT v.*, "
                   + "  c.modelo, c.marca, c.ano, c.preco, c.quantidade AS estoque, "
                   + "  cl.nome, cl.cpf, cl.telefone "
                   + "FROM venda v "
                   + "JOIN carro   c  ON v.carro_id   = c.id "
                   + "JOIN cliente cl ON v.cliente_id = cl.id "
                   + "WHERE v.cliente_id = ? "
                   + "ORDER BY v.data_venda DESC";

        List<Venda> lista = new ArrayList<>();
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearVenda(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar vendas do cliente: " + e.getMessage());
        } finally {
            fecharConexao(conn);
        }

        return lista;
    }

    // ── DELETE ──────────────────────────────────────────────────────
    public boolean cancelar(int id) {
        String sql = "DELETE FROM venda WHERE id=?";
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao cancelar venda: " + e.getMessage());
            return false;
        } finally {
            fecharConexao(conn);
        }
    }

    // ── Helpers ─────────────────────────────────────────────────────
    private Venda mapearVenda(ResultSet rs) throws SQLException {
        Carro carro = new Carro(
                rs.getInt   ("carro_id"),
                rs.getString("modelo"),
                rs.getString("marca"),
                rs.getInt   ("ano"),
                rs.getDouble("preco"),
                rs.getInt   ("estoque")
        );
        Cliente cliente = new Cliente(
                rs.getInt   ("cliente_id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("telefone")
        );
        return new Venda(
                rs.getInt   ("id"),
                carro,
                cliente,
                rs.getInt   ("quantidade"),
                rs.getDouble("valor_total"),
                rs.getDate  ("data_venda").toLocalDate()
        );
    }

    private void fecharConexao(Connection conn) {
        if (conn != null) {
            try { conn.close(); }
            catch (SQLException e) { System.err.println("Erro ao fechar: " + e.getMessage()); }
        }
    }
}
