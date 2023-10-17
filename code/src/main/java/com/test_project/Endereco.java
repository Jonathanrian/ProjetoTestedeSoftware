package com.test_project;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Endereco {
    private int id;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String complemento;
    private String cep;
    private int numero;
    
    public Endereco(int id, String estado, String cidade, String bairro, String rua, String complemento, String cep,
            int numero) {
        setId(id);
        setEstado(estado);
        setCidade(cidade);
        setBairro(bairro);
        setRua(rua);
        setComplemento(complemento);
        setCep(cep);
        setNumero(numero);
    }
    
    public Endereco(String estado, String cidade, String bairro, String rua, String complemento, String cep,
            int numero) {
        setEstado(estado);
        setCidade(cidade);
        setBairro(bairro);
        setRua(rua);
        setComplemento(complemento);
        setCep(cep);
        setNumero(numero);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }
    public boolean setEstado(String estado) {

        if (estado == null) {
            return false; // Não deve ser nulo
        }

        estado = estado.toUpperCase();

        //Array com todos os estados brasileiros
        String[] estadosBrasileiros = {
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS",
            "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"
        };

        //Verifica se o estado que foi passado como parâmetro está contido no array de estados
        for (String estadoString : estadosBrasileiros) {
            if (estadoString.equals(estado)) {
                this.estado = estado;
                return true;
            }
        }

        return false;
    }
    public String getCidade() {
        return cidade;
    }
    public boolean setCidade(String cidade) {
        if (cidade == null) {
            return false; // Não deve ser nulo
        }
        
        // Comprimento mínimo e máximo
        if (cidade.length() < 2 || cidade.length() > 100) {
            return false;
        }
        
        // Caracteres válidos (permite letras, espaços, hífens, apóstrofos e acentos)
        if (!cidade.matches("^[\\p{L}\\s'-]+$")) {
            return false;
        }
        
        // Evita números
        if (cidade.matches(".*\\d.*")) {
            return false;
        }
        this.cidade = cidade;
        return true;
    }
    public String getBairro() {
        return bairro;
    }
    public boolean setBairro(String bairro) {
        if (bairro == null) {
            return false; // Não deve ser nulo
        }
        
        // Comprimento mínimo e máximo
        if (bairro.length() < 2 || bairro.length() > 100) {
            return false;
        }
        
        // Caracteres válidos (permite letras, espaços, hífens, apóstrofos e acentos)
        if (!bairro.matches("^[\\p{L}\\s'-]+$")) {
            return false;
        }
        
        // Evita números
        if (bairro.matches(".*\\d.*")) {
            return false;
        }
        this.bairro = bairro;
        return true;
    }
    public String getRua() {
        return rua;
    }
    public boolean setRua(String rua) {
        if (rua == null) {
            return false; // Não deve ser nulo
        }
        
        // Comprimento mínimo e máximo
        if (rua.length() < 2 || rua.length() > 100) {
            return false;
        }
        
        // Caracteres válidos (permite letras, espaços, hífens, apóstrofos e acentos)
        if (!rua.matches("^[\\p{L}\\s'-]+$")) {
            return false;
        }
        
        // Evita números
        if (rua.matches(".*\\d.*")) {
            return false;
        }
        this.rua = rua;
        return true;
    }
    public String getComplemento() {
        return complemento;
    }
    public boolean setComplemento(String complemento) {
        if (complemento == null) {
            return false; // Não deve ser nulo
        }
        
        // Comprimento mínimo e máximo
        if (complemento.length() < 2 || complemento.length() > 15) {
            return false;
        }
        
        // Caracteres válidos (permite letras, espaços, hífens, apóstrofos e acentos)
        if (!complemento.matches("^[\\p{L}\\s'-]+$")) {
            return false;
        }
        
        // Evita números
        if (complemento.matches(".*\\d.*")) {
            return false;
        }
        this.complemento = complemento;
        return true;
    }
    public String getCep() {
        return cep;
    }
    public boolean setCep(String cep) {

        if (cep == null) {
            return false; // Não deve ser nulo
        }

        // O padrão da expressão regular para um CEP no formato "99999-999"
        String padraoCEP = "\\d{5}-\\d{3}";

        // Compila o padrão em um objeto Pattern
        Pattern pattern = Pattern.compile(padraoCEP);

        // Cria um Matcher com o CEP fornecido
        Matcher matcher = pattern.matcher(cep);

        // Verifica se o CEP corresponde ao padrão
        if (matcher.matches()) {
            this.cep = cep;
            return true;
        } 

        return false;
    }
    public int getNumero() {
        return numero;
    }
    public boolean setNumero(int numero) {
        
        if (numero >= 0) {
            this.numero = numero;
            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Endereco other = (Endereco) obj;
        return id == other.id &&
            numero == other.numero &&
            Objects.equals(estado, other.estado) &&
            Objects.equals(cidade, other.cidade) &&
            Objects.equals(bairro, other.bairro) &&
            Objects.equals(rua, other.rua) &&
            Objects.equals(complemento, other.complemento) &&
            Objects.equals(cep, other.cep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, estado, cidade, bairro, rua, complemento, cep, numero);
    }
}
