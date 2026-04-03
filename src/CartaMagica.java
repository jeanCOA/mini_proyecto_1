public abstract class CartaMagica extends Carta implements Activable {
    
   
    private String descripcion;

    public CartaMagica(String nombre, String descripcion) {
        super(nombre);
        this.descripcion = descripcion;
    }

    @Override
    public abstract void activar(Contexto ctx);

    public String getDescripcion() {
        return this.descripcion;
    }

    @Override
    public String getTipo() {
        return "MAGICA";
    }

    @Override
    public String toString() {
        return "[" + getTipo() + "] " + getNombre() + ": " + descripcion;
    }
}