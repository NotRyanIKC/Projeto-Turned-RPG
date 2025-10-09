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

    public int getDanoBase() { return danoBase; }
    public void setDanoBase(int danoBase) { this.danoBase = danoBase; }
    public int getRecompensaExp() { return recompensaExp; }
    public void setRecompensaExp(int recompensaExp) { this.recompensaExp = recompensaExp; }
    public int getRecompensaMoedas() { return recompensaMoedas; }
    public void setRecompensaMoedas(int recompensaMoedas) { this.recompensaMoedas = recompensaMoedas; }

    @Override
    public void atacar(Personagem alvo) {
        if (!this.estaVivo()) {
            System.out.println(getNome() + " está morto e não pode atacar!");
            return;
        }
        int dado = random.nextInt(6) + 1;
        int danoTotal = danoBase + dado;
        System.out.println(getNome() + " rola um dado (" + dado + ") e ataca " + alvo.getNome() +
                " causando " + danoTotal + " de dano!");
        int vidaAntes = alvo.getVidaAtual();
        alvo.receberDano(danoTotal);
        int aplicado = Math.max(0, vidaAntes - alvo.getVidaAtual());
        if (aplicado > 0) this.adicionarDanoCausado(aplicado);
    }

    public int getRecompensaMoedasTotal() { return recompensaMoedas; }
    public int getRecompensaExpTotal() { return recompensaExp; }
}
