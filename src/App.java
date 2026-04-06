import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════╗");
        System.out.println("║   ¡DUELO DE YU-GI-OH!        ║");
        System.out.println("╚══════════════════════════════╝");

        System.out.print("Ingresa el nombre del Jugador 1: ");
        String nombre1 = scanner.nextLine().trim();
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