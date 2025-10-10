public abstract class Personagem {
    private int id;
    private String nome;
    private int vidaMaxima;
    private int vidaAtual;
    private int nivel;
    private int ataque;
    private int defesa;
    private int danoCausado;
    private int danoRecebido;
    private int ordemDeMorte;
    private Jogador dono;
    private int experiencia;
    private int cargasCura = 3;

    public Personagem(int id, String nome, int nivel, int vidaMaxima, int ataque, int defesa, Jogador dono) {
        this.id = id;
        this.nome = nome;
        this.nivel = nivel;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.ataque = ataque;
        this.defesa = defesa;
        this.dono = dono;
    }
    public Personagem(int id, String nome, int nivel, int vidaMaxima, int ataque, int defesa) {
        this(id, nome, nivel, vidaMaxima, ataque, defesa, null);
    }
    public Personagem(String nome, int vidaMaxima, int ataque, int defesa, int nivel, Jogador dono) {
        this(0, nome, nivel, vidaMaxima, ataque, defesa, dono);
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getVidaAtual() { return vidaAtual; }
    public int getNivel() { return nivel; }
    public Jogador getDono() { return dono; }
    public void setDono(Jogador dono) { this.dono = dono; }
    public int getDanoCausado() { return danoCausado; }
    public int getDanoRecebido() { return danoRecebido; }
    public int getOrdemDeMorte() { return ordemDeMorte; }
    public void setOrdemDeMorte(int ordem) { this.ordemDeMorte = ordem; }
    public void setVidaAtual(int v) { this.vidaAtual = Math.max(0, Math.min(v, vidaMaxima)); }

    public int getExperiencia() { return experiencia; }
    public int getXpRestanteParaUp() { return 100 - (experiencia % 100); }

    public int getCargasCura() { return cargasCura; }
    public void adicionarCargasCura(int qtd) { if (qtd > 0) cargasCura += qtd; }
    public boolean consumirCargaCura() { if (cargasCura > 0) { cargasCura--; return true; } return false; }
    public void resetarCurasParaNovaBatalha() { if (cargasCura < 1) cargasCura = 1; }

    public boolean estaVivo() { return vidaAtual > 0; }

    public void curar(int valor) {
        if (valor <= 0 || !estaVivo()) return;
        vidaAtual = Math.min(vidaAtual + valor, vidaMaxima);
    }
    public void adicionarDanoRecebido(int v) { if (v > 0) danoRecebido += v; }
    public void adicionarDanoCausado(int v) { if (v > 0) danoCausado += v; }

    protected void receberDano(int valor) {
        int real = Math.max(0, valor - defesa);
        real = Math.min(real, vidaAtual);
        vidaAtual -= real;
        danoRecebido += real;
    }

    public void atacar(Personagem alvo) {
        if (alvo == null || !estaVivo()) return;
        int vidaAntes = alvo.getVidaAtual();
        alvo.receberDano(calcularDanoBaseContra(alvo));
        int aplicado = vidaAntes - alvo.getVidaAtual();
        if (aplicado > 0) danoCausado += aplicado;
    }

    protected int calcularDanoBase() { return ataque + Math.max(0, nivel - 1); }
    protected int calcularDanoBaseContra(Personagem alvo) { return calcularDanoBase(); }

    public void ganharExperiencia(int xp) {
        if (xp <= 0) return;
        experiencia += xp;
        int ups = 0;
        while (experiencia >= 100) {
            experiencia -= 100;
            ups++;
            nivel += 1;
            ataque += 1;
            vidaMaxima += 5;
            vidaAtual = vidaMaxima;
        }
        if (ups > 0) {
            System.out.println("Parabéns! " + nome + " subiu para o nível " + nivel +
                               " (+" + ups + " nível" + (ups > 1 ? "s" : "") + ").");
            System.out.println("Bônus: +" + (1*ups) + " Ataque, +" + (5*ups) + " Vida Máxima. Vida totalmente recuperada!");
        }
    }
}
