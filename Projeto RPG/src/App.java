import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);     // um único Scanner para o app todo
        Menu menu = new Menu(sc);                // passa o mesmo scanner pro Menu
        menu.menuPrincipal();
        sc.close();
    }
}
