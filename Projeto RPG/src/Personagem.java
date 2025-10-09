public class Personagem {
    private int id;
    private String nome;
    private int nivel;
    private int vidaMaxima;
    private int vidaAtual;
    private int experiencia = 0 ;


    public Personagem(int id, String nome, int nivel, int vidaMaxima, int vidaAtual, int experiencia) {
        this.id = id;
        this.nome = nome;
        this.nivel = nivel;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaAtual;
        this.experiencia = experiencia;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }   

    public int getVidaAtual() {
        return vidaAtual;
    }

    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void receberDano(int dano) {
        this.vidaAtual -= dano;
        if (this.vidaAtual < 0) {
            this.vidaAtual = 0;
        }
    }

    public void curar(int valor) {
    this.vidaAtual = Math.min(this.vidaMaxima, this.vidaAtual + valor);
}

    public boolean estaVivo() {
        return this.vidaAtual > 0;
    }

    public void ganharExperiencia(int exp) {
    this.experiencia += exp;
    System.out.println(nome + " ganhou " + exp + " de experiência! (Total: " + experiencia + ")");

    // experiência necessária pra subir de nível
    int experienciaNecessaria = nivel * 50; // Ex: nível 1 → 100 exp, nível 2 → 200 exp, etc.

    // Verifica se o personagem tem experiência suficiente pra subir de nível
    while (this.experiencia >= experienciaNecessaria) {
        this.experiencia -= experienciaNecessaria;
        subirNivel();
        experienciaNecessaria = nivel * 50; 
        }
    }


    public void atacar(Personagem alvo) {
    int dano = (int)(Math.random() * 20 + 10); 
    System.out.println(this.nome + " atacou " + alvo.getNome() + " e causou " + dano + " de dano!");
    alvo.receberDano(dano);
    }

    public void subirNivel() {
    this.nivel++;
    this.vidaMaxima += 10; 
    this.vidaAtual = this.vidaMaxima; 
    System.out.println( nome + " subiu para o nível " + nivel + "!");
    System.out.println("Vida máxima aumentou para " + vidaMaxima + " e vida foi totalmente restaurada!");
    }


    

}
