public class Fisura extends CartaMagica implements Activable {

    public Fisura(){

        super("Fisura. ", "Destruye el montruo con menor ATK del oponente. ");

    }

    @Override
    public void aplicarEfecto(Jugador jugador) {
        contexto.getCampo().destruirMonstruoMasDebilOponente(contexto.getJugadorActivo());
    }

    @Override
    public void Activar(Object contexto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    
}
