import java.time.LocalDate;

public class Pedido {
    private int numPedido;
    private Carrinho produtos;
    private String status;
    private Usuario usuario;
    private double valorTotal;
    private String formaPagamento;
    private Endereco endereco;
    private LocalDate dataCriacao;
    private LocalDate dataEnvio;
    private String tipoEnvio;
    private double custoEnvio;
    private double valorFrete;

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

    public boolean editarFormaPagamento(){
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cancelarPedido(){
        try {
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
    public Carrinho getProdutos() {
        return produtos;
    }
    public void setProdutos(Carrinho produtos) {
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
}
