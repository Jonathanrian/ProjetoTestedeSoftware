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
    
}
