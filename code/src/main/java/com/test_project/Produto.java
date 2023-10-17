package com.test_project;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private String categoria;
    private int estoque;
    private String descricao;
    private String fabricante;
    private int desconto;
    //private Image imagem;

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

