public class Contexto {
    private Jugador jugadorActivo;
    private CampoDeBatalla campo;

    public Contexto(CampoDeBatalla campo, Jugador jugadorActivo) {
        this.campo = campo;
        this.jugadorActivo = jugadorActivo;
    }

    public Jugador getJugadorActivo() {
        return jugadorActivo;
    }

    public void setJugadorActivo(Jugador jugadorActivo) {
        this.jugadorActivo = jugadorActivo;
    }

    public CampoDeBatalla getCampo() {
        return campo;
    }

    public void setCampo(CampoDeBatalla campo) {
        this.campo = campo;
    }

    

    
}
