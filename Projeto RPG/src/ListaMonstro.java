public class ListaMonstro {
    private NodeMonstro head;
    private int size;

    public void addLast(Monstro m) {
        NodeMonstro n = new NodeMonstro(m);
        if (head == null) head = n;
        else {
            NodeMonstro c = head;
            while (c.next != null) c = c.next;
            c.next = n;
        }
        size++;
    }

    public Monstro get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        NodeMonstro c = head;
        for (int i = 0; i < index; i++) c = c.next;
        return c.value;
    }

    public boolean remove(Monstro m) {
        if (head == null) return false;
        if (head.value == m) { head = head.next; size--; return true; }
        NodeMonstro c = head;
        while (c.next != null) {
            if (c.next.value == m) { c.next = c.next.next; size--; return true; }
            c = c.next;
        }
        return false;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
}
