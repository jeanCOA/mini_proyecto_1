public class PotOfGreed extends CartaMagica {
    public PotOfGreed() {
        super("Pot of Greed", 0, "Roba 2 cartas del mazo inmediatamente.");
    }

    @Override
    public void activar(Contexto contexto) {
        System.out.println(">>> Activando Pot of Greed: Robando 2 cartas...");
        contexto.getJugadorActivo().robar();
        contexto.getJugadorActivo().robar();
    }
}