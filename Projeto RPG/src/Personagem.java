public class Personagem {
    private int id;
    private String nome;
    private int nivel;
    private int vidaMaxima;
    private int vidaAtual;
    private int experiencia = 0 ;
    // Lista de Habilidades e Inventário podem ser adicionados aqui

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

    // public void usarHabilidade(Int IdHabilidade, Personagem alvo){}
    // public void usarItem(Int IdItem){}

    public void curar(int cura) {
        this.vidaAtual += cura;
        if (this.vidaAtual > this.vidaMaxima) {
            this.vidaAtual = this.vidaMaxima;
        }
    }

    public boolean estaVivo() {
        return this.vidaAtual > 0;
    }

    public void ganharExperiencia(int exp) {
        this.experiencia += exp;
        // Lógica para subir de nível pode ser adicionada aqui
    }

    public void subirNivel() {
        this.nivel++;
        this.vidaMaxima += 10; // Exemplo de aumento de vida máxima ao subir de nível
        this.vidaAtual = this.vidaMaxima; // Cura completa ao subir de nível
        this.experiencia = 0; // Reseta experiência ao subir de nível
    }

    

}
