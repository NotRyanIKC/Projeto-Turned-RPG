public class Habilidade {
    private int id;
    private String nome;
    private int dano;
    private int custoMana;
    private String efeito; // Ex: "congelar", "queimar", curar etc.
    

    

    public Habilidade(int id, String nome, int dano, int custoMana, String efeito) {
        this.id = id;
        this.nome = nome;
        this.dano = dano;
        this.custoMana = custoMana;
        this.efeito = efeito;
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

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getCustoMana() {
        return custoMana;
    }

    public void setCustoMana(int custoMana) {
        this.custoMana = custoMana;
    }

    public String getEfeito() {
        return efeito;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    
}
