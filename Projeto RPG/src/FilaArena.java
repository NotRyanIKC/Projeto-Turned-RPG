public class FilaArena {
    private NodePersonagem head;
    private NodePersonagem tail;
    private int size;

    public void adicionar(Personagem p) {
        NodePersonagem n = new NodePersonagem(p);
        if (tail == null) { head = n; tail = n; }
        else { tail.next = n; tail = n; }
        size++;
    }

    public Personagem remover() {
        if (head == null) return null;
        Personagem v = head.personagem;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return v;
    }

    public boolean estaVazia() { return head == null; }
    public int tamanho() { return size; }
}
