import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;

public class Pedido {
    private int numPedido;
    private HashMap<Produto, Integer> produtos;
    private String status;
    private Usuario usuario;
    private double valorProdutos;
    private String formaPagamento;
    private Endereco endereco;
    private LocalDate dataCriacao;
    private LocalDate dataEnvio;
    private String tipoEnvio;
    private double custoEnvio;
    private double valorFrete;
    private double valorTotal;

    public Pedido(HashMap<Produto, Integer> produtos, Usuario usuario, double valorProdutos, LocalDate dataCriacao) {
        this.produtos = produtos;
        this.usuario = usuario;
        this.valorProdutos = valorProdutos;
        this.dataCriacao = dataCriacao;
    }

    public boolean exibirDetalhes(){
        try {
            //Colocar uma forma legal de imprimir os detalhes talvez?
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean finalizarCompra(){
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean editarFormaPagamento(String novaFormaPagamento){
        try {

            setFormaPagamento(novaFormaPagamento);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cancelarPedido(){
        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM " + 
                "pedido WHERE id_pedido = ?");

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
    public void setProdutos(HashMap<Produto, Integer> produtos) {
        this.produtos = produtos;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    public String getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public LocalDate getDataEnvio() {
        return dataEnvio;
    }
    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
    public String getTipoEnvio() {
        return tipoEnvio;
    }
    public void setTipoEnvio(String tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }
    public double getCustoEnvio() {
        return custoEnvio;
    }
    public void setCustoEnvio(double custoEnvio) {
        this.custoEnvio = custoEnvio;
    }
    public double getValorFrete() {
        return valorFrete;
    }
    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public double getValorProdutos() {
        return valorProdutos;
    }

    public void setValorProdutos(double valorProdutos) {
        this.valorProdutos = valorProdutos;
    }

    @Override
    public String toString() {
        return "Pedido [numPedido=" + numPedido + ", produtos=" + produtos + ", status=" + status + ", usuario="
                + usuario + ", valorTotal=" + valorTotal + ", formaPagamento=" + formaPagamento + ", endereco="
                + endereco + ", dataCriacao=" + dataCriacao + ", dataEnvio=" + dataEnvio + ", tipoEnvio=" + tipoEnvio
                + ", custoEnvio=" + custoEnvio + ", valorFrete=" + valorFrete + "]";
    }
}
