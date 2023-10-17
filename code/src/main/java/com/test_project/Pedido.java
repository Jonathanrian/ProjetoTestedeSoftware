package com.test_project;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map.Entry;

public class Pedido {
    private int numPedido;
    private HashMap<Produto, Integer> produtos;
    private String status;
    private Usuario cliente;
    private double valorProdutos;
    private String formaPagamento;
    private Endereco endereco;
    private LocalDate dataCriacao;
    private LocalDate dataEnvio;
    private String tipoEnvio;
    private double custoEnvio;
    private double valorFrete;
    private double valorTotal;

    public Pedido(HashMap<Produto, Integer> produtos, Usuario cliente, double valorProdutos) {
        this.produtos = produtos;
        this.cliente = cliente;
        this.valorProdutos = valorProdutos;
    }

    public boolean exibirDetalhes(){
        try {
            //Colocar uma forma legal de imprimir os detalhes talvez?
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean finalizarCompra() throws Exception{
        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO " + 
                "pedido (cliente, status, data_criacao, valor_total, forma_pagamento, data_envio, tipo_envio, valor_frete, endereco) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
                Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, this.cliente.getId());
            setStatus("em andamento");
            pstmt.setString(2, status);
            this.dataCriacao = LocalDate.now();
            pstmt.setDate(3, Date.valueOf(dataCriacao));
            setValorProdutos();
            setCustoEnvio();
            setValorTotal();
            pstmt.setDouble(4, this.valorTotal);
            pstmt.setString(5, this.formaPagamento);
            this.dataEnvio = LocalDate.now().plusDays(3);
            pstmt.setDate(6, Date.valueOf(dataEnvio));
            if (tipoEnvio == "envio padrão") {
                pstmt.setInt(7, 1);
            } else if(tipoEnvio == "envio expresso"){
                pstmt.setInt(7, 2);
            }
            pstmt.setDouble(8, this.valorFrete);
            pstmt.setInt(9, this.endereco.getId());
            pstmt.executeUpdate();

            int idPedido = 0;

            ResultSet keyPedido = pstmt.getGeneratedKeys();
            if (keyPedido.next()) {
                
                idPedido = keyPedido.getInt(1);
                setNumPedido(idPedido);
            } else{
                return false;
            }

            for (Entry<Produto, Integer> map : produtos.entrySet()) {
                Produto produto = map.getKey();
                Integer quantidade = map.getValue();

                pstmt = connection.prepareStatement(
                    "INSERT INTO "+
                    "produtos_pedido(id_pedido, id_produto, quantidade) " +
                    "VALUES (?, ?, ?)"
                );
    
                pstmt.setInt(1, idPedido);
                pstmt.setInt(2, produto.getId());
                pstmt.setInt(3, quantidade);
                pstmt.executeUpdate();
            }

            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean cancelarPedido(){
        try {

            if (this.status.equals("cancelado") || this.status.equals("concluído")) {
                return false;
            }

            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                "UPDATE pedido " + 
                "SET status = 'cancelado' " +
                "WHERE id_pedido = ?");

            pstmt.setInt(1, getNumPedido());
            pstmt.executeUpdate();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public float calcularFrete(String cep){
        try {
            float frete = 0;

            return frete;
        } catch (Exception e) {
            return -1;
        }
    }

    public int getNumPedido() {
        return numPedido;
    }
    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public HashMap<Produto, Integer> getProdutos() {
        return produtos;
    }

    public String getStatus() {
        return status;
    }
    public boolean setStatus(String status) {

        if (status == null) {
            return false; // Não deve ser nulo
        }

        status = status.toLowerCase();

        String[] arrayStatus = {
            "em andamento", "enviado", "atrasado", "cancelado", "concluído"
        };

        for (String statusString : arrayStatus) {
            if (statusString.equals(status)) {
                this.status = status;
                return true;
            }
        }

        return false;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public double getValorTotal() {
        return valorTotal;
    }
    public boolean setValorTotal() {

        try {
            this.valorTotal = this.valorProdutos + this.custoEnvio;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }
    public boolean setFormaPagamento(String formaPagamento) {

        if (formaPagamento == null) {
            return false; // Não deve ser nulo
        }

        formaPagamento = formaPagamento.toLowerCase();

        String[] arrayFormasPagamento = {
            "cartão de crédito", "cartão de débito", "boleto bancãrio", "pix"
        };

        for (String formaPagamentoString : arrayFormasPagamento) {
            if (formaPagamentoString.equals(formaPagamento)) {
                this.formaPagamento = formaPagamento;
                return true;
            }
        }

        return false;
    }

    public Endereco getEndereco() {
        return endereco;
    }
    public boolean setEndereco(Endereco endereco) {

        if (endereco == null) {
            return false;
        }

        this.endereco = endereco;
        return true;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }
    public boolean setDataEnvio(LocalDate dataEnvio) {
        if (dataEnvio == null) {
            this.dataEnvio = LocalDate.now();
            return true;
        } else if (dataEnvio.isBefore(this.dataCriacao)) {
            return false;
        }

        this.dataEnvio = dataEnvio;
        return true;
    }

    public String getTipoEnvio() {
        return tipoEnvio;
    }
    public boolean setTipoEnvio(String tipoEnvio) {

        if (tipoEnvio == null) {
            return false; // Não deve ser nulo
        }

        tipoEnvio = tipoEnvio.toLowerCase();

        String[] arrayTipoEnvio = {
            "envio padrão", "envio expresso"
        };

        for (String tipoEnvioString : arrayTipoEnvio) {
            if (tipoEnvioString.equals(tipoEnvio)) {
                this.tipoEnvio = tipoEnvio;
                return true;
            }
        }

        return false;
    }

    public double getCustoEnvio() {
        return custoEnvio;
    }

    public boolean setCustoEnvio() {
        
        double custoTipo;

        if (this.tipoEnvio == null) {
            return false;
        }

        if (this.tipoEnvio.equals("envio padrão")) {
            custoTipo = 10;
        } else if (this.tipoEnvio.equals("envio expresso")) {
            custoTipo = 20;
        } else{
            return false;
        }

        setValorFrete();

        this.custoEnvio = custoTipo + getValorFrete();
        return true;
    }

    public double getValorFrete() {
        return valorFrete;
    }
    public void setValorFrete() {

        double valorFrete = calcularFrete(this.endereco.getCep());

        this.valorFrete = valorFrete;
    }

    public double getValorProdutos() {
        return valorProdutos;
    }
    public boolean setValorProdutos() {
        try {
            float total = 0;

            for (Produto produto : this.produtos.keySet()) {
                total += produto.getPreco() * this.produtos.get(produto);
            }

            this.valorProdutos = total;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Pedido [numPedido=" + numPedido + ", produtos=" + produtos + ", status=" + status + ", cliente="
                + cliente + ", valorTotal=" + valorTotal + ", formaPagamento=" + formaPagamento + ", endereco="
                + endereco + ", dataCriacao=" + dataCriacao + ", dataEnvio=" + dataEnvio + ", tipoEnvio=" + tipoEnvio
                + ", custoEnvio=" + custoEnvio + ", valorFrete=" + valorFrete + "]";
    }
}

