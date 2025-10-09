import java.util.Scanner;

public class Menu {
    private final ListaJogador jogadores = new ListaJogador();
    private final Scanner sc;

    public Menu(Scanner sc) { this.sc = sc; }

    public void menuPrincipal() {
        while (true) {
            clear();
            System.out.println("1 - Cadastrar Jogador");
            System.out.println("2 - Login");
            System.out.println("3 - Sair");
            int opcao = lerInt("Escolha uma opção: ");

            if (opcao == 1) { cadastrarJogador(); pause(); }
            else if (opcao == 2) {
                Jogador j = login();
                if (j != null) menuJogador(j);
            } else if (opcao == 3) return;
            else { System.out.println("Opção inválida."); pause(); }
        }
    }

    private void cadastrarJogador() {
        clear();
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
            clear();
            System.out.print("Nome do jogador (ou 'sair'): ");
            String nome = sc.nextLine();
            if (nome.equalsIgnoreCase("sair")) return null;
            System.out.print("Senha: ");
            String senha = sc.nextLine();

            for (int i = 0; i < jogadores.size(); i++) {
                Jogador j = jogadores.get(i);
                if (j.getNome().equals(nome) && j.getSenha().equals(senha)) {
                    System.out.println("Login realizado com sucesso!");
                    pause();
                    return j;
                }
            }
            System.out.println("Nome ou senha incorretos."); 
            pause();
        }
    }

    private void menuJogador(Jogador jogador) {
        while (true) {
            clear();
            System.out.println("==============================");
            System.out.println("Menu do Jogador: " + jogador.getNome());
            System.out.println("Saldo: " + jogador.getSaldoMoedas() + " moedas");
            System.out.println("==============================");
            System.out.println("1 - Listar Personagens");
            System.out.println("2 - Criar Personagem");
            System.out.println("3 - Selecionar Personagem");
            System.out.println("4 - Iniciar Combate PvP");
            System.out.println("5 - Iniciar Combate PvE");
            System.out.println("6 - Curar Personagem (fora do combate)");
            System.out.println("7 - Sair");
            int op = lerInt("Escolha: ");

            if (op == 1) { listarPersonagens(jogador); pause(); }
            else if (op == 2) { criarPersonagem(jogador); pause(); }
            else if (op == 3) { selecionarPersonagem(jogador); pause(); }
            else if (op == 4) { iniciarCombate("pvp", jogador); pause(); }
            else if (op == 5) { iniciarCombate("pve", jogador); pause(); }
            else if (op == 6) { curarForaCombate(jogador); pause(); }
            else if (op == 7) return;
            else { System.out.println("Opção inválida."); pause(); }
        }
    }

    private void listarPersonagens(Jogador jogador) {
        clear();
        System.out.println("Personagens de " + jogador.getNome() + ":");
        jogador.personagens.listar();
    }

    private void criarPersonagem(Jogador jogador) {
        clear();
        System.out.print("Nome do personagem: ");
        String nome = sc.nextLine();
        int novoId = jogador.personagens.getSize() + 1;
        Personagem p = new Personagem(novoId, nome, 1, 100, 12, 4, jogador) {};
        jogador.personagens.adicionar(p);
        System.out.println("Personagem criado!");
    }

    private void selecionarPersonagem(Jogador jogador) {
        clear();
        jogador.selecionarPersonagem(sc);
    }

    private void curarForaCombate(Jogador jogador) {
        clear();
        Personagem selecionado = jogador.getPersonagemSelecionado();
        if (selecionado == null) {
            System.out.println("Nenhum personagem selecionado! Selecione primeiro.");
            return;
        }
        jogador.curarForaCombate(selecionado, sc);
    }

    private void iniciarCombate(String tipo, Jogador jogador) {
        clear();
        FilaArena fila = new FilaArena();

        if (tipo.equalsIgnoreCase("pvp")) {
            if (jogadores.size() < 2) {
                System.out.println("Precisa de pelo menos 2 jogadores para PvP.");
                return;
            }
            System.out.println("Escolha o oponente:");
            for (int i = 0; i < jogadores.size(); i++) {
                Jogador j = jogadores.get(i);
                if (j != jogador) System.out.println((i + 1) + " - " + j.getNome());
            }
            int idx = lerInt("Índice: ") - 1;
            if (idx < 0 || idx >= jogadores.size()) {
                System.out.println("Índice inválido.");
                return;
            }
            Jogador op = jogadores.get(idx);
            if (op == jogador) {
                System.out.println("Não pode lutar consigo mesmo.");
                return;
            }
            if (jogador.getPersonagemSelecionado() == null || op.getPersonagemSelecionado() == null) {
                System.out.println("Ambos precisam selecionar um personagem.");
                return;
            }
            fila.adicionar(jogador.getPersonagemSelecionado());
            fila.adicionar(op.getPersonagemSelecionado());
            new Arena("pvp").iniciarBatalha(fila);
        } else if (tipo.equalsIgnoreCase("pve")) {
            if (jogador.getPersonagemSelecionado() == null) {
                System.out.println("Selecione um personagem antes.");
                return;
            }
            fila.adicionar(jogador.getPersonagemSelecionado());
            fila.adicionar(new Monstro(1, "Goblin", 1, 40, 4, 30, 10));
            fila.adicionar(new Monstro(2, "Orc", 2, 70, 7, 60, 20));
            new Arena("pve").iniciarBatalha(fila);
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    // ===== utils =====
    private int lerInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.println("Digite um número válido."); }
        }
    }

    private void pause() {
        System.out.print("\nPressione ENTER para continuar...");
        sc.nextLine();
    }

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
