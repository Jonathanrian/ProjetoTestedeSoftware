import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario {
    private int id;
    private String nome_completo;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;
    private String usuario;
    private Endereco endereco;
    private LocalDate dataNasc;
    
    public Usuario(int id, String nome_completo, String cpf, String email, String telefone, String senha,
            String usuario, Endereco endereco, LocalDate dataNasc) {
        this.id = id;
        this.nome_completo = nome_completo;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.usuario = usuario;
        this.endereco = endereco;
        this.dataNasc = dataNasc;
    }

    public boolean Login(){
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validarDados(){
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean editarInformacoes(){
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean recuperarSenha(){
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean excluirUsuario(){
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Pedido> listarPedidos(){
        try {
            ArrayList<Pedido> pedidos = new ArrayList<>();

            return pedidos;
        } catch (Exception e) {
            return null;
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome_completo() {
        return nome_completo;
    }
    public void setNome_completo(String nome_completo) {
        this.nome_completo = nome_completo;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public LocalDate getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

}
