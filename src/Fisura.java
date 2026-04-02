public class Fisura extends CartaMagica {
    public Fisura() {
        super("Fisura", 0, "Destruye al monstruo con menor ATK del oponente.");
    }

    @Override
    public void activar(Contexto contexto) {
        System.out.println(">>> Activando Fisura: Buscando al más débil del rival...");
        contexto.getCampo().destruirMenorAtkOponente(contexto.getJugadorActivo());
    }
}
