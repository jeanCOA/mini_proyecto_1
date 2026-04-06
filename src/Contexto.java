public class Contexto {
    
    private Jugador jugActivo;
    private Jugador oponente;
    private CampoBatalla campo;
 
    public Contexto(Jugador jugActivo, Jugador oponente, CampoBatalla campo) {
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
 
    public CampoBatalla getCampo() {
        return campo;
    }
 
    public int getTurno() {
        return campo.getTurnoActual();
    }
    
}