public class NodeHabilidade {
    Habilidade habilidade;
    NodeHabilidade next;

    public NodeHabilidade(Habilidade habilidade) {
        this.habilidade = habilidade;
        this.next = null;
    }
}
