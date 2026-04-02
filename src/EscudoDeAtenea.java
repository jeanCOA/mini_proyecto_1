public class EscudoDeAtenea extends CartaMagica {
    private int boostDef = 800;

    public EscudoDeAtenea() {
        super("Escudo de Atenea", 0, "Aumenta +800 DEF a un monstruo en tu campo.");
    }

    @Override
    public void activar(Contexto contexto) {
        System.out.println(">>> Activando Escudo de Atenea: +800 DEF.");
        contexto.getCampo().aplicarBoostDef(contexto.getJugadorActivo(), boostDef);
    }
}