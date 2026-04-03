
public class Contexto {
    
    private Jugador jugActivo;
    private Jugador oponente;
    private CampoDeBatalla campo;

    public Contexto(Jugador jugActivo, Jugador oponente, CampoDeBatalla campo) {
        this.jugActivo = jugActivo;
        this.oponente = oponente;
        this.campo = campo;
    }

    public Jugador getJugadorActivo() {
        return jugActivo;
    }

    public Jugador getOponente() {
        return oponente;
    }

    public CampoDeBatalla getCampo() {
        return campo;
    }

    public int getTurno() {
        return campo.getTurnoActual();
    }
    
}