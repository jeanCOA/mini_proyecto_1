public abstract class Carta {

    
    private String nombre;
    private int nivelCarta;

    public Carta(String nombre, int nivelCarta) {
        this.nombre = nombre;
        this.nivelCarta = nivelCarta;
    }  
    public String getNombre() {
        return nombre;
    }

    public int getNivel() {
        return nivelCarta;
    }
    
    public abstract String toString();
}