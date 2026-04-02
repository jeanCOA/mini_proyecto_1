public class LlamadaDelAbismo extends CartaMagica {
    public LlamadaDelAbismo() {
        super("Llamada del Abismo", "Robas 1 carta, pero pierdes 500 LP.");
    }

    @Override
    public void activar(Contexto contexto) {
        Jugador activo = contexto.getJugadorActivo();
        // Costo
        activo.setLp(activo.getLp() - 500);
        // Beneficio
        activo.robar();
        System.out.println("Pagaste 500 LP por una carta.");
    }

    @Override
    public void aplicarEfecto(Jugador jugador) {
        jugador.setLp(jugador.getLp() - 500);
        jugador.robar();
    }
}