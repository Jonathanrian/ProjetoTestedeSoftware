package com.test_project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import br.com.caelum.stella.validation.CPFValidator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Usuario {
    private int id;
    private String nome_completo;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;
    private String usuario;
    private ArrayList<Endereco> enderecos = new ArrayList<>();
    private LocalDate dataNasc;
    
    public Usuario(String nome_completo, String cpf, String email, String telefone, String senha,
            String usuario, LocalDate dataNasc) {
        this.nome_completo = nome_completo;
        this.cpf = cpf;
        setCpf(this.cpf);
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.usuario = usuario;
        this.dataNasc = dataNasc;
    }

    public Usuario(int id, String nome_completo, String cpf, String email, String telefone, String senha,
            String usuario, ArrayList<Endereco> endereco, LocalDate dataNasc) {
        this.id = id;
        this.nome_completo = nome_completo;
        this.cpf = cpf;
        setCpf(this.cpf);
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.usuario = usuario;
        this.enderecos = endereco;
        this.dataNasc = dataNasc;
    }

    public boolean cadastrar(){
        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            if (this.validarDados()) {

                PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM " + 
                    "cliente WHERE cpf = ? OR usuario = ? OR email = ?");

                pstmt.setString(1, getCpf());
                pstmt.setString(2, getUsuario());
                pstmt.setString(3, getEmail());
                ResultSet rs = pstmt.executeQuery();

                if (!rs.next()) {
                    pstmt = connection.prepareStatement(
                        "INSERT INTO " + 
                        "cliente(nome_completo, cpf, email, telefone, usuario, senha, datanasc)" + 
                        "VALUES(?, ?, ?, ?, ?, ?, ?)");

                        pstmt.setString(1, getNome_completo());
                        pstmt.setString(2, getCpf());
                        pstmt.setString(3, getEmail());
                        pstmt.setString(4, getTelefone());
                        pstmt.setString(5, getUsuario());
                        pstmt.setString(6, getSenha());
                        pstmt.setDate(7, Date.valueOf(dataNasc));
                        pstmt.executeUpdate();

                    return true;
                }

                return false;
                
            }
            
            return false;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static Usuario login(String usuario, String senha){
        try {

            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM " + 
                "cliente WHERE usuario = ? AND senha = ?");

            pstmt.setString(1, usuario);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                Usuario cliente;
                int id = rs.getInt(1);
                String nome_completo = rs.getString(2);
                String cpf = rs.getString(3);
                String email = rs.getString(4);
                String telefone = rs.getString(5);
                ArrayList<Endereco> enderecos = new ArrayList<>();
                java.sql.Date dataSql = rs.getDate(8);
                LocalDate dataNasc = dataSql.toLocalDate();

                PreparedStatement pstmt2 = connection.prepareStatement(
                    "SELECT * FROM endereco " +
                    "WHERE cliente = ?");
                
                pstmt2.setInt(1, id);

                ResultSet rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    Endereco endereco;

                    int id_endereco = rs2.getInt(1);
                    String estado = rs2.getString(3);
                    String cidade = rs2.getString(4);
                    String bairro = rs2.getString(5);
                    String rua = rs2.getString(6);
                    int numero = rs2.getInt(7);
                    String complemento = rs2.getString(8);
                    String cep = rs2.getString(9);

                    endereco = new Endereco(id_endereco, estado, cidade, bairro, rua, complemento, cep, numero);
                    enderecos.add(endereco);
                }

                cliente = new Usuario(id, nome_completo, cpf, email, telefone, senha, usuario, enderecos, dataNasc);
                
                return cliente;
            }

            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean adicionarEndereco(Endereco endereco) throws Exception{
        try {

            if (endereco == null) {
                return false;
            }

            this.enderecos.add(endereco);

            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO " + 
                "endereco(cliente, estado, cidade, bairro, rua, numero, complemento, cep)" + 
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, this.id);
            pstmt.setString(2, endereco.getEstado());
            pstmt.setString(3, endereco.getCidade());
            pstmt.setString(4, endereco.getBairro());
            pstmt.setString(5, endereco.getRua());
            pstmt.setInt(6, endereco.getNumero());
            pstmt.setString(7, endereco.getComplemento());
            pstmt.setString(8, endereco.getCep());
            pstmt.executeUpdate();

            return true;
            
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean validarDados(){
        try {

            if (setNome_completo(this.nome_completo) &&
                setCpf(this.cpf) &&
                setEmail(this.email) &&
                setTelefone(this.telefone, "BR") &&
                setSenha(this.senha) &&
                setUsuario(this.usuario) &&
                setDataNasc(dataNasc)){
                
                return true;
            } else{
                return false;
            }

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
            Connection connection = PostgreSQLConnection.getInstance().getConnection();

            PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM " + 
                "cliente WHERE cpf = ?");

            pstmt.setString(1, getCpf());
            pstmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e);
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
    public boolean setId(int id) {
        this.id = id;
        return true;
    }
    public String getNome_completo() {
        return nome_completo;
    }
    public boolean setNome_completo(String nome_completo) {
        if (nome_completo == null) {
            return false; // Não deve ser nulo
        }
        
        // Comprimento mínimo e máximo
        if (nome_completo.length() < 4 || nome_completo.length() > 100) {
            return false;
        }
        
        // Caracteres válidos (permite letras, espaços, hífens, apóstrofos e acentos)
        if (!nome_completo.matches("^[\\p{L}\\s'-]+$")) {
            return false;
        }
        
        // Evita números
        if (nome_completo.matches(".*\\d.*")) {
            return false;
        }
        
        this.nome_completo = nome_completo;
        return true;
    }
    public String getCpf() {
        return cpf;
    }
    public boolean setCpf(String cpf) {
        CPFValidator cpfValidator = new CPFValidator(); 
        try {
            if (cpf == null) {
                return false; // Não deve ser nulo
            }

            cpf = cpf.replaceAll("[^0-9]", "");

            // O cpfValidator faz a validação do CPF, caso seja inválido irá gerar uma exceção.
            cpfValidator.assertValid(cpf); 
            
            this.cpf = cpf;
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    public String getEmail() {
        return email;
    }
    public boolean setEmail(String email) {
        try {
            if (email == null) {
                return false; // Não deve ser nulo
            }

            // O InternetAddress faz a validação do email, caso seja inválido irá gerar uma exceção.
            InternetAddress emailAddr = new InternetAddress(email); 
            emailAddr.validate();
            this.email = email;
            return true;

        } catch (AddressException e) {
            return false;
        }
    }
    public String getTelefone() {
        return telefone;
    }
    public boolean setTelefone(String telefone, String codigoPais) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            // O telefone pode ser nulo então ele armazena na variável e retorna verdadeiro
            if (telefone == null) {
                this.telefone = telefone;
                return true;
            }
            // O InternetAddress faz a validação do telefone, caso seja inválido irá gerar uma exceção.
            PhoneNumber numero = phoneNumberUtil.parse(telefone, codigoPais);
            this.telefone = telefone;
            return phoneNumberUtil.isValidNumber(numero);

        } catch (NumberParseException e) {
            return false;
        }
    }
    public String getSenha() {
        return senha;
    }
    public boolean setSenha(String senha) {

        if (senha == null) {
            return false; // Não deve ser nulo
        }

        // Comprimento mínimo e máximo
        if (senha.length() < 8 || senha.length() > 40) {
            return false;
        }

        this.senha = senha;
        return true;
    }
    public String getUsuario() {
        return usuario;
    }
    public boolean setUsuario(String usuario) {

        if (usuario == null) {
            return false; // Não deve ser nulo
        }
        
        // Comprimento mínimo e máximo
        if (usuario.length() < 3 || usuario.length() > 50) {
            return false;
        }
        
        // Caracteres válidos (permite letras, espaços, hífens, apóstrofos, acentos e números.)
        if (!usuario.matches("^[\\p{L}0-9\\s'-]+$")) {
            return false;
        }

        this.usuario = usuario;
        return true;
    }
    public ArrayList<Endereco> getEnderecos() {
        return enderecos;
    }
    public boolean setEnderecos(ArrayList<Endereco> endereco) {
        this.enderecos = endereco;
        return true;
    }
    public LocalDate getDataNasc() {
        return dataNasc;
    }
    public boolean setDataNasc(LocalDate dataNasc) {

        if (dataNasc == null) {
            return false; // Não deve ser nulo
        }

        //A data não pode estar no futuro
        LocalDate dataAtual = LocalDate.now();
        if (dataNasc.isAfter(dataAtual)) {
            return false;
        }

        //A data não pode estar muito no passado
        LocalDate dataMinima = dataAtual.minusYears(110);
        if (dataNasc.isBefore(dataMinima)) {
            return false;
        }

        //A data não pode estar depois de 18 anos antes, ou seja o usuário não pode ter menos de 18 anos.
        LocalDate idadeMinima = dataAtual.minusYears(18);
        if (dataNasc.isAfter(idadeMinima)) {
            return false;
        }

        this.dataNasc = dataNasc;
        return true;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome_completo=" + nome_completo + ", cpf=" + cpf + ", email=" + email
                + ", telefone=" + telefone + ", senha=" + senha + ", usuario=" + usuario + ", enderecos=" + enderecos
                + ", dataNasc=" + dataNasc + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        Usuario other = (Usuario) obj;
        
        return id == other.id &&
            Objects.equals(nome_completo, other.nome_completo) &&
            Objects.equals(cpf, other.cpf) &&
            Objects.equals(email, other.email) &&
            Objects.equals(senha, other.senha) &&
            Objects.equals(usuario, other.usuario) &&
            Objects.equals(dataNasc, other.dataNasc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome_completo, cpf, email, senha, usuario, dataNasc);
    }

}
