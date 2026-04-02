public class CuraMilagrosa extends CartaMagica implements Activable {

    public CuraMilagrosa (){
        super("Cura milagrosa. ", "Aumenta +1000 LP a tu contador de vida. ");
    }

    @Override
    public void Activar(Object contexto) {

        Jugador activo = contexto.getJugadorActivo();
        activo.setLp(activo.getLp() + 1000);
        System.out.println(activo.getNombre() + "ahora tiene" + activo.getLp() + " LP.");

    }

    @Override
    public void aplicarEfecto(Jugador jugador) {
        jugador.setLp(jugador.getLp() + 1000)
    }

    


    
}
