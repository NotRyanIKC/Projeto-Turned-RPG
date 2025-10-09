import java.util.*;

public class Arena {
    private String tipo;
    private Scanner sc = new Scanner(System.in);
    private final int CURA_PADRAO = 30;

    private Stack<Personagem> rankingFinal = new Stack<>();
    private int ordemMorteAtual = 1;

    public Arena(String tipo) {
        this.tipo = tipo.toUpperCase();
    }

    public void iniciarBatalha(ArrayList<Personagem> participantes) {
        if (participantes == null || participantes.isEmpty()) {
            System.out.println("Nenhum participante foi adicionado à arena.");
            return;
        }

        if (tipo.equals("PVP")) {
            if (participantes.size() != 2) {
                System.out.println("Combate PVP requer exatamente 2 personagens!");
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
                System.out.println("Combate PVE requer ao menos 1 jogador e 1 monstro!");
                return;
            }
        } else {
            System.out.println("Tipo de batalha inválido! Use 'PVP' ou 'PVE'.");
            return;
        }

        Queue<Personagem> fila = new LinkedList<>(participantes);

        rankingFinal.clear();
        ordemMorteAtual = 1;

        if (tipo.equals("PVP")) {
            combatePVP(fila);
        } else {
            combatePVE(fila);
        }

        exibirRankingFinal();
    }

    private void combatePVP(Queue<Personagem> fila) {
        Personagem p1 = fila.poll();
        Personagem p2 = fila.poll();

        System.out.println("\nInício do combate PVP: " + p1.getNome() + " VS " + p2.getNome());
        System.out.println("-----------------------------------");

        int turno = 1;
        while (p1.estaVivo() && p2.estaVivo()) {
            System.out.println("\n--- Turno " + turno + " ---");
            System.out.println(p1.getNome() + " - HP: " + p1.getVidaAtual() + "/" + p1.getVidaMaxima());
            System.out.println(p2.getNome() + " - HP: " + p2.getVidaAtual() + "/" + p2.getVidaMaxima());

            System.out.println("\n" + p1.getNome() + ", escolha ação: 1) Atacar  2) Curar (" + CURA_PADRAO + " HP)");
            String acao = sc.nextLine().trim();

            if (acao.equals("2")) {
                p1.curar(CURA_PADRAO);
                System.out.println(p1.getNome() + " se curou em " + CURA_PADRAO + " pontos!");
            } else {
                int vidaAntes = p2.getVidaAtual();
                p1.atacar(p2);
                int dano = Math.max(0, vidaAntes - p2.getVidaAtual());
                p2.adicionarDanoRecebido(dano);
            }

            System.out.println("HP atual de " + p1.getNome() + ": " + p1.getVidaAtual());
            System.out.println("HP atual de " + p2.getNome() + ": " + p2.getVidaAtual());

            if (!p2.estaVivo()) {
                p2.setOrdemDeMorte(ordemMorteAtual++);
                rankingFinal.push(p2);
                System.out.println(p2.getNome() + " foi derrotado!");
                break;
            }

            System.out.println("\n" + p2.getNome() + ", escolha ação: 1) Atacar  2) Curar (" + CURA_PADRAO + " HP)");
            acao = sc.nextLine().trim();

            if (acao.equals("2")) {
                p2.curar(CURA_PADRAO);
                System.out.println(p2.getNome() + " se curou em " + CURA_PADRAO + " pontos!");
            } else {
                int vidaAntes = p1.getVidaAtual();
                p2.atacar(p1);
                int dano = Math.max(0, vidaAntes - p1.getVidaAtual());
                p1.adicionarDanoRecebido(dano);
            }

            System.out.println("HP atual de " + p1.getNome() + ": " + p1.getVidaAtual());
            System.out.println("HP atual de " + p2.getNome() + ": " + p2.getVidaAtual());

            if (!p1.estaVivo()) {
                p1.setOrdemDeMorte(ordemMorteAtual++);
                rankingFinal.push(p1);
                System.out.println(p1.getNome() + " foi derrotado!");
                break;
            }

            turno++;
        }

        System.out.println("\nCombate PVP finalizado!");

        if (p1.estaVivo()) {
            p1.setOrdemDeMorte(ordemMorteAtual++);
            rankingFinal.push(p1);
            System.out.println("Vencedor: " + p1.getNome() + "!");
            if (p1.getDono() != null) {
                p1.getDono().adicionarMoedas(100);
            }
        } else if (p2.estaVivo()) {
            p2.setOrdemDeMorte(ordemMorteAtual++);
            rankingFinal.push(p2);
            System.out.println("Vencedor: " + p2.getNome() + "!");
            if (p2.getDono() != null) {
                p2.getDono().adicionarMoedas(100);
            }
        } else {
            System.out.println("Empate! Ambos caíram ao mesmo tempo.");
        }
    }

    private void combatePVE(Queue<Personagem> fila) {
        Personagem jogador = null;
        ArrayList<Monstro> monstros = new ArrayList<>();

        for (Personagem p : fila) {
            if (p instanceof Monstro) monstros.add((Monstro) p);
            else jogador = p;
        }

        if (jogador == null || monstros.isEmpty()) {
            System.out.println("Erro: faltam personagens válidos para o PVE.");
            return;
        }

        System.out.println("\nInício do combate PVE!");
        int turno = 1;

        while (jogador.estaVivo() && !monstros.isEmpty()) {
            System.out.println("\n--- Turno " + turno + " ---");

            System.out.println(jogador.getNome() + " (HP: " + jogador.getVidaAtual() + ")");
            for (int i = 0; i < monstros.size(); i++) {
                Monstro m = monstros.get(i);
                System.out.println(" " + (i + 1) + ") " + m.getNome() + " - HP: " + m.getVidaAtual());
            }

            System.out.println("\nEscolha ação: 1) Atacar  2) Curar");
            String acao = sc.nextLine().trim();

            if (acao.equals("2")) {
                jogador.curar(CURA_PADRAO);
                System.out.println(jogador.getNome() + " se curou em " + CURA_PADRAO + " pontos!");
            } else {
                System.out.print("Escolha o monstro para atacar (1-" + monstros.size() + "): ");
                int escolha;
                try {
                    escolha = Integer.parseInt(sc.nextLine().trim()) - 1;
                } catch (NumberFormatException e) {
                    escolha = -1;
                }

                if (escolha >= 0 && escolha < monstros.size()) {
                    Monstro alvo = monstros.get(escolha);
                    int vidaAntes = alvo.getVidaAtual();
                    jogador.atacar(alvo);
                    int dano = Math.max(0, vidaAntes - alvo.getVidaAtual());
                    alvo.adicionarDanoRecebido(dano);

                    if (!alvo.estaVivo()) {
                        alvo.setOrdemDeMorte(ordemMorteAtual++);
                        rankingFinal.push(alvo);
                        System.out.println(alvo.getNome() + " foi derrotado!");
                        monstros.remove(alvo);
                    }
                } else {
                    System.out.println("Escolha inválida! Você perdeu o turno!");
                }
            }

            for (Monstro m : new ArrayList<>(monstros)) {
                if (!jogador.estaVivo()) break;
                int vidaAntes = jogador.getVidaAtual();
                m.atacar(jogador);
                int dano = Math.max(0, vidaAntes - jogador.getVidaAtual());
                jogador.adicionarDanoRecebido(dano);
            }

            turno++;
        }

        if (jogador.estaVivo()) {
            jogador.setOrdemDeMorte(ordemMorteAtual++);
            rankingFinal.push(jogador);
            System.out.println("\nVitória! Todos os monstros foram derrotados!");

            int recompensaTotal = 0;
            for (Personagem p : rankingFinal) {
                if (p instanceof Monstro) {
                    recompensaTotal += ((Monstro) p).getRecompensaMoedas();
                    jogador.ganharExperiencia(((Monstro) p).getRecompensaExp());
                    
                    
            
                }
            }

            if (jogador.getDono() != null) {
                jogador.getDono().adicionarMoedas(recompensaTotal);
            }
            

        } else {
            jogador.setOrdemDeMorte(ordemMorteAtual++);
            rankingFinal.push(jogador);
            System.out.println("\nDerrota! O jogador caiu em batalha.");
        }
    }

    private void exibirRankingFinal() {
        System.out.println("\n=====  RANKING FINAL  =====");

        Stack<Personagem> temp = new Stack<>();
        while (!rankingFinal.isEmpty()) {
            temp.push(rankingFinal.pop());
        }

        int pos = 1;
        while (!temp.isEmpty()) {
            Personagem p = temp.pop();
            System.out.printf("%dº - %s (Lvl %d)\n", pos++, p.getNome(), p.getNivel());
            System.out.println("Ordem de morte: " + p.getOrdemDeMorte());
            System.out.println("Dano causado: " + p.getDanoCausado());
            System.out.println("Dano recebido: " + p.getDanoRecebido());
        }

        System.out.println("=================================\n");
    }
}
