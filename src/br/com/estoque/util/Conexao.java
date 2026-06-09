package br.com.estoque.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilitário de conexão com o banco MySQL via JDBC.
    Requisito coberto: try / catch / finally (usado em todos os DAOs),
    e encapsulamento das credenciais nesta classe.
 */
public class Conexao {

    private static final String URL    = "jdbc:mysql://localhost:3306/estoque_carros"
                                       + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String SENHA   = "montanha";   //Senha  
    private Conexao() {}   // impede instanciação

    /*
     Retorna uma nova conexão com o banco.
     Quem chamar é responsável por fechá-la (no bloco finally do DAO).
     */
    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
