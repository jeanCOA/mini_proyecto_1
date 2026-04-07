import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Crea el objeto 'scanner' pa poder capturar lo que el usuario escriba en la consola
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════╗");
        System.out.println("║   ¡DUELO DE YU-GI-OH!        ║");
        System.out.println("╚══════════════════════════════╝");

        System.out.print("Ingresa el nombre del Jugador 1: ");
        // .nextLine() lee el texto, .trim() quita espacios vacios al inicio o final
        String nombre1 = scanner.nextLine().trim();
        // Si el usuario no escribio nada, se le asigna un nombre por defecto
        if (nombre1.isEmpty()) nombre1 = "Jugador 1";

        System.out.print("Ingresa el nombre del Jugador 2: ");
        String nombre2 = scanner.nextLine().trim();
        if (nombre2.isEmpty()) nombre2 = "Jugador 2";

        Jugador jugador1 = new Jugador(nombre1);
        Jugador jugador2 = new Jugador(nombre2);

        CampoBatalla campo = new CampoBatalla(jugador1, jugador2);
        campo.iniciarDuelo();
    }
}