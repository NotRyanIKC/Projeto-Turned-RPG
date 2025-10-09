import java.util.*;

public class Arena {
    private String tipo; // "PVP" ou "PVE"
    private Scanner sc = new Scanner(System.in);
    private final int CURA_PADRAO = 30;

    public Arena(String tipo) {
        this.tipo = tipo.toUpperCase();
    }

    public void iniciarBatalha(ArrayList<Personagem> participantes) {
        if (participantes == null || participantes.isEmpty()) {
            System.out.println("Nenhum participante foi adicionado à arena.");
            return;
        }

        // 🔒 Verificações de segurança
        if (tipo.equals("PVP")) {
            if (participantes.size() != 2) {
                System.out.println("❌ Combate PVP requer exatamente 2 personagens!");
                return;
            }
        } else if (tipo.equals("PVE")) {
            boolean temJogador = false;
            boolean temMonstro = false;

            for (Personagem p : participantes) {
                if (p instanceof Monstro) temMonstro = true;
                else temJogador = true;
            }

            if (!temJogador || !temMonstro) {
                System.out.println("❌ Combate PVE requer ao menos 1 jogador e 1 monstro!");
                return;
            }
        } else {
            System.out.println("❌ Tipo de batalha inválido! Use 'PVP' ou 'PVE'.");
            return;
        }

        // ✅ Inicia a fila de combate
        Queue<Personagem> fila = new LinkedList<>(participantes);

        if (tipo.equals("PVP")) {
            combatePVP(fila);
        } else {
            combatePVE(fila);
        }
    }

    // --------------------- COMBATE PVP ---------------------
// --------------------- COMBATE PVP ---------------------
// --------------------- COMBATE PVP ---------------------
private void combatePVP(Queue<Personagem> fila) {
    // Verifica se há dois personagens na fila
    if (fila.size() != 2) {
        System.out.println("Combate PVP precisa de exatamente 2 personagens!");
        return;
    }

    // Remove da fila na ordem
    Personagem p1 = fila.poll();
    Personagem p2 = fila.poll();

    System.out.println("\nInício do combate PVP: " + p1.getNome() + " VS " + p2.getNome());
    System.out.println("-----------------------------------");
    System.out.println(p1.getNome() + " - HP: " + p1.getVidaAtual() + "/" + p1.getVidaMaxima());
    System.out.println(p2.getNome() + " - HP: " + p2.getVidaAtual() + "/" + p2.getVidaMaxima());

    int turno = 1;
    while (p1.estaVivo() && p2.estaVivo()) {
        System.out.println("\n--- Turno " + turno + " ---");
        System.out.println(p1.getNome() + " (HP: " + p1.getVidaAtual() + "/" + p1.getVidaMaxima() + ")");
        System.out.println(p2.getNome() + " (HP: " + p2.getVidaAtual() + "/" + p2.getVidaMaxima() + ")");

        // Turno do primeiro jogador
        System.out.println("\n" + p1.getNome() + ", escolha ação: 1) Atacar  2) Curar (" + CURA_PADRAO + " HP)");
        String acao = sc.nextLine().trim();

        if (acao.equals("2")) {
            p1.curar(CURA_PADRAO);
            System.out.println(p1.getNome() + " se curou em " + CURA_PADRAO + " pontos!");
        } else {
            p1.atacar(p2);
            System.out.println(p1.getNome() + " atacou " + p2.getNome() + "!");
        }

        if (!p2.estaVivo()) {
            System.out.println(p2.getNome() + " foi derrotado!");
            break;
        }

        // Turno do segundo jogador
        System.out.println("\n" + p2.getNome() + ", escolha ação: 1) Atacar  2) Curar (" + CURA_PADRAO + " HP)");
        acao = sc.nextLine().trim();

        if (acao.equals("2")) {
            p2.curar(CURA_PADRAO);
            System.out.println(p2.getNome() + " se curou em " + CURA_PADRAO + " pontos!");
        } else {
            p2.atacar(p1);
            System.out.println(p2.getNome() + " atacou " + p1.getNome() + "!");
        }

        if (!p1.estaVivo()) {
            System.out.println(p1.getNome() + " foi derrotado!");
            break;
        }

        turno++;
    }

    System.out.println("\nCombate PVP finalizado!");
    
    
    if (p1.estaVivo() && !p2.estaVivo()) {
        System.out.println("Vencedor: " + p1.getNome() + "!");
    } else if (p2.estaVivo() && !p1.estaVivo()) {
        System.out.println("Vencedor: " + p2.getNome() + "!");
    } else {
        System.out.println("Empate! Ambos caíram ao mesmo tempo.");
    }
}




    // --------------------- COMBATE PVE ---------------------
private void combatePVE(Queue<Personagem> fila) {
    Personagem jogador = null;
    ArrayList<Monstro> monstros = new ArrayList<>();

    // Separa jogador e monstros
    for (Personagem p : fila) {
        if (p instanceof Monstro) monstros.add((Monstro) p);
        else if (jogador == null) jogador = p; 
    }

    if (jogador == null || monstros.isEmpty()) {
        System.out.println("Erro: faltam personagens válidos para o PVE.");
        return;
    }

    System.out.println("\nInício do combate PVE!");
    int turno = 1;

    while (jogador.estaVivo() && !monstros.isEmpty()) {
        System.out.println("\n--- Turno " + turno + " ---");
        System.out.println("Jogador: " + jogador.getNome() + " (HP: " + jogador.getVidaAtual() + "/" + jogador.getVidaMaxima() + ")");
        System.out.println("Monstros:");
        for (int i = 0; i < monstros.size(); i++) {
            Monstro m = monstros.get(i);
            System.out.println(" " + (i + 1) + ") " + m.getNome() + " - " + m.getVidaAtual() + "/" + m.getVidaMaxima() + " HP");
        }

        // Escolhe ação
        System.out.println("\nEscolha ação: 1) Atacar  2) Curar");
        String acao = sc.nextLine().trim();

        if (acao.equals("2")) {
            jogador.curar(CURA_PADRAO);
            System.out.println(jogador.getNome() + " se curou em " + CURA_PADRAO + " pontos!");
        } else {
            // Jogador escolhe o monstro alvo
            System.out.print("Escolha o monstro para atacar (1-" + monstros.size() + "): ");
            int escolha = -1;
            try {
                escolha = Integer.parseInt(sc.nextLine().trim()) - 1;
            } catch (NumberFormatException e) {
                escolha = -1;
            }

            if (escolha >= 0 && escolha < monstros.size()) {
                Monstro alvo = monstros.get(escolha);
                jogador.atacar(alvo);
                System.out.println(jogador.getNome() + " atacou " + alvo.getNome() + "!");

                if (!alvo.estaVivo()) {
                    System.out.println(alvo.getNome() + " foi derrotado!");
                    jogador.ganharExperiencia(alvo.getRecompensaExp());
                    monstros.remove(escolha);
                }
            } else {
                System.out.println("Escolha inválida! Você perdeu o turno!");
            }
        }

        // Monstros atacam de volta
        for (Monstro m : new ArrayList<>(monstros)) {
            if (!jogador.estaVivo()) break;

            m.atacar(jogador);
            System.out.println(m.getNome() + " atacou " + jogador.getNome() + " -> HP: " + jogador.getVidaAtual() + "/" + jogador.getVidaMaxima());
        }

        turno++;
    }

    if (jogador.estaVivo()) {
        System.out.println("\nVitória! Todos os monstros foram derrotados!");
    } else {
        System.out.println("\nDerrota! O jogador caiu em batalha.");
    }
}

}
