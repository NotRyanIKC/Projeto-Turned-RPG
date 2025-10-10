import java.util.Random;

public class Monstro extends Personagem {
    private int danoBase;
    private int recompensaExp;
    private int recompensaMoedas;
    private Random random = new Random();

    public Monstro(int id, String nome, int nivel, int vidaMaxima, int danoBase, int recompensaExp, int recompensaMoedas) {
        super(id, nome, nivel, vidaMaxima, danoBase, 0);
        this.danoBase = danoBase;
        this.recompensaExp = recompensaExp;
        this.recompensaMoedas = recompensaMoedas;
    }

    public int getRecompensaExp() { return recompensaExp; }
    public int getRecompensaMoedas() { return recompensaMoedas; }

    @Override
    public void atacar(Personagem alvo) {
        if (!estaVivo()) return;
        int dado = random.nextInt(6) + 1;
        int danoTotal = danoBase + dado;
        System.out.println(getNome() + " rola um dado (" + dado + ") e ataca " + alvo.getNome() + " causando " + danoTotal + " de dano!");
        int vidaAntes = alvo.getVidaAtual();
        alvo.receberDano(danoTotal);
        int aplicado = Math.max(0, vidaAntes - alvo.getVidaAtual());
        if (aplicado > 0) adicionarDanoCausado(aplicado);
    }
}
