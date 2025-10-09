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

    // XP acumulado
    private int experiencia;

    public Personagem(int id, String nome, int nivel, int vidaMaxima, int ataque, int defesa, Jogador dono) {
        this.id = id;
        this.nome = nome;
        this.nivel = nivel;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.ataque = ataque;
        this.defesa = defesa;
        this.dono = dono;
        this.danoCausado = 0;
        this.danoRecebido = 0;
        this.ordemDeMorte = 0;
        this.experiencia = 0;
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
    public int getDanoCausado() { return danoCausado; }
    public int getDanoRecebido() { return danoRecebido; }
    public int getOrdemDeMorte() { return ordemDeMorte; }
    public void setOrdemDeMorte(int ordem) { this.ordemDeMorte = ordem; }
    public void setVidaAtual(int valor) {
        if (valor < 0) valor = 0;
        if (valor > vidaMaxima) valor = vidaMaxima;
        this.vidaAtual = valor;
    }

    // XP helpers
    public int getExperiencia() { return experiencia; }
    public int getXpRestanteParaUp() { return 100 - (experiencia % 100); }

    public boolean estaVivo() { return vidaAtual > 0; }

    public void curar(int valor) {
        if (valor <= 0 || !estaVivo()) return;
        vidaAtual += valor;
        if (vidaAtual > vidaMaxima) vidaAtual = vidaMaxima;
    }

    public void adicionarDanoRecebido(int valor) {
        if (valor <= 0) return;
        danoRecebido += valor;
    }

    public void adicionarDanoCausado(int valor) {
        if (valor <= 0) return;
        danoCausado += valor;
    }

    protected void receberDano(int valor) {
        int real = valor - defesa;
        if (real < 0) real = 0;
        if (real > vidaAtual) real = vidaAtual;
        vidaAtual -= real;
        danoRecebido += real;
    }

    public void atacar(Personagem alvo) {
        if (alvo == null || !estaVivo()) return;
        int danoBase = calcularDanoBaseContra(alvo);
        int vidaAntes = alvo.getVidaAtual();
        alvo.receberDano(danoBase);
        int aplicado = vidaAntes - alvo.getVidaAtual();
        if (aplicado > 0) danoCausado += aplicado;
    }

    protected int calcularDanoBase() {
        return ataque + (nivel > 1 ? (nivel - 1) : 0);
    }

    protected int calcularDanoBaseContra(Personagem alvo) {
        return calcularDanoBase();
    }

    // UP de nível: +Ataque, +Vida Máxima e cura total; com aviso
    public void ganharExperiencia(int xp) {
        if (xp <= 0) return;
        experiencia += xp;

        boolean upou = false;
        int ups = 0;

        while (experiencia >= 100) {
            experiencia -= 100;
            ups++;
            upou = true;

            nivel += 1;
            ataque += 1;      // +dano
            vidaMaxima += 5;  // +vida máxima
            vidaAtual = vidaMaxima; // cura total ao upar
        }

        if (upou) {
            System.out.println("Parabéns! " + nome + " subiu para o nível " + nivel +
                    " (+" + (ups) + " nível" + (ups > 1 ? "s" : "") + ").");
            System.out.println("Bônus: +"+ (1*ups) +" Ataque, +" + (5*ups) + " Vida Máxima. Vida totalmente recuperada!");
        }
    }
}
