public class EspadaDeZeus extends CartaMagica {
    private int boostAtk = 500;

    public EspadaDeZeus() {
        super("Espada de Zeus", 0, "Aumenta +500 ATK a un monstruo en tu campo.");
    }

    @Override
    public void activar(Contexto contexto) {
        System.out.println(">>> Activando Espada de Zeus: +500 ATK.");
        // Se asume que CampoDeBatalla tiene un método para aplicar este boost
        contexto.getCampo().aplicarBoostAtk(contexto.getJugadorActivo(), boostAtk);
    }
}
