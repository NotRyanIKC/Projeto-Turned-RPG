public class ListaPersonagem {
    private NodePersonagem head;
    private int size;

    public ListaPersonagem() {
        this.head = null;
        this.size = 0;
    }

    public void adicionar(Personagem personagem) {
        NodePersonagem newNode = new NodePersonagem(personagem);
        if (head == null) {
            head = newNode;
        } else {
            NodePersonagem current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public void remover(int id) {
        if (head == null) return;

        if (head.personagem.getId() == id) {
            head = head.next;
            size--;
            return;
        }

        NodePersonagem current = head;
        while (current.next != null && current.next.personagem.getId() != id) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            size--;
        }
    }

    public void listar() {
        NodePersonagem current = head;
        while (current != null) {
            Personagem p = current.personagem;
            int xp = p.getExperiencia();
            int falta = 100 - (xp % 100);
            System.out.println(
                "ID: " + p.getId() +
                " | Nome: " + p.getNome() +
                " | Nível: " + p.getNivel() +
                " | Vida: " + p.getVidaAtual() + "/" + p.getVidaMaxima() +
                " | XP: " + xp + "/100 (falta " + falta + ")"
            );
            current = current.next;
        }
    }
    

    public int getSize() {
        return size;
    }

    public Personagem buscarPorId(int id) {
        NodePersonagem current = head;
        while (current != null) {
            if (current.personagem.getId() == id) {
                return current.personagem;
            }
            current = current.next;
        }
        return null;
    }
}
