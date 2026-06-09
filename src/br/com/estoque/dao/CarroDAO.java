package br.com.estoque.dao;

import br.com.estoque.model.Carro;
import br.com.estoque.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Carro — CRUD completo com PreparedStatement.
 *
 * Requisitos cobertos:
 *  - Persistência MySQL (Create / Read / Update / Delete)
 *  - PreparedStatement em todas as operações
 *  - try / catch / finally em cada método
 *  - Estrutura de repetição (while no ResultSet, for na listagem)
 */
public class CarroDAO {

    // ──────────────────────────────────────────────────────────────
    // CREATE
    // ──────────────────────────────────────────────────────────────
    public boolean inserir(Carro carro) {
        String sql = "INSERT INTO carro (modelo, marca, ano, preco, quantidade) "
                   + "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, carro.getModelo());
            stmt.setString(2, carro.getMarca());
            stmt.setInt   (3, carro.getAno());
            stmt.setDouble(4, carro.getPreco());
            stmt.setInt   (5, carro.getQuantidade());

            int linhas = stmt.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir carro: " + e.getMessage());
            return false;
        } finally {
            fecharConexao(conn);   // sempre executa — cobre requisito finally
        }
    }

    // ──────────────────────────────────────────────────────────────
    // READ — buscar por ID
    // ──────────────────────────────────────────────────────────────
    public Carro buscarPorId(int id) {
        String sql = "SELECT * FROM carro WHERE id = ?";
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {                           // if/else cobre requisito decisão
                return mapearCarro(rs);
            } else {
                System.out.println("Carro com ID " + id + " não encontrado.");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar carro: " + e.getMessage());
            return null;
        } finally {
            fecharConexao(conn);
        }
    }

    // ──────────────────────────────────────────────────────────────
    // READ — listar todos
    // ──────────────────────────────────────────────────────────────
    public List<Carro> listarTodos() {
        String sql = "SELECT * FROM carro ORDER BY marca, modelo";
        List<Carro> lista = new ArrayList<>();
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {                        // while no ResultSet
                lista.add(mapearCarro(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar carros: " + e.getMessage());
        } finally {
            fecharConexao(conn);
        }

        return lista;
    }

    // ──────────────────────────────────────────────────────────────
    // READ — buscar por modelo (like)
    // ──────────────────────────────────────────────────────────────
    public List<Carro> buscarPorModelo(String modelo) {
        String sql = "SELECT * FROM carro WHERE modelo LIKE ?";
        List<Carro> lista = new ArrayList<>();
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + modelo + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapearCarro(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar por modelo: " + e.getMessage());
        } finally {
            fecharConexao(conn);
        }

        return lista;
    }

    // ──────────────────────────────────────────────────────────────
    // UPDATE
    // ──────────────────────────────────────────────────────────────
    public boolean atualizar(Carro carro) {
        String sql = "UPDATE carro SET modelo=?, marca=?, ano=?, preco=?, quantidade=? "
                   + "WHERE id=?";
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, carro.getModelo());
            stmt.setString(2, carro.getMarca());
            stmt.setInt   (3, carro.getAno());
            stmt.setDouble(4, carro.getPreco());
            stmt.setInt   (5, carro.getQuantidade());
            stmt.setInt   (6, carro.getId());

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Carro atualizado com sucesso.");
                return true;
            } else {
                System.out.println("Nenhum carro encontrado com esse ID.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar carro: " + e.getMessage());
            return false;
        } finally {
            fecharConexao(conn);
        }
    }

    // ──────────────────────────────────────────────────────────────
    // UPDATE parcial — apenas quantidade (usado pela Venda)
    // ──────────────────────────────────────────────────────────────
    public boolean atualizarQuantidade(int id, int novaQuantidade) {
        String sql = "UPDATE carro SET quantidade=? WHERE id=?";
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar estoque: " + e.getMessage());
            return false;
        } finally {
            fecharConexao(conn);
        }
    }

    // ──────────────────────────────────────────────────────────────
    // DELETE
    // ──────────────────────────────────────────────────────────────
    public boolean deletar(int id) {
        String sql = "DELETE FROM carro WHERE id=?";
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Carro removido com sucesso.");
                return true;
            } else {
                System.out.println("Carro não encontrado.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar carro: " + e.getMessage());
            return false;
        } finally {
            fecharConexao(conn);
        }
    }

    // ──────────────────────────────────────────────────────────────
    // Helpers privados
    // ──────────────────────────────────────────────────────────────

    /** Converte uma linha do ResultSet em objeto Carro */
    private Carro mapearCarro(ResultSet rs) throws SQLException {
        return new Carro(
                rs.getInt   ("id"),
                rs.getString("modelo"),
                rs.getString("marca"),
                rs.getInt   ("ano"),
                rs.getDouble("preco"),
                rs.getInt   ("quantidade")
        );
    }

    /** Fecha conexão com segurança — sempre chamado no finally */
    private void fecharConexao(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
