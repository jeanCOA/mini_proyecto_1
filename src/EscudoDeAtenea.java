public class EscudoDeAtenea extends CartaMagica implements Activable{

    public EscudoDeAtenea(String nombre, String descripcion) {
        super("Escudo de atenea. ", "Aumenta +800 de DEF a un montruo en tu campo. ");
    }

    @Override
    public void Activar(Contexto contexto) {

        if (!contexto.getJugadorActivo().getCampo().isEmpty()){
            contexto.getJugadorActivo().getCampo().get(0).aumentarDef(800);
        }

    }

}
