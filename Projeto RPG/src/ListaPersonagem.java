public class ListaPersonagem {
    NodePersonagem head;
    private int size;

    public void adicionar(Personagem personagem) {
        NodePersonagem n = new NodePersonagem(personagem);
        if (head == null) head = n;
        else {
            NodePersonagem c = head;
            while (c.next != null) c = c.next;
            c.next = n;
        }
        size++;
    }

    public void remover(int id) {
        if (head == null) return;
        if (head.personagem.getId() == id) { head = head.next; size--; return; }
        NodePersonagem c = head;
        while (c.next != null && c.next.personagem.getId() != id) c = c.next;
        if (c.next != null) { c.next = c.next.next; size--; }
    }

    public void listar() {
        NodePersonagem c = head;
        while (c != null) {
            Personagem p = c.personagem;
            int xp = p.getExperiencia();
            int falta = 100 - (xp % 100);
            System.out.println(
                "ID: " + p.getId() +
                " | Nome: " + p.getNome() +
                " | Nível: " + p.getNivel() +
                " | Vida: " + p.getVidaAtual() + "/" + p.getVidaMaxima() +
                " | XP: " + xp + "/100 (falta " + falta + ")" +
                " | Curas: " + p.getCargasCura()
            );
            c = c.next;
        }
    }

    public int getSize() { return size; }

    public Personagem buscarPorId(int id) {
        NodePersonagem c = head;
        while (c != null) {
            if (c.personagem.getId() == id) return c.personagem;
            c = c.next;
        }
        return null;
    }
}
