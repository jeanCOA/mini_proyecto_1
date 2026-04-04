public class EscudoDeAtenea extends CartaMagica implements Activable{
    private int boostDef = 800;

    public EscudoDeAtenea() {
        super("Escudo de Atenea", "Aumenta +800 DEF a un monstruo en tu campo.");
    }

    @Override
    public void activar(Contexto ctx) {
        System.out.println(">>> Escudo de Atenea otorga +" + boostDef + " DEF.");
        // Según UML: invoca campo.aplicarBoostDef
        ctx.getCampo().aplicarBoostDef(ctx.getJugadorActivo(), boostDef);
    }
}