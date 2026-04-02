public class LlamadaDelAbismo extends CartaMagica {
    private int costo = 500;

    public LlamadaDelAbismo() {
        super("Llamada del Abismo", 0, "Robas 1 carta, pero pierdes 500 LP.");
    }

    @Override
    public void activar(Contexto contexto) {
<<<<<<< HEAD
        Jugador j = contexto.getJugadorActivo();
        System.out.println(">>> Llamada del Abismo: Pagando 500 LP para robar...");
        j.setLp(j.getLp() - costo);
        j.robar();
=======
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
>>>>>>> 0ab2dfec0610040be5443984ffa1f521db7f86f9
    }
}