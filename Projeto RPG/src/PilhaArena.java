public class PilhaArena {
    private NodePersonagem top;
    private int size;

    public void adicionar(Personagem p) {
        NodePersonagem n = new NodePersonagem(p);
        n.next = top;
        top = n;
        size++;
    }

    public Personagem remover() {
        if (top == null) return null;
        Personagem v = top.personagem;
        top = top.next;
        size--;
        return v;
    }

    public boolean estaVazia() { return top == null; }
    public int tamanho() { return size; }
    public void limpar() { top = null; size = 0; }
}
