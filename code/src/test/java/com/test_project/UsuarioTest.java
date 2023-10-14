package com.test_project;

import org.junit.jupiter.api.Test;
import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

public class UsuarioTest {

    private Usuario cliente;

    @BeforeEach
    void setUp(){
        LocalDate data = LocalDate.of(2003, 7, 27);
        cliente = new Usuario("FRANCISCO RENAN LEITE DA COSTA", "07769719305", "renanleitedacosta@gmail.com", null, "renan123", "RenanCosta", data);
    }

    /**
     * Verifica se o método cadastra o usuário corretamente no banco de dados.
     */
    @Test
    void cadastrar(){

        try {
            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM " + 
                "cliente WHERE cpf = ?");

            pstmt.setString(1, this.cliente.getCpf());

            ResultSet rs = pstmt.executeQuery();

            this.cliente.cadastrar();

            Usuario usuario = null;

            while (rs.next()) {
                usuario = new Usuario(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(7).toLocalDate());
            }

            assertEquals(this.cliente, usuario);

            this.cliente.excluirUsuario();

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
