import java.util.ArrayList;
import java.util.HashMap;

public class Carrinho {
    private HashMap<Produto, Integer> produtos = new HashMap<>();

    public boolean adicionarItem(Produto produto){
        try {
            if (this.produtos.containsKey(produto)) {
            this.produtos.put(produto, this.produtos.get(produto) + 1);
            } else{
                this.produtos.put(produto, 1);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean removerItem(Produto produto){

        try {
            if (this.produtos.containsKey(produto)) {
                this.produtos.remove(produto);
                return true;
            } else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean removerItem(Produto produto, int decremento){

    try {
        if (this.produtos.containsKey(produto)) {
            this.produtos.put(produto, this.produtos.get(produto) + decremento);
            if (this.produtos.get(produto) == 0) {
                this.produtos.remove(produto);
            }
            return true;
        } else{
            return false;
        }
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
            if (this.produtos.containsKey(produto)) {
                return this.produtos.get(produto);
            } else{
                return -1;
            }
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

    public boolean realizarPedido(){
        return true;
    }

    public HashMap<Produto, Integer> getProdutos() {
        return produtos;
    }

    public void setProdutos(HashMap<Produto, Integer> produtos) {
        this.produtos = produtos;
    }


    public static void main(String[] args) {
        Carrinho carrinho = new Carrinho();
        Produto produto = new Produto();
        Produto produto2 = new Produto();

        carrinho.adicionarItem(produto);
        carrinho.adicionarItem(produto);
        carrinho.adicionarItem(produto2);
        carrinho.adicionarItem(produto2);
        carrinho.adicionarItem(produto);
        carrinho.adicionarItem(produto);
        carrinho.adicionarItem(produto2);
        carrinho.adicionarItem(produto2);

        carrinho.removerItem(produto2, -1);
        carrinho.removerItem(produto2, -1);
        
        System.out.println(carrinho.getProdutos());

        System.out.println(carrinho.calcularTotal());

        System.out.println(carrinho.quantidadeProduto(produto2));

        System.out.println(carrinho.produtosCarrinho());

        carrinho.esvaziarCarrinho();

        System.out.println(carrinho.getProdutos());
        
    }

}

