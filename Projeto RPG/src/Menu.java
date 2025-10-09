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
        String nome = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        Jogador jogador = new Jogador(jogadores.size() + 1, nome, senha, 0, new ListaPersonagem());
        jogadores.add(jogador);
        System.out.println("Jogador cadastrado!");
    }

    private Jogador login() {
        while (true) {
            System.out.print("Nome do jogador (ou digite 'sair' para voltar): ");
            String nome = sc.nextLine();
            if (nome.equalsIgnoreCase("sair")) {
                return null;
            }
            System.out.print("Senha: ");
            String senha = sc.nextLine();
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
        System.out.println("\n==============================");
        System.out.println("Menu do Jogador: " + jogador.getNome());
        System.out.println("Saldo atual: " + jogador.getSaldoMoedas() + " moedas");
        System.out.println("==============================");
        System.out.println("1 - Listar Personagens");
        System.out.println("2 - Criar Personagem");
        System.out.println("3 - Selecionar Personagem");
        System.out.println("4 - Iniciar Combate PvP");
        System.out.println("5 - Iniciar Combate PvE");
        System.out.println("6 - Sair");
        System.out.print("Escolha uma opção: ");
        int opcao = sc.nextInt();
        sc.nextLine(); // Consome o '\n'

        switch (opcao) {
            case 1:
                listarPersonagens(jogador);
                break;
            case 2:
                criarPersonagem(jogador);
                break;
            case 3:
                selecionarPersonagem(jogador);
                break;
            case 4:
                iniciarCombate("pvp", jogador);
                break;
            case 5:
                iniciarCombate("pve", jogador);
                break;
            case 6:
                System.out.println("Saindo...");
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
        String nome = sc.nextLine();
        Personagem personagem = new Personagem(jogador.personagens.getSize() + 1, nome, 1, 100, 100, 0 );
        personagem.setDono(jogador);
        jogador.personagens.adicionar(personagem);
        System.out.println("Personagem criado!");
    }

    private void selecionarPersonagem(Jogador jogador) {
        jogador.selecionarPersonagem(sc);
    }

    // --------------------- FUNÇÃO ÚNICA PARA COMBATE ---------------------
    private void iniciarCombate(String tipo, Jogador jogador) {
        ArrayList<Personagem> participantes = new ArrayList<>();

        if (tipo.equalsIgnoreCase("pvp")) {
            if (jogadores.size() < 2) {
                System.out.println("É necessário pelo menos 2 jogadores cadastrados para PvP!");
                return;
            }

            System.out.println("\n=== MODO PVP ===");
            System.out.println("Escolha o oponente:");
            for (int i = 0; i < jogadores.size(); i++) {
                if (!jogadores.get(i).equals(jogador)) {
                    System.out.println((i + 1) + " - " + jogadores.get(i).getNome());
                }
            }

            System.out.print("\n Escolha: ");
            int indice = sc.nextInt() - 1;
            sc.nextLine();

            if (indice < 0 || indice >= jogadores.size() || jogadores.get(indice).equals(jogador)) {
                System.out.println("Oponente inválido!");
                return;
            }

            Jogador oponente = jogadores.get(indice);

            // Ambos precisam ter personagem selecionado
            if (jogador.getPersonagemSelecionado() == null) {
                System.out.println("\n Você não tem personagem selecionado!");
                return;
            }
            if (oponente.getPersonagemSelecionado() == null) {
                System.out.println(" O oponente não tem personagem selecionado!");
                return;
            }

            participantes.add(jogador.getPersonagemSelecionado());
            participantes.add(oponente.getPersonagemSelecionado());

        } else if (tipo.equalsIgnoreCase("pve")) {
            if (jogador.getPersonagemSelecionado() == null) {
                System.out.println("Selecione um personagem antes de iniciar o combate!");
                return;
            }

            participantes.add(jogador.getPersonagemSelecionado());


            participantes.add(new Monstro(1,"Goblin", 1, 30, 3, 30, 10));
            participantes.add(new Monstro(2,"Goblin 2", 2, 50, 5, 50, 20));
        }

        Arena arena = new Arena(tipo);
        arena.iniciarBatalha(participantes);
    }

}
