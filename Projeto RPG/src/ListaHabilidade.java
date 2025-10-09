public class ListaHabilidade {
    private NodeHabilidade head;
    private int size;

    public ListaHabilidade() {
        this.head = null;
        this.size = 0;
    }

    public void adicionar(Habilidade habilidade) {
        NodeHabilidade newNode = new NodeHabilidade(habilidade);
        if (head == null) {
            head = newNode;
        } else {
            NodeHabilidade current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public void remover(int id) {
        if (head == null) return;

        if (head.habilidade.getId() == id) {
            head = head.next;
            size--;
            return;
        }

        NodeHabilidade current = head;
        while (current.next != null && current.next.habilidade.getId() != id) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            size--;
        }
    }

    public Habilidade buscar(int id) {
        NodeHabilidade current = head;
        while (current != null) {
            if (current.habilidade.getId() == id) {
                return current.habilidade;
            }
            current = current.next;
        }
        return null;
    }

    public int getSize() {
        return size;
    }
}
