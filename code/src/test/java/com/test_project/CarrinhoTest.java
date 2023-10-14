package com.test_project;

import org.junit.jupiter.api.Test;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

/**
 * Unit test for simple App.
 */
class CarrinhoTest {

    private Carrinho carrinho;
    private Produto produto1;
    private Produto produto2;
    private Usuario cliente;

    @BeforeEach
    void setUp(){
        this.cliente = Usuario.login("RenanCosta", "renan123");
        this.carrinho = new Carrinho(cliente);
        this.produto1 = new Produto(2, "Mouse Gamer HyperX", 160, "periféricos", 50, "Oferece aos jogadores o melhor em estilo e conteúdo, oferecendo extrema precisão graças a seu sensor Pixart 3389 e efeitos de iluminação RGB espetaculares em 360°", "HyperX ", 0);
        this.produto2 = new Produto(1, "celular A12", 1300, "smartphone", 50, "Um celular", "SAMSUNG", 0);
        carrinho.esvaziarCarrinho();
    }

    /**
     * Verifica se o método é capaz de adicionar um item ao carrinho e se a quantidade desse item é atualizada corretamente para 1. 
     */
    @Test
    void adicionarItem(){
        assertTrue(carrinho.adicionarItem(produto1));
        assertTrue(carrinho.adicionarItem(produto2));
        assertEquals(1, carrinho.quantidadeProduto(produto1));
        assertEquals(1, carrinho.quantidadeProduto(produto2));
    }

    /**
     * Verifica se o método é capaz de adicionar um item que já existe no carrinho e se a quantidade desse item é incrementada corretamente.
     */
    @Test
    void adicionarItemDuplicado(){
        carrinho.adicionarItem(produto1);
        assertTrue(carrinho.adicionarItem(produto1));
        assertEquals(2, carrinho.quantidadeProduto(produto1));
    }

    /**
     * Verifica se o método é capaz de remover um item do carrinho.
     */
    @Test
    void removerItem(){
        carrinho.adicionarItem(produto1);
        assertTrue(carrinho.removerItem(produto1));
        assertFalse(carrinho.removerItem(produto2));
        assertEquals(-1, carrinho.quantidadeProduto(produto1));
    }

    /**
     * Verifica se o método é capaz de remover uma quantidade específica de um item do carrinho.
     * Caso a quantidade desse item chegue a 0 ou inferior o produto é removido do carrinho.
     */
    @Test
    void removerItemPorDecremento(){
        assertEquals(true, carrinho.adicionarItem(produto1));
        assertEquals(true, carrinho.adicionarItem(produto1));
        assertEquals(true, carrinho.adicionarItem(produto1));
        assertTrue(carrinho.removerItem(produto1, -2));
        assertEquals(1, carrinho.quantidadeProduto(produto1));
        assertTrue(carrinho.removerItem(produto1, -3));
        assertEquals(-1, carrinho.quantidadeProduto(produto1));
    }

    /**
     * Verifica se o método calcula o total do preço do carrinho corretamente.
     */
    @Test
    void calcularTotal(){
        assertEquals(true, carrinho.adicionarItem(produto1));
        assertEquals(true, carrinho.adicionarItem(produto1));
        assertEquals(true, carrinho.adicionarItem(produto2));
        double preco = produto1.getPreco() * 2 + produto2.getPreco();

        assertEquals(preco, carrinho.calcularTotal());
    }

    /**
     * Verifica se o método está retornando a quantidade correta de cada produto no carrinho.
     */
    @Test
    void quantidadeProduto(){
        assertEquals(true, carrinho.adicionarItem(produto1));
        assertEquals(true, carrinho.adicionarItem(produto1));
        assertEquals(true, carrinho.adicionarItem(produto2));
        
        assertEquals(2, carrinho.quantidadeProduto(produto1));
        assertEquals(1, carrinho.quantidadeProduto(produto2));
    }

    /**
     * Verifica se o método está retornando todos os produtos que estão no carrinho corretamente.
     */
    @Test
    void produtosCarrinho(){
        assertEquals(true, carrinho.adicionarItem(produto1));
        assertEquals(true, carrinho.adicionarItem(produto2));
        
        ArrayList<Produto> produtos = carrinho.produtosCarrinho();

        assertTrue(produtos.contains(produto1) && produtos.contains(produto2));
    }

    /**
     * Verifica se o método está esvaziando o carrinho corretamente.
     */
    @Test
    void esvaziarCarrinho(){
        assertEquals(true, carrinho.adicionarItem(produto1));
        assertEquals(true, carrinho.adicionarItem(produto2));

        carrinho.esvaziarCarrinho();

        assertTrue(carrinho.getProdutos().isEmpty());
        
        assertFalse(carrinho.esvaziarCarrinho());
    }

    /**
     * Verifica se o método está realizando o pedido corretamente.
     */
    @Test
    void realizarPedido(){
        assertNull(carrinho.realizarPedido());

        assertEquals(true, carrinho.adicionarItem(produto1));
        assertEquals(true, carrinho.adicionarItem(produto2));

        assertNotNull(carrinho.realizarPedido());
    }

    /**
     * Verifica se o método está atualizando o banco de dados corretamente, de forma quando esse método for chamado o banco de dados e o carrinho contenham os mesmo itens.
     */
    @Test
    void atualizacaoBanco(){

        try {
                
            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmtSelect = connection.prepareStatement(
                "SELECT * FROM " +
                "carrinho WHERE cliente = ?");

            pstmtSelect.setInt(1, this.cliente.getId());

            Map<Produto, Integer> dadosBanco = new HashMap<>();

            carrinho.adicionarItem(produto1);
            carrinho.adicionarItem(produto1);
            carrinho.adicionarItem(produto1);

            carrinho.atualizarBanco();
            
            ResultSet rs = pstmtSelect.executeQuery();

            Produto produto;
            int id;
            String nome;
            double preco;
            int quantidade;
            String categoria;
            int estoque;
            String descricao;
            String fabricante;
            int desconto;
            
            while (rs.next()) {
                id = rs.getInt(1);
                nome = rs.getString(2);
                preco = rs.getDouble(3);
                quantidade = rs.getInt(4);
                categoria = rs.getString(5);
                estoque = rs.getInt(6);
                descricao = rs.getString(7);
                fabricante = rs.getString(8);
                desconto = rs.getInt(9);

                produto = new Produto(id, nome, preco, categoria, estoque, descricao, fabricante, desconto);

                dadosBanco.put(produto, quantidade);
            }

            assertEquals(dadosBanco, carrinho.getProdutos());
            dadosBanco.clear();

            carrinho.removerItem(produto1, -1);
            carrinho.adicionarItem(produto2);
            carrinho.adicionarItem(produto2);

            while (rs.next()) {
                id = rs.getInt(1);
                nome = rs.getString(2);
                preco = rs.getDouble(3);
                quantidade = rs.getInt(4);
                categoria = rs.getString(5);
                estoque = rs.getInt(6);
                descricao = rs.getString(7);
                fabricante = rs.getString(8);
                desconto = rs.getInt(9);

                produto = new Produto(id, nome, preco, categoria, estoque, descricao, fabricante, desconto);

                dadosBanco.put(produto, quantidade);
            }

            assertEquals(dadosBanco, carrinho.getProdutos());
            dadosBanco.clear();

            carrinho.removerItem(produto1);
            carrinho.adicionarItem(produto2);

            while (rs.next()) {
                id = rs.getInt(1);
                nome = rs.getString(2);
                preco = rs.getDouble(3);
                quantidade = rs.getInt(4);
                categoria = rs.getString(5);
                estoque = rs.getInt(6);
                descricao = rs.getString(7);
                fabricante = rs.getString(8);
                desconto = rs.getInt(9);

                produto = new Produto(id, nome, preco, categoria, estoque, descricao, fabricante, desconto);

                dadosBanco.put(produto, quantidade);
            }

            assertEquals(dadosBanco, carrinho.getProdutos());
            dadosBanco.clear();

            carrinho.esvaziarCarrinho();

            while (rs.next()) {
                id = rs.getInt(1);
                nome = rs.getString(2);
                preco = rs.getDouble(3);
                quantidade = rs.getInt(4);
                categoria = rs.getString(5);
                estoque = rs.getInt(6);
                descricao = rs.getString(7);
                fabricante = rs.getString(8);
                desconto = rs.getInt(9);

                produto = new Produto(id, nome, preco, categoria, estoque, descricao, fabricante, desconto);

                dadosBanco.put(produto, quantidade);
            }

            assertEquals(dadosBanco, carrinho.getProdutos());
            dadosBanco.clear();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}