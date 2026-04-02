public class PodOfGreed extends CartaMagica{

    public PodOfGreed(){

        super("Olla de la codicia", "Roba 2 cartas de tu mazo inmediatamente. ");

    }

    public void Activar(Contexto contexto){

        System.out.println("EFECTO DE OLLA DE LA CODICIA ACTIVADO !!!!!!!");

        Jugador elQueUsa = contexto.getJugadorActivo();

        elQueUsa.robarCarta();
        elQueUsa.robarCarta();

    }

}