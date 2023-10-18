package com.test_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private String categoria;
    private int estoque;
    private String descricao;
    private String fabricante;
    private int desconto;

    public Produto(int id, String nome, double preco, String categoria, int estoque, String descricao,
            String fabricante, int desconto) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.estoque = estoque;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.desconto = desconto;
        setPreco(preco);
    }

    public boolean exibirDetalhes(){
        try {
            //Colocar uma forma legal de imprimir os detalhes talvez?
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Produto buscaProduto(int id_produto) throws Exception{
        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM " +
                "produto WHERE id_produto = ?");

            pstmt.setInt(1, id_produto);

            ResultSet rs = pstmt.executeQuery();

            Produto produto = null;

            if (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                double preco = rs.getDouble(3);
                String categoria = rs.getString(4);
                int estoque = rs.getInt(5);
                String descricao = rs.getString(6);
                String fabricante = rs.getString(7);
                int desconto = rs.getInt(8);

                produto = new Produto(id, nome, preco, categoria, estoque, descricao, fabricante, desconto);

            }

            return produto;
            
        } catch (Exception e) {
            throw e;
        }
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public double getPreco() {
        return preco;
    }
    public String getCategoria() {
        return categoria;
    }
    public int getEstoque() {
        return estoque;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getFabricante() {
        return fabricante;
    }
    public int getDesconto() {
        return desconto;
    }

    public void setPreco(double preco) {
        this.preco = preco * (100 - desconto)/100;
    }

    @Override
    public String toString() {
        return "Produto [id=" + id + ", nome=" + nome + ", preco=" + preco + ", categoria=" + categoria + ", estoque="
                + estoque + ", descricao=" + descricao + ", fabricante=" + fabricante + ", desconto=" + desconto + "]";
    }
    
}

