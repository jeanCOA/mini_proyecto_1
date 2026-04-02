public class Contexto {
    private Jugador jugadorActivo;
    private CampoDeBatalla campo;

    public Contexto(CampoDeBatalla campo, Jugador jugadorActivo) {
        this.campo = campo;
        this.jugadorActivo = jugadorActivo;
    }

    // Métodos que pide el UML
    public Jugador getJugadorActivo() {
        return jugadorActivo;
    }

    public void setJugadorActivo(Jugador jugadorActivo) {
        this.jugadorActivo = jugadorActivo;
    }

    public CampoDeBatalla getCampo() {
        return campo;
    }

    // Le pregunta al campo qué turno es
    public int getTurnoActual() {
        return campo.getTurnoActual();
    }
}