public class Arena {
    private String tipo;
    private final java.util.Scanner sc;
    private final int CURA_PADRAO = 30;

    private PilhaArena rankingFinal = new PilhaArena();
    private int ordemMorteAtual = 1;
    private NodePersonagem partHead;

    public Arena(String tipo, java.util.Scanner sc) {
        this.tipo = tipo.toUpperCase();
        this.sc = sc;
    }

    public void iniciarBatalha(FilaArena fila) {
        if (fila == null || fila.estaVazia()) { System.out.println("Nenhum participante foi adicionado à arena."); return; }

        rankingFinal.limpar();
        ordemMorteAtual = 1;
        partHead = null;

        FilaArena copia = new FilaArena();
        int count = 0, jog = 0, mon = 0;
        while (!fila.estaVazia()) {
            Personagem p = fila.remover();
            copia.adicionar(p);
            appendPart(p);
            count++;
            if (p instanceof Monstro) mon++; else jog++;
        }
        while (!copia.estaVazia()) fila.adicionar(copia.remover());

        if (tipo.equals("PVP") && count != 2) { System.out.println("Combate PVP requer exatamente 2 personagens!"); return; }
        if (tipo.equals("PVE") && (jog < 1 || mon < 1)) { System.out.println("PVE requer ao menos 1 jogador e 1 monstro!"); return; }

        if (tipo.equals("PVP")) combatePVP(fila);
        else combatePVE(fila);

        exibirRankingPorDano();
        pause(); clear();
    }

    private void combatePVP(FilaArena fila) {
        Personagem p1 = fila.remover();
        Personagem p2 = fila.remover();
        p1.resetarCurasParaNovaBatalha(); 
        p2.resetarCurasParaNovaBatalha();

        System.out.println("\nInício do combate PVP: " + p1.getNome() + " VS " + p2.getNome());
        int turno = 1;
        while (p1.estaVivo() && p2.estaVivo()) {
            System.out.println("\n--- Turno " + turno + " ---");
            System.out.println(p1.getNome() + " - HP: " + p1.getVidaAtual() + "/" + p1.getVidaMaxima() + " | Curas: " + p1.getCargasCura());
            System.out.println(p2.getNome() + " - HP: " + p2.getVidaAtual() + "/" + p2.getVidaMaxima() + " | Curas: " + p2.getCargasCura());

            System.out.println("\n" + p1.getNome() + ", 1) Atacar  2) Curar (" + CURA_PADRAO + ")");
            String acao = sc.nextLine().trim();
            if (acao.equals("2")) {
                if (p1.consumirCargaCura()) { p1.curar(CURA_PADRAO); System.out.println(p1.getNome() + " curou. (" + p1.getCargasCura() + " cargas)"); }
                else System.out.println("Sem cargas de cura.");
            } else {
                int vidaAntes = p2.getVidaAtual();
                p1.atacar(p2);
                p2.adicionarDanoRecebido(Math.max(0, vidaAntes - p2.getVidaAtual()));
            }
            if (!p2.estaVivo()) { p2.setOrdemDeMorte(ordemMorteAtual++); rankingFinal.adicionar(p2); excluirSeJogador(p2); System.out.println(p2.getNome() + " foi derrotado!"); break; }

            System.out.println("\n" + p2.getNome() + ", 1) Atacar  2) Curar (" + CURA_PADRAO + ")");
            acao = sc.nextLine().trim();
            if (acao.equals("2")) {
                if (p2.consumirCargaCura()) { p2.curar(CURA_PADRAO); System.out.println(p2.getNome() + " curou. (" + p2.getCargasCura() + " cargas)"); }
                else System.out.println("Sem cargas de cura.");
            } else {
                int vidaAntes = p1.getVidaAtual();
                p2.atacar(p1);
                p1.adicionarDanoRecebido(Math.max(0, vidaAntes - p1.getVidaAtual()));
            }
            if (!p1.estaVivo()) { p1.setOrdemDeMorte(ordemMorteAtual++); rankingFinal.adicionar(p1); excluirSeJogador(p1); System.out.println(p1.getNome() + " foi derrotado!"); break; }

            turno++;
        }

        System.out.println("\nCombate PVP finalizado!");
        if (p1.estaVivo() && !p2.estaVivo()) { p1.setOrdemDeMorte(ordemMorteAtual++); rankingFinal.adicionar(p1); if (p1.getDono() != null) p1.getDono().adicionarMoedas(100); }
        else if (p2.estaVivo() && !p1.estaVivo()) { p2.setOrdemDeMorte(ordemMorteAtual++); rankingFinal.adicionar(p2); if (p2.getDono() != null) p2.getDono().adicionarMoedas(100); }
    }

    private void combatePVE(FilaArena fila) {
        Personagem jogador = null;
        ListaMonstro monstros = new ListaMonstro();

        while (!fila.estaVazia()) {
            Personagem p = fila.remover();
            if (p instanceof Monstro) monstros.addLast((Monstro) p);
            else jogador = p;
        }
        if (jogador == null || monstros.isEmpty()) { System.out.println("Erro: faltam personagens válidos para o PVE."); return; }

        jogador.resetarCurasParaNovaBatalha();
        System.out.println("\nInício do combate PVE!");
        int turno = 1;

        while (jogador.estaVivo() && !monstros.isEmpty()) {
            System.out.println("\n--- Turno " + turno + " ---");
            System.out.println(jogador.getNome() + " (HP: " + jogador.getVidaAtual() + " | Curas: " + jogador.getCargasCura() + ")");
            for (int i = 0; i < monstros.size(); i++) {
                Monstro m = monstros.get(i);
                System.out.println(" " + (i + 1) + ") " + m.getNome() + " - HP: " + m.getVidaAtual());
            }

            System.out.println("\n1) Atacar  2) Curar");
            String acao = sc.nextLine().trim();

            if (acao.equals("2")) {
                if (jogador.consumirCargaCura()) {
                    jogador.curar(CURA_PADRAO);
                    System.out.println("Curado. (" + jogador.getCargasCura() + " cargas)");
                } else {
                    System.out.println("Sem cargas. Recarregue no menu.");
                }
            } else {
                System.out.print("Alvo (1-" + monstros.size() + "): ");
                int idx; try { idx = Integer.parseInt(sc.nextLine().trim()) - 1; } catch (Exception e) { idx = -1; }
                if (idx >= 0 && idx < monstros.size()) {
                    Monstro alvo = monstros.get(idx);
                    int vidaAntes = alvo.getVidaAtual();
                    jogador.atacar(alvo);
                    alvo.adicionarDanoRecebido(Math.max(0, vidaAntes - alvo.getVidaAtual()));
                    if (!alvo.estaVivo()) { 
                        alvo.setOrdemDeMorte(ordemMorteAtual++); 
                        rankingFinal.adicionar(alvo); 
                        System.out.println(alvo.getNome() + " foi derrotado!"); 
                        monstros.remove(alvo); 
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
                jogador.adicionarDanoRecebido(Math.max(0, vidaAntes - jogador.getVidaAtual()));
            }
            turno++;
        }

        if (jogador.estaVivo()) {
            jogador.setOrdemDeMorte(ordemMorteAtual++);
            rankingFinal.adicionar(jogador);
            System.out.println("\nVitória! Todos os monstros foram derrotados!");

            int recompensaTotal = 0, xpTotal = 0;
            PilhaArena temp = new PilhaArena();
            while (!rankingFinal.estaVazia()) {
                Personagem p = rankingFinal.remover();
                if (p instanceof Monstro) {
                    recompensaTotal += ((Monstro) p).getRecompensaMoedas();
                    xpTotal += ((Monstro) p).getRecompensaExp();
                }
                temp.adicionar(p);
            }
            while (!temp.estaVazia()) rankingFinal.adicionar(temp.remover());

            recompensaTotal = (int)Math.round(recompensaTotal * 1.5);
            jogador.ganharExperiencia(xpTotal);
            if (jogador.getDono() != null) jogador.getDono().adicionarMoedas(recompensaTotal);
        } else {
            jogador.setOrdemDeMorte(ordemMorteAtual++);
            rankingFinal.adicionar(jogador);
            excluirSeJogador(jogador);
            System.out.println("\nDerrota! O jogador caiu em batalha.");
        }
    }

    private void exibirRankingPorDano() {
        NodePersonagem tempHead = null;
        for (NodePersonagem c = partHead; c != null; c = c.next) {
            NodePersonagem n = new NodePersonagem(c.personagem);
            if (tempHead == null) tempHead = n;
            else {
                NodePersonagem t = tempHead;
                while (t.next != null) t = t.next;
                t.next = n;
            }
        }

        PilhaArena pilhaOrdenada = new PilhaArena();
        while (tempHead != null) {
            NodePersonagem it = tempHead, prev = null;
            NodePersonagem minNode = tempHead, prevMin = null;
            while (it != null) {
                if (it.personagem.getDanoCausado() < minNode.personagem.getDanoCausado()) {
                    minNode = it; prevMin = prev;
                }
                prev = it; it = it.next;
            }
            if (prevMin == null) tempHead = minNode.next;
            else prevMin.next = minNode.next;
            pilhaOrdenada.adicionar(minNode.personagem);
        }
    
        System.out.println("\n=====  RANKING (por Dano Causado)  =====");
        int pos = 1;
        while (!pilhaOrdenada.estaVazia()) {
            Personagem p = pilhaOrdenada.remover();
            System.out.printf("%dº - %s | Dano: %d | Nível: %d | HP final: %d/%d\n",
                    pos++, p.getNome(), p.getDanoCausado(), p.getNivel(), p.getVidaAtual(), p.getVidaMaxima());
        }
        System.out.println("========================================\n");
    }
    

    private void appendPart(Personagem p) {
        NodePersonagem n = new NodePersonagem(p);
        if (partHead == null) partHead = n;
        else {
            NodePersonagem c = partHead;
            while (c.next != null) c = c.next;
            c.next = n;
        }
    }

    private void pause() { System.out.print("\nPressione ENTER para voltar ao menu..."); sc.nextLine(); }
    private void clear() { System.out.print("\033[H\033[2J"); System.out.flush(); }

    private void excluirSeJogador(Personagem p) {
        if (p == null || p instanceof Monstro) return;
        Jogador dono = p.getDono();
        if (dono != null && dono.personagens != null) {
            dono.personagens.remover(p.getId());
            if (dono.getPersonagemSelecionado() == p) dono.setPersonagemSelecionado(null);
            System.out.println("Personagem " + p.getNome() + " removido da conta de " + dono.getNome() + ".");
        }
    }
}
