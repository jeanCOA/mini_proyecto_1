public abstract class CartaMagica extends Carta implements Activable {
    protected String descripcion; 
    public CartaMagica(String nombre, int nivel, String descripcion) {
        super(nombre, nivel); // El nombre y nivel se van a la clase Carta
        this.descripcion = descripcion; 
    }

    // Este método es el que usará tu Menú para mostrar qué hace la carta
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String getTipo() {
        return "MAGICA";
    }
}