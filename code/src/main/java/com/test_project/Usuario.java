package com.test_project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario {
    private int id;
    private String nome_completo;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;
    private String usuario;
    private ArrayList<Endereco> enderecos;
    private LocalDate dataNasc;
    
    public Usuario(String nome_completo, String cpf, String email, String telefone, String senha,
            String usuario, LocalDate dataNasc) {
        this.nome_completo = nome_completo;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.usuario = usuario;
        this.dataNasc = dataNasc;
    }

    public Usuario(int id, String nome_completo, String cpf, String email, String telefone, String senha,
            String usuario, ArrayList<Endereco> endereco, LocalDate dataNasc) {
        this.id = id;
        this.nome_completo = nome_completo;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.usuario = usuario;
        this.enderecos = endereco;
        this.dataNasc = dataNasc;
    }

    public boolean cadastrar(){
        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            if (this.validarDados()) {

                PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM " + 
                    "cliente WHERE cpf = ? OR usuario = ?");

                pstmt.setString(1, getCpf());
                pstmt.setString(2, getUsuario());
                ResultSet rs = pstmt.executeQuery();

                if (!rs.next()) {
                    pstmt = connection.prepareStatement(
                        "INSERT INTO " + 
                        "cliente(nome_completo, cpf, email, telefone, usuario, senha, datanasc)" + 
                        "VALUES(?, ?, ?, ?, ?, ?, ?)");

                        pstmt.setString(1, getNome_completo());
                        pstmt.setString(2, getCpf());
                        pstmt.setString(3, getEmail());
                        pstmt.setString(4, getTelefone());
                        pstmt.setString(5, getUsuario());
                        pstmt.setString(6, getSenha());
                        pstmt.setDate(7, Date.valueOf(dataNasc));
                        pstmt.executeUpdate();

                    return true;
                }

                return false;
                
            }
            
            return false;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static Usuario login(String usuario, String senha){
        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM " + 
                "cliente WHERE usuario = ? AND senha = ?");

            pstmt.setString(1, usuario);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                Usuario cliente;
                int id = rs.getInt(1);
                String nome_completo = rs.getString(2);
                String cpf = rs.getString(3);
                String email = rs.getString(4);
                String telefone = rs.getString(5);
                ArrayList<Endereco> enderecos = new ArrayList<>();
                java.sql.Date dataSql = rs.getDate(8);
                LocalDate dataNasc = dataSql.toLocalDate();

                PreparedStatement pstmt2 = connection.prepareStatement(
                    "SELECT * FROM endereco " +
                    "WHERE cliente = ?");
                
                pstmt2.setInt(1, id);

                ResultSet rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    Endereco endereco;

                    int id_endereco = rs2.getInt(1);
                    String estado = rs2.getString(3);
                    String cidade = rs2.getString(4);
                    String bairro = rs2.getString(5);
                    String rua = rs2.getString(6);
                    int numero = rs2.getInt(7);
                    String complemento = rs2.getString(8);
                    String cep = rs2.getString(9);

                    endereco = new Endereco(id_endereco, estado, cidade, bairro, rua, complemento, cep, numero);
                    enderecos.add(endereco);
                }

                cliente = new Usuario(id, nome_completo, cpf, email, telefone, senha, usuario, enderecos, dataNasc);
                
                return cliente;
            }

            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean validarDados(){
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean editarInformacoes(){
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean recuperarSenha(){
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean excluirUsuario(){
        try {
            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM " + 
                "cliente WHERE cpf = ?");

            pstmt.setString(1, getCpf());
            pstmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<Pedido> listarPedidos(){
        try {
            ArrayList<Pedido> pedidos = new ArrayList<>();

            return pedidos;
        } catch (Exception e) {
            return null;
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome_completo() {
        return nome_completo;
    }
    public void setNome_completo(String nome_completo) {
        this.nome_completo = nome_completo;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public ArrayList<Endereco> getEnderecos() {
        return enderecos;
    }
    public void setEnderecos(ArrayList<Endereco> endereco) {
        this.enderecos = endereco;
    }
    public LocalDate getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome_completo=" + nome_completo + ", cpf=" + cpf + ", email=" + email
                + ", telefone=" + telefone + ", senha=" + senha + ", usuario=" + usuario + ", enderecos=" + enderecos
                + ", dataNasc=" + dataNasc + "]";
    }

}
