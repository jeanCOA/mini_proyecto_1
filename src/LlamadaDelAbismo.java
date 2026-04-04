public class LlamadaDelAbismo extends CartaMagica {
    private int costo = 500;

    public LlamadaDelAbismo() {
        super("Llamada del Abismo", "Robas 1 carta, pero pierdes 500 LP.");
    }

    @Override
    public void activar(Contexto ctx) {
        Jugador j = ctx.getJugadorActivo();
        System.out.println(">>> Llamada del Abismo: Pagando " + costo + " LP para robar.");
        j.setLp(j.getLp() - costo);
        j.robarCarta();
    }
}