public class ListaJogador {
    private NodeJogador head;
    private int size;

    public void add(Jogador j) {
        NodeJogador n = new NodeJogador(j);
        if (head == null) head = n;
        else {
            NodeJogador c = head;
            while (c.next != null) c = c.next;
            c.next = n;
        }
        size++;
    }

    public Jogador get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        NodeJogador c = head;
        for (int i = 0; i < index; i++) c = c.next;
        return c.value;
    }

    public int size() {
        return size;
    }
}
