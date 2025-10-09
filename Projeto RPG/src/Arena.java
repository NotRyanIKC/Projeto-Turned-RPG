public class Arena {
    private String tipo;
    private final java.util.Scanner sc = new java.util.Scanner(System.in);
    private final int CURA_PADRAO = 30;

    private PilhaArena rankingFinal = new PilhaArena();
    private int ordemMorteAtual = 1;

    public Arena(String tipo) {
        this.tipo = tipo.toUpperCase();
    }

    public void iniciarBatalha(FilaArena participantes) {
        if (participantes == null || participantes.estaVazia()) {
            System.out.println("Nenhum participante foi adicionado à arena.");
            return;
        }

        if (tipo.equals("PVP")) {
            FilaArena copia = new FilaArena();
            int count = 0;
            while (!participantes.estaVazia()) {
                Personagem p = participantes.remover();
                copia.adicionar(p);
                count++;
            }
            if (count != 2) {
                System.out.println("Combate PVP requer exatamente 2 personagens!");
                while (!copia.estaVazia()) participantes.adicionar(copia.remover());
                return;
            }
            while (!copia.estaVazia()) participantes.adicionar(copia.remover());
        } else if (tipo.equals("PVE")) {
            boolean temJogador = false;
            boolean temMonstro = false;
            FilaArena buffer = new FilaArena();
            while (!participantes.estaVazia()) {
                Personagem p = participantes.remover();
                buffer.adicionar(p);
                if (p instanceof Monstro) temMonstro = true; else temJogador = true;
            }
            while (!buffer.estaVazia()) participantes.adicionar(buffer.remover());
            if (!temJogador || !temMonstro) {
                System.out.println("Combate PVE requer ao menos 1 jogador e 1 monstro!");
                return;
            }
        } else {
            System.out.println("Tipo de batalha inválido! Use 'PVP' ou 'PVE'.");
            return;
        }

        rankingFinal.limpar();
        ordemMorteAtual = 1;

        if (tipo.equals("PVP")) combatePVP(participantes);
        else combatePVE(participantes);

        exibirRankingFinal();
    }

    private void combatePVP(FilaArena fila) {
        Personagem p1 = fila.remover();
        Personagem p2 = fila.remover();

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

            if (!p2.estaVivo()) {
                p2.setOrdemDeMorte(ordemMorteAtual++);
                rankingFinal.adicionar(p2);
                excluirSeJogador(p2); // <<< remove da conta
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

            if (!p1.estaVivo()) {
                p1.setOrdemDeMorte(ordemMorteAtual++);
                rankingFinal.adicionar(p1);
                excluirSeJogador(p1); // <<< remove da conta
                System.out.println(p1.getNome() + " foi derrotado!");
                break;
            }

            turno++;
        }

        System.out.println("\nCombate PVP finalizado!");

        if (p1.estaVivo() && !p2.estaVivo()) {
            p1.setOrdemDeMorte(ordemMorteAtual++);
            rankingFinal.adicionar(p1);
            System.out.println("Vencedor: " + p1.getNome() + "!");
            if (p1.getDono() != null) p1.getDono().adicionarMoedas(100);
        } else if (p2.estaVivo() && !p1.estaVivo()) {
            p2.setOrdemDeMorte(ordemMorteAtual++);
            rankingFinal.adicionar(p2);
            System.out.println("Vencedor: " + p2.getNome() + "!");
            if (p2.getDono() != null) p2.getDono().adicionarMoedas(100);
        } else if (p1.estaVivo() && p2.estaVivo()) {
            System.out.println("Empate! Ambos permaneceram de pé ao fim do turno.");
            p1.setOrdemDeMorte(ordemMorteAtual++);
            p2.setOrdemDeMorte(ordemMorteAtual++);
            rankingFinal.adicionar(p2);
            rankingFinal.adicionar(p1);
        } else {
            System.out.println("Empate! Ambos caíram ao mesmo tempo.");
        }
    }

    private void combatePVE(FilaArena fila) {
        Personagem jogador = null;
        ListaMonstro monstros = new ListaMonstro();

        while (!fila.estaVazia()) {
            Personagem p = fila.remover();
            if (p instanceof Monstro) monstros.addLast((Monstro) p);
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
                        rankingFinal.adicionar(alvo);
                        System.out.println(alvo.getNome() + " foi derrotado!");
                        monstros.remove(alvo); // remove da lista de monstros
                    }
                } else {
                    System.out.println("Escolha inválida! Você perdeu o turno!");
                }
            }

            for (int i = 0; i < monstros.size(); i++) {
                if (!jogador.estaVivo()) break;
                Monstro m = monstros.get(i);
                int vidaAntes = jogador.getVidaAtual();
                m.atacar(jogador);
                int dano = Math.max(0, vidaAntes - jogador.getVidaAtual());
                jogador.adicionarDanoRecebido(dano);
            }

            turno++;
        }

        if (jogador.estaVivo()) {
            jogador.setOrdemDeMorte(ordemMorteAtual++);
            rankingFinal.adicionar(jogador);
            System.out.println("\nVitória! Todos os monstros foram derrotados!");

            int recompensaTotal = 0;
            PilhaArena temp = new PilhaArena();
            while (!rankingFinal.estaVazia()) {
                Personagem p = rankingFinal.remover();
                if (p instanceof Monstro) {
                    recompensaTotal += ((Monstro) p).getRecompensaMoedas();
                    jogador.ganharExperiencia(((Monstro) p).getRecompensaExp());
                }
                temp.adicionar(p);
            }
            while (!temp.estaVazia()) rankingFinal.adicionar(temp.remover());

            if (jogador.getDono() != null) jogador.getDono().adicionarMoedas(recompensaTotal);
        } else {
            jogador.setOrdemDeMorte(ordemMorteAtual++);
            rankingFinal.adicionar(jogador);
            excluirSeJogador(jogador); // <<< remove da conta
            System.out.println("\nDerrota! O jogador caiu em batalha.");
        }
    }

    private void exibirRankingFinal() {
        System.out.println("\n=====  RANKING FINAL  =====");

        PilhaArena temp = new PilhaArena();
        while (!rankingFinal.estaVazia()) temp.adicionar(rankingFinal.remover());

        int pos = 1;
        while (!temp.estaVazia()) {
            Personagem p = temp.remover();
            System.out.printf("%dº - %s (Lvl %d)\n", pos++, p.getNome(), p.getNivel());
            System.out.println("Ordem de morte: " + p.getOrdemDeMorte());
            System.out.println("Dano causado: " + p.getDanoCausado());
            System.out.println("Dano recebido: " + p.getDanoRecebido());
            rankingFinal.adicionar(p);
        }

        System.out.println("=================================\n");
    }

    // ====== remove o personagem do dono quando for derrotado ======
    private void excluirSeJogador(Personagem p) {
        if (p == null || p instanceof Monstro) return;
        Jogador dono = p.getDono();
        if (dono != null && dono.personagens != null) {
            dono.personagens.remover(p.getId()); // usa teu ListaPersonagem.remover(int id)
            if (dono.getPersonagemSelecionado() == p) {
                dono.setPersonagemSelecionado(null);
            }
            System.out.println("Personagem " + p.getNome() + " removido da conta de " + dono.getNome() + ".");
        }
    }
}
