public class EspadaDeZeus extends CartaMagica implements Activable{
    
    public EspadaDeZeus(){

        super("Espada de Zeus", "Aumenta +500 ATK a un mounstruo en tu campo.");

    }

    @Override
    public void Activar(Contexto contexto) {

        if (!contexto.getJugadorActivo().getCampo().isEmpty()){

            contexto.getJugadorActivo().getCampo().get(0).aumentarATK(500);
            System.out.println("Ataque aumentado.");

        }

    }
    

}
