public class App {
    public static void main(String[] args) {
        
        Jugador jugador1 = new Jugador("Jugador 1", new Mazo(true));
        Jugador jugador2 = new Jugador("Jugador 2", new Mazo(true));
 
        CampoBatalla campo = new CampoBatalla(jugador1, jugador2);
 
        campo.iniciarDuelo();
    }
}
 
