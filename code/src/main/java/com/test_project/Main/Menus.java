package com.test_project.Main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import com.test_project.Carrinho;
import com.test_project.Pedido;
import com.test_project.Produto;
import com.test_project.Usuario;

public class Menus {
    public static void telaInicial(){
        System.out.println(" ====  LOGIN  ====");
        System.out.println(" 1 -> Efetuar Login");
        System.out.println(" 2 -> Cadastrar-se");
        System.out.println(" 3 -> Sair");
    }

    public static void telaUsuario(){
        System.out.println(" ====  TELA PRINCIPAL  ====");
        System.out.println(" 1 -> Perfil");
        System.out.println(" 2 -> Vizualizar Carrinho");
        System.out.println(" 3 -> Produtos");
        System.out.println(" 4 -> LOGOUT");
    }

    public static void perfilUsuario(Usuario cliente){
        System.out.println(" ====  PERFIL  ====");
        System.out.println(cliente.toString());

        System.out.println(" 1 -> Editar Informações");
        System.out.println(" 2 -> Listar Pedidos");
        System.out.println(" 3 -> Excluir Usuário");
        System.out.println(" 4 -> Voltar");
    }

    public static void editarUsuario(){
        System.out.println(" ====  EDITAR PERFIL  ====");
        
        System.out.println(" 1 -> Nome completo");
        System.out.println(" 2 -> Email");
        System.out.println(" 3 -> Telefone");
        System.out.println(" 4 -> Usuario");
        System.out.println(" 5 -> Senha");
        System.out.println(" 6 -> Data Nascimento");
        System.out.println(" 7 -> Adicionar Endereço");
        System.out.println(" 8 -> Excluir Endereço");
        System.out.println(" 9 -> Voltar");
    }

    public static void carrinhoUsuario(Carrinho carrinho){
        System.out.println(" ====  CARRINHO  ====");

        System.out.println(carrinho.toString());

        System.out.println(" 1 -> Remover Item");
        System.out.println(" 2 -> Esvaziar carrinho");
        System.out.println(" 3 -> Realizar Pedido");
        System.out.println(" 4 -> Voltar");
    }

    public static void telaProdutos(ArrayList<Produto> produtos){
        System.out.println(" ====  PRODUTOS  ====");

        for (Produto produto : produtos) {
            System.out.println(produto.toString());
        }

        System.out.println(" 1 -> Adicionar um Produto ao Carrinho");
        System.out.println(" 2 -> Filtrar Produtos");
        System.out.println(" 3 -> Voltar");
    }

    public static void telaPedidos(ArrayList<Pedido> pedidos, Usuario cliente){
        System.out.println("========== PEDIDOS ==========");

        System.out.println();

        for (Pedido pedido : pedidos) {
            System.out.println(pedido.toString(cliente));
            System.out.println();
        }

        System.out.println(" 1 -> Cancelar Pedido");
        System.out.println(" 2 -> Voltar");

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite uma data no formato 'dd-MM-yyyy': ");
        String dataTexto = scanner.nextLine();

        try {
            // Use um DateTimeFormatter com o formato 'dd-MM-yyyy' para analisar a entrada do usuário
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate data = LocalDate.parse(dataTexto, formatter);

            System.out.println("Você digitou a data: " + data);
        } catch (Exception e) {
            System.err.println("Formato de data inválido. Use o formato 'dd-MM-yyyy'.");
        }

        scanner.close();
    }
}