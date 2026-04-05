public class App {
    public static void main(String[] args) {
        
        Jugador jugador1 = new Jugador("Jugador 1");
        Jugador jugador2 = new Jugador("Jugador 2");
        
        CampoBatalla campo = new CampoBatalla(jugador1, jugador2);
 
        campo.iniciarDuelo();
    }
}
 
