public class LlamadaDelAbismo extends CartaMagica {
    private int costo = 500;

    public LlamadaDelAbismo() {
        super("Llamada del Abismo", 0, "Robas 1 carta, pero pierdes 500 LP.");
    }

    @Override
    public void activar(Contexto contexto) {
        Jugador j = contexto.getJugadorActivo();
        System.out.println(">>> Llamada del Abismo: Pagando 500 LP para robar...");
        j.setLp(j.getLp() - costo);
        j.robar();
    }
}