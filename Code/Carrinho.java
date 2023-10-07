import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Carrinho {
    private Usuario cliente;
    private HashMap<Produto, Integer> produtos = new HashMap<>();

    public Carrinho(Usuario cliente) {

        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT p.id_produto, p.nome, p.preco, c.quantidade, p.categoria, p.estoque, p.descricao, p.fabricante, p.desconto " + 
                "FROM (SELECT * FROM carrinho " +
                "WHERE cliente = ?) as c " +
                "INNER JOIN " +
                "(SELECT * FROM produto) as p " +
                "ON c.produto = p.id_produto");

            pstmt.setInt(1, cliente.getId());

            ResultSet rs = pstmt.executeQuery();

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

                this.produtos.put(produto, quantidade);

            }
            
            this.cliente = cliente;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean adicionarItem(Produto produto){
        try {

            if (this.produtos.isEmpty()) {
                this.produtos.put(produto, 1);
                return true;
            }

            for (Produto key : this.produtos.keySet()) {
                if (key.getId() == produto.getId()) {
                    this.produtos.put(key, this.produtos.get(key) + 1);
                    return true;
                }
            }

            this.produtos.put(produto, 1);
            
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean removerItem(Produto produto){

        try {

            for (Produto key : this.produtos.keySet()) {

                if (key.getId() == produto.getId()) {
                    this.produtos.remove(key);
                    return true;
                }
                
            }

            return false;
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean removerItem(Produto produto, int decremento){

    try {

            for (Produto key : this.produtos.keySet()) {

                if (key.getId() == produto.getId()) {
                    this.produtos.put(key, this.produtos.get(key) + decremento);
                    if (this.produtos.get(key) == 0) {
                        this.produtos.remove(key);
                    }
                    return true;
                }
                
            }

            return false;
        } catch (Exception e) {
            return false;
        }
        
    }

    public float calcularTotal(){

        try {
            float total = 0;

            for (Produto produto : this.produtos.keySet()) {
                total += produto.getPreco() * this.produtos.get(produto);
            }
            return total;
        } catch (Exception e) {
            return -1;
        }
    }

    public int quantidadeProduto(Produto produto){
        try {

            for (Produto key : this.produtos.keySet()) {
                if (key.getId() == produto.getId()) {
                    return this.produtos.get(key);
                }
            }

            return -1;

        } catch (Exception e) {
            return -1;
        }
    }

    public ArrayList<Produto> produtosCarrinho(){
        try {
            ArrayList<Produto> produtos = new ArrayList<>();

            for (Produto produto : this.produtos.keySet()) {
                produtos.add(produto);
            }

            return produtos;
            
        } catch (Exception e) {
            return null;
        }
    }

    public boolean esvaziarCarrinho(){
        try {
            if (!this.produtos.isEmpty()) {
                this.produtos.clear();
                return true;
            } else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Pedido realizarPedido(){
        try {

            Pedido pedido;

            pedido = new Pedido(produtos, cliente, this.calcularTotal(), LocalDate.now());
                
            return pedido;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean atualizarBanco(){
        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmtSelect = connection.prepareStatement(
                "SELECT * FROM " +
                "carrinho WHERE cliente = ?");

            pstmtSelect.setInt(1, this.cliente.getId());

            ResultSet rs = pstmtSelect.executeQuery();

            PreparedStatement pstmtUpdate = connection.prepareStatement(
                "UPDATE carrinho " +
                "SET quantidade = ? " +
                "WHERE produto = ?");
            
            PreparedStatement pstmtDelete = connection.prepareStatement(
                "DELETE FROM " +
                "carrinho WHERE produto = ?");

            PreparedStatement pstmtInsert = connection.prepareStatement(
                "INSERT INTO " +
                "carrinho(cliente, produto, quantidade) " +
                "VALUES(?, ?, ?)");

                ArrayList<Integer> idProdutos = new ArrayList<>();

            while (rs.next()) {

                int encontrado = 0;
                idProdutos.add(rs.getInt(2));

                for (Map.Entry<Produto, Integer> entry : this.produtos.entrySet()) {

                    Produto produto = entry.getKey();
                    int quantidade = entry.getValue();

                    if (produto.getId() == rs.getInt(2)) {
                        encontrado++;
                        if (quantidade != rs.getInt(3)) {

                            pstmtUpdate.setInt(1, quantidade);
                            pstmtUpdate.setInt(2, rs.getInt(2));
                            pstmtUpdate.executeUpdate();
                        }
                        break;
                    }

                }

                if (encontrado == 0) {
                    
                    pstmtDelete.setInt(1, rs.getInt(2));
                    pstmtDelete.executeUpdate();
                }
            }

            for (Map.Entry<Produto, Integer> entry : this.produtos.entrySet()) {

                Produto produto = entry.getKey();
                int quantidade = entry.getValue();

                    if (!idProdutos.contains(produto.getId())) {
                        pstmtInsert.setInt(1, this.cliente.getId());
                        pstmtInsert.setInt(2, produto.getId());
                        pstmtInsert.setInt(3, quantidade);
                        pstmtInsert.executeUpdate();
                    }
                    
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public HashMap<Produto, Integer> getProdutos() {
        return produtos;
    }

    public void setProdutos(HashMap<Produto, Integer> produtos) {
        this.produtos = produtos;
    }


    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Carrinho [cliente=" + cliente + ", produtos=" + produtos + "]";
    }

    public static void main(String[] args) {

        Usuario cliente = Usuario.login("RenanCosta", "renan123");

        Carrinho carrinho = new Carrinho(cliente);

        Produto produto = new Produto(2, "Mouse Gamer HyperX", 160, "periféricos", 50, "Oferece aos jogadores o melhor em estilo e conteúdo, oferecendo extrema precisão graças a seu sensor Pixart 3389 e efeitos de iluminação RGB espetaculares em 360°", "HyperX ", 0);
        Produto produto2 = new Produto(1, "celular A12", 1300, "smartphone", 50, "Um celular", "SAMSUNG", 0);

        carrinho.adicionarItem(produto);

        System.out.println(carrinho.quantidadeProduto(produto));

        carrinho.esvaziarCarrinho();

        carrinho.atualizarBanco();

        System.out.println(carrinho);
        // carrinho.removerItem(produto2, -1);
        
        // System.out.println(carrinho.getProdutos());

        // System.out.println(carrinho.calcularTotal());

        // System.out.println(carrinho.quantidadeProduto(produto2));

        // System.out.println(carrinho.produtosCarrinho());

        // carrinho.esvaziarCarrinho();

        // System.out.println(carrinho.getProdutos());
        
    }

}

