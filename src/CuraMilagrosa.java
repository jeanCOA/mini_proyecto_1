public class CuraMilagrosa extends CartaMagica {
    private int puntosCura = 1000;

    public CuraMilagrosa() {
        super("Cura Milagrosa", 0, "Aumenta +1000 LP a tu contador de vida.");
    }

    @Override
    public void activar(Contexto contexto) {
        Jugador j = contexto.getJugadorActivo();
        j.setLp(j.getLp() + puntosCura);
        System.out.println(">>> ¡Cura Milagrosa! " + j.getNombre() + " recupera 1000 LP.");
    }
}