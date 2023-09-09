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
}
