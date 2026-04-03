public class Fisura extends CartaMagica {
    public Fisura() {
        super("Fisura", 0, "Destruye al monstruo con menor ATK del oponente.");
    }

    @Override
    public void activar(Contexto ctx) {
        System.out.println(">>> Activando Fisura: Destruyendo al monstruo más débil del rival.");
        // Según UML: invoca destruirMenorAtkOponente
        ctx.getCampo().destruirMenorAtkOponente(ctx.getJugadorActivo());
    }
}