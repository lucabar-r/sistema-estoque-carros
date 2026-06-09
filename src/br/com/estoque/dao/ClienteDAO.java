package br.com.estoque.dao;

import br.com.estoque.model.Cliente;
import br.com.estoque.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Cliente — CRUD completo com PreparedStatement.
 */
public class ClienteDAO {

    // ── CREATE ──────────────────────────────────────────────────────
    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, telefone) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());

            return stmt.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            // CPF duplicado cai aqui — exemplo de catch específico
            System.err.println("CPF já cadastrado: " + cliente.getCpf());
            return false;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
            return false;
        } finally {
            fecharConexao(conn);
        }
    }

    // ── READ — por ID ───────────────────────────────────────────────
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearCliente(rs);
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
            return null;
        } finally {
            fecharConexao(conn);
        }
    }

    // ── READ — por CPF ──────────────────────────────────────────────
    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearCliente(rs);
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por CPF: " + e.getMessage());
            return null;
        } finally {
            fecharConexao(conn);
        }
    }

    // ── READ — listar todos ─────────────────────────────────────────
    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM cliente ORDER BY nome";
        List<Cliente> lista = new ArrayList<>();
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        } finally {
            fecharConexao(conn);
        }

        return lista;
    }

    // ── UPDATE ──────────────────────────────────────────────────────
    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome=?, cpf=?, telefone=? WHERE id=?";
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setInt   (4, cliente.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        } finally {
            fecharConexao(conn);
        }
    }

    // ── DELETE ──────────────────────────────────────────────────────
    public boolean deletar(int id) {
        String sql = "DELETE FROM cliente WHERE id=?";
        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println("Cliente removido.");
                return true;
            } else {
                System.out.println("Cliente não encontrado.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        } finally {
            fecharConexao(conn);
        }
    }

    // ── Helpers ─────────────────────────────────────────────────────
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt   ("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("telefone")
        );
    }

    private void fecharConexao(Connection conn) {
        if (conn != null) {
            try { conn.close(); }
            catch (SQLException e) { System.err.println("Erro ao fechar conexão: " + e.getMessage()); }
        }
    }
}
