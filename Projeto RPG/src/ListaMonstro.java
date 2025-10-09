public class ListaMonstro {
    private NodeMonstro head;
    private int size;

    public void addLast(Monstro m) {
        NodeMonstro n = new NodeMonstro(m);
        if (head == null) head = n;
        else {
            NodeMonstro cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = n;
        }
        size++;
    }

    public Monstro get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        NodeMonstro cur = head;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur.value;
    }

    public boolean remove(Monstro m) {
        if (head == null) return false;
        if (head.value == m) { head = head.next; size--; return true; }
        NodeMonstro cur = head;
        while (cur.next != null) {
            if (cur.next.value == m) {
                cur.next = cur.next.next;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
}
