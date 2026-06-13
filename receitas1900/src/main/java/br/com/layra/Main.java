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
                    System.out.println("Encerrando o programa. Ate logo!");
                    break;
                default:
                    System.out.println("Opcao invalida! Tente novamente.");
            }

            System.out.println();
        } while (opcao != 0);
    }

    static void mostrarMenu() {
        System.out.println("===== CADERNO DE RECEITAS =====");
        System.out.println("1 - Cadastrar receita");
        System.out.println("2 - Listar receitas");
        System.out.println("3 - Buscar receita pelo nome");
        System.out.println("4 - Editar receita");
        System.out.println("5 - Excluir receita");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    static int lerOpcao() {
        String linha = entrada.nextLine();
        try {
            return Integer.parseInt(linha.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static String lerTextoMultiplasLinhas() {
        StringBuilder blocoTexto = new StringBuilder();
        while (entrada.hasNextLine()) {
            String linha = entrada.nextLine();
            if (linha.equalsIgnoreCase("FIM")) {
                break;
            }
            if (blocoTexto.length() > 0) {
                blocoTexto.append("[BR]");
            }
            blocoTexto.append(linha);
        }
        return blocoTexto.toString();
    }

    static void cadastrar() {
        if (qtdReceitas >= receitas.length) {
            System.out.println("O caderno esta cheio! Nao da para cadastrar mais receitas.");
            return;
        }

        System.out.print("Nome da receita: ");
        String nome = entrada.nextLine();

        if (nome.trim().isEmpty()) {
            System.out.println("O nome nao pode ficar em branco. Receita nao cadastrada.");
            return;
        }

        System.out.println("Digite os Ingredientes (Aperte ENTER para pular linha. Digite FIM para encerrar):");
        String ingredientes = lerTextoMultiplasLinhas();

        if (ingredientes.trim().isEmpty()) {
            System.out.println("Adicione pelo menos um ingrediente. Receita nao cadastrada.");
            return;
        }

        System.out.println("Digite o Modo de preparo (Aperte ENTER para pular linha. Digite FIM para encerrar):");
        String preparo = lerTextoMultiplasLinhas();

        if (preparo.trim().isEmpty()) {
            System.out.println("Adicione o modo de preparo, nao pode ser cadastrada sem.");
            return;
        }

        receitas[qtdReceitas][0] = nome;
        receitas[qtdReceitas][1] = ingredientes;
        receitas[qtdReceitas][2] = preparo;
        qtdReceitas++;

        System.out.println("Receita cadastrada com sucesso!");
    }

    static void listar() {
        if (qtdReceitas == 0) {
            System.out.println("Nenhuma receita cadastrada ainda.");
            return;
        }

        System.out.println("===== RECEITAS CADASTRADAS =====");
        for (int i = 0; i < qtdReceitas; i++) {
            System.out.println("Receita " + (i + 1));
            System.out.println("  Nome.........: " + receitas[i][0]);
            System.out.println("  Ingredientes.: \n" + receitas[i][1].replace("[BR]", "\n"));
            System.out.println("  Preparo......: \n" + receitas[i][2].replace("[BR]", "\n"));
            System.out.println("--------------------------------");
        }
    }

    static void buscar() {
        if (qtdReceitas == 0) {
            System.out.println("Nenhuma receita cadastrada ainda.");
            return;
        }

        System.out.print("Digite o nome da receita: ");
        String alvo = entrada.nextLine();

        boolean achou = false;
        for (int i = 0; i < qtdReceitas; i++) {
            if (receitas[i][0].toLowerCase().contains(alvo.toLowerCase())) {
                System.out.println("Receita encontrada:");
                System.out.println("  Nome.........: " + receitas[i][0]);
                System.out.println("  Ingredientes.: \n" + receitas[i][1].replace("[BR]", "\n"));
                System.out.println("  Preparo......: \n" + receitas[i][2].replace("[BR]", "\n"));
                System.out.println("--------------------------------");
                achou = true;
            }
        }

        if (!achou) {
            System.out.println("Nenhuma receita encontrada com esse nome.");
        }
    }

    static void editar() {
        if (qtdReceitas == 0) {
            System.out.println("Nenhuma receita cadastrada para editar.");
            return;
        }

        listar();
        System.out.print("Digite o numero da receita que deseja editar: ");
        int num = lerOpcao();

        if (num < 1 || num > qtdReceitas) {
            System.out.println("Numero invalido.");
            return;
        }

        int indice = num - 1;

        System.out.println("\nEditando a receita: " + receitas[indice][0]);
        System.out.println("Digite os NOVOS Ingredientes (Aperte ENTER para pular linha. Digite FIM para encerrar):");
        String novosIngredientes = lerTextoMultiplasLinhas();

        System.out.println("Digite o NOVO Modo de preparo (Aperte ENTER para pular linha. Digite FIM para encerrar):");
        String novoPreparo = lerTextoMultiplasLinhas();

        if (!novosIngredientes.trim().isEmpty()) {
            receitas[indice][1] = novosIngredientes;
        }
        if (!novoPreparo.trim().isEmpty()) {
            receitas[indice][2] = novoPreparo;
        }

        System.out.println("Receita alterada com sucesso!");
    }

    static void salvar() {
        if (qtdReceitas == 0) {
            try {
                FileWriter escritor = new FileWriter(arquivo);
                escritor.write("");
                escritor.close();
            } catch (IOException e) {
            }
            return;
        }

        try {
            FileWriter escritor = new FileWriter(arquivo);
            for (int i = 0; i < qtdReceitas; i++) {
                escritor.write("Nome: " + receitas[i][0] + "\n");
                escritor.write("Ingredientes: " + receitas[i][1] + "\n");
                escritor.write("Modo de preparo: " + receitas[i][2] + "\n");
                escritor.write("=====\n");
            }
            escritor.close();
            System.out.println("Receitas salvas automaticamente.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
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
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    static void excluir() {
        if (qtdReceitas == 0) {
            System.out.println("Nenhuma receita cadastrada ainda.");
            return;
        }

        listar();
        System.out.print("Digite o numero da receita que deseja excluir: ");
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

        System.out.println("Receita excluida com sucesso!");
    }
}