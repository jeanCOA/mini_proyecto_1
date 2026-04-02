public abstract class CartaMagica extends Carta implements Activable {
    protected String descripcion;

    public CartaMagica (String nombre, String descripcion){
        super(nombre); //llama al nombre de la clase carta que crea jean
        this.descripcion = descripcion;


    }

    public String getDescripcion() {
        return descripcion;
    }

    public abstract void aplicarEfecto(Jugador jugador);





    
}
