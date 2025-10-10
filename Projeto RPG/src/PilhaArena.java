public class PilhaArena {
    private NodePersonagem topo;
    private int size;

    public void adicionar(Personagem p) {
        NodePersonagem n = new NodePersonagem(p);
        n.next = topo;
        topo = n;
        size++;
    }

    public Personagem remover() {
        if (topo == null) return null;
        Personagem p = topo.personagem;
        topo = topo.next;
        size--;
        return p;
    }

    public boolean estaVazia() { return topo == null; }
    public void limpar() { topo = null; size = 0; }
    public int tamanho() { return size; }
}
