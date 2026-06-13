package br.com.layra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static Scanner entrada = new Scanner(System.in);
    static String[][] receitas = new String[100][3];
    static int qtdReceitas = 0;
    static String arquivo = "receitas.txt";

    public static void main(String[] args) {

        int opcao;

        carregar();

        do {

            mostrarMenu();
            opcao = lerOpcao();

            switch (opcao) {

                case 1:
                    cadastrar();
                    salvar();
                    break;

                case 2:
                    listar();
                    break;

                case 3:
                    buscar();
                    break;

                case 4:
                    editar();
                    salvar();
                    break;

                case 5:
                    excluir();
                    salvar();
                    break;

                case 0:
                    System.out.println("\nEncerrando o programa...");
                    break;

                default:
                    System.out.println("\nOpcao invalida.");
            }

            System.out.println();

        } while (opcao != 0);
    }

    static void mostrarMenu() {

        System.out.println("\n===== CADERNO DE RECEITAS =====");
        System.out.println("1 - Cadastrar receita");
        System.out.println("2 - Listar receitas");
        System.out.println("3 - Buscar receita");
        System.out.println("4 - Editar receita");
        System.out.println("5 - Excluir receita");
        System.out.println("0 - Sair");
        System.out.print("\nEscolha uma opcao: ");
    }

    static int lerOpcao() {

        String linha = entrada.nextLine();

        try {
            return Integer.parseInt(linha.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static String lerTexto() {

        StringBuilder texto = new StringBuilder();

        while (entrada.hasNextLine()) {

            String linha = entrada.nextLine();

            if (linha.equalsIgnoreCase("FIM")) {
                break;
            }

            if (texto.length() > 0) {
                texto.append("[BR]");
            }

            texto.append(linha);
        }

        return texto.toString();
    }

    static void cadastrar() {

        if (qtdReceitas >= receitas.length) {
            System.out.println("\nLimite de receitas atingido.");
            return;
        }

        System.out.print("\nNome da receita: ");
        String nome = entrada.nextLine();

        if (nome.trim().isEmpty()) {
            System.out.println("Nome invalido.");
            return;
        }

        System.out.println("\nDigite os ingredientes:");
        System.out.println("(Digite FIM para terminar)");

        String ingredientes = lerTexto();

        if (ingredientes.trim().isEmpty()) {
            System.out.println("Digite os ingredientes.");
            return;
        }

        System.out.println("\nDigite o modo de preparo:");
        System.out.println("(Digite FIM para terminar)");

        String preparo = lerTexto();

        if (preparo.trim().isEmpty()) {
            System.out.println("Digite o modo de preparo.");
            return;
        }

        receitas[qtdReceitas][0] = nome;
        receitas[qtdReceitas][1] = ingredientes;
        receitas[qtdReceitas][2] = preparo;

        qtdReceitas++;

        System.out.println("\nReceita cadastrada com sucesso!");
    }

    static void listar() {

        if (qtdReceitas == 0) {
            System.out.println("\nNenhuma receita cadastrada.");
            return;
        }

        System.out.println("\n=========== RECEITAS CADASTRADAS ===========");

        for (int i = 0; i < qtdReceitas; i++) {

            System.out.println("\nReceita " + (i + 1));
            System.out.println("\nNome:");
            System.out.println(receitas[i][0]);
            System.out.println("\nIngredientes:");
            System.out.println(receitas[i][1].replace("[BR]", "\n"));
            System.out.println("\nModo de preparo:");
            System.out.println(receitas[i][2].replace("[BR]", "\n"));
            System.out.println("\n--------------------------------------------");
        }
    }

    static void buscar() {

        if (qtdReceitas == 0) {
            System.out.println("\nNenhuma receita cadastrada.");
            return;
        }

        System.out.print("\nDigite o nome da receita: ");
        String busca = entrada.nextLine();

        boolean encontrou = false;

        for (int i = 0; i < qtdReceitas; i++) {

            if (receitas[i][0].toLowerCase().contains(busca.toLowerCase())) {

                System.out.println("\nReceita encontrada:");
                System.out.println("\nNome:");
                System.out.println(receitas[i][0]);
                System.out.println("\nIngredientes:");
                System.out.println(receitas[i][1].replace("[BR]", "\n"));
                System.out.println("\nModo de preparo:");
                System.out.println(receitas[i][2].replace("[BR]", "\n"));
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("\nReceita nao encontrada.");
        }
    }

    static void editar() {

        if (qtdReceitas == 0) {
            System.out.println("\nNenhuma receita cadastrada.");
            return;
        }

        listar();

        System.out.print("\nDigite o numero da receita: ");
        int num = lerOpcao();

        if (num < 1 || num > qtdReceitas) {
            System.out.println("Numero invalido.");
            return;
        }

        int indice = num - 1;

        System.out.println("\nEditando: " + receitas[indice][0]);
        System.out.println("\nNovos ingredientes:");
        System.out.println("(Digite FIM para terminar)");

        String ingredientes = lerTexto();

        System.out.println("\nNovo modo de preparo:");
        System.out.println("(Digite FIM para terminar)");

        String preparo = lerTexto();

        if (!ingredientes.trim().isEmpty()) {
            receitas[indice][1] = ingredientes;
        }

        if (!preparo.trim().isEmpty()) {
            receitas[indice][2] = preparo;
        }

        System.out.println("\nReceita alterada com sucesso!");
    }

    static void excluir() {

        if (qtdReceitas == 0) {
            System.out.println("\nNenhuma receita cadastrada.");
            return;
        }

        listar();

        System.out.print("\nDigite o numero da receita: ");
        int num = lerOpcao();

        if (num < 1 || num > qtdReceitas) {
            System.out.println("Numero invalido.");
            return;
        }

        for (int i = num - 1; i < qtdReceitas - 1; i++) {

            receitas[i][0] = receitas[i + 1][0];
            receitas[i][1] = receitas[i + 1][1];
            receitas[i][2] = receitas[i + 1][2];
        }

        qtdReceitas--;

        System.out.println("\nReceita excluida com sucesso!");
    }

    static void salvar() {

        try {

            FileWriter escritor = new FileWriter(arquivo);

            for (int i = 0; i < qtdReceitas; i++) {

                escritor.write("Nome: " + receitas[i][0] + "\n");
                escritor.write("Ingredientes: " + receitas[i][1] + "\n");
                escritor.write("Modo de preparo: " + receitas[i][2] + "\n");
                escritor.write("=====\n");
            }

            escritor.close();

        } catch (IOException e) {
            System.out.println("Erro ao salvar.");
        }
    }

    static void carregar() {

        File f = new File(arquivo);

        if (!f.exists()) {
            return;
        }

        try {

            Scanner leitor = new Scanner(f);
            qtdReceitas = 0;

            while (leitor.hasNextLine() && qtdReceitas < receitas.length) {

                String linha = leitor.nextLine();

                if (linha.startsWith("Nome: ")) {

                    receitas[qtdReceitas][0] = linha.substring(6);

                } else if (linha.startsWith("Ingredientes: ")) {

                    receitas[qtdReceitas][1] = linha.substring(14);

                } else if (linha.startsWith("Modo de preparo: ")) {

                    receitas[qtdReceitas][2] = linha.substring(17);

                } else if (linha.equals("=====")) {

                    qtdReceitas++;
                }
            }

            leitor.close();

        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo.");
        }
    }
}