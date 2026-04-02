public abstract class CartaMagica extends Carta implements Activable {
    protected String descripcion;

    public CartaMagica(String nombre, String descripcion) {
        super(nombre, 0); // Las magias suelen ser nivel 0 según el nuevo UML
        this.descripcion = descripcion;
    }

    @Override
    public String getTipo() {
        return "MAGICA";
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
    
}