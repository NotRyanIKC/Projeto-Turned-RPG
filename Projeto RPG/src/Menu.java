import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private ArrayList<Jogador> jogadores = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public void menuPrincipal() {
        while (true) {
            System.out.println("1 - Cadastrar Jogador");
            System.out.println("2 - Login");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine(); // Consome o '\n' deixado pelo nextInt

            switch (opcao) {
                case 1:
                    cadastrarJogador();
                    break;
                case 2:
                    Jogador jogador = login();
                    if (jogador != null) {
                        menuJogador(jogador);
                    }
                    break;
                case 3:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrarJogador() {
        System.out.print("Nome: ");
        String nome = sc.next();
        System.out.print("Senha: ");
        String senha = sc.next();
        Jogador jogador = new Jogador(jogadores.size() + 1, nome, senha, 0, new ListaPersonagem());
        jogadores.add(jogador);
        System.out.println("Jogador cadastrado!");
    }

    private Jogador login() {
        while (true) {
            System.out.print("Nome do jogador (ou digite 'sair' para voltar): ");
            String nome = sc.next();
            if (nome.equalsIgnoreCase("sair")) {
                return null;
            }
            System.out.print("Senha: ");
            String senha = sc.next();
            for (Jogador j : jogadores) {
                if (j.getNome().equals(nome) && j.getSenha().equals(senha)) {
                    System.out.println("Login realizado com sucesso!");
                    return j;
                }
            }
            System.out.println("Nome ou senha incorretos. Tente novamente.");
        }
    }

    private void menuJogador(Jogador jogador) {
        while (true) {
            System.out.println("\nMenu do Jogador: " + jogador.getNome());
            System.out.println("1 - Listar Personagens");
            System.out.println("2 - Criar Personagem");
            System.out.println("3 - Iniciar Combate PvP");
            System.out.println("4 - Iniciar Combate PvE");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine(); // Consome o '\n' deixado pelo nextInt

            switch (opcao) {
                case 1:
                    listarPersonagens(jogador);
                    break;
                case 2:
                    criarPersonagem(jogador);
                    break;
                case 3:
                    System.out.println("Iniciando combate PvP...");
                    // lógica do combate PvP
                    break;
                case 4:
                    System.out.println("Iniciando combate PvE...");
                    // lógica do combate PvE
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void listarPersonagens(Jogador jogador) {
        System.out.println("Personagens do jogador " + jogador.getNome() + ":");
        jogador.personagens.listar();
    }

    private void criarPersonagem(Jogador jogador) {
        System.out.print("Nome do personagem: ");
        String nome = sc.next();
        Personagem personagem = new Personagem(jogador.personagens.getSize() + 1, nome, 1, 100, 100, 0, new ListaHabilidade());
        jogador.personagens.adicionar(personagem);
        System.out.println("Personagem criado!");
    }
}
