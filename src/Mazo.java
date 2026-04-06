import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazo {
    // lista de objetos Carta
    private List<Carta> cartas;

    public Mazo(boolean usarFabrica) {
        this.cartas = new ArrayList<>();
        if(usarFabrica){
            this.agregarCartas(FabricaDeCartas.crearMazoCompleto());
            this.barajar();
        }
    }

    // Mezcla las cartas aleatoriamente
    public void barajar() {
        Collections.shuffle(cartas); 
        System.out.println("El mazo ha sido barajado.");
    }

    // Saca la primera carta del mazo
    public Carta robar() {
        if (estaVacio()) {
            return null; 
        }
        return cartas.remove(0); // Quita la de arriba y la entrega
    }

    // Revisa si quedan cartas
    public boolean estaVacio() {
        return cartas.isEmpty();
    }

    // Cuantas cartas quedan
    public int tamano() {
        return cartas.size();
    }

    public List<Carta> repartir(int n) {
        List<Carta> manoRepartida = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            Carta cartaRobada = this.robar(); // Reutilizamos el método robar() ya quemaneja el remove(0)
            
            if (cartaRobada != null) {
                manoRepartida.add(cartaRobada);
            } else {
                // Si el mazo se vacía antes de terminar de repartir, dejamos de iterar
                break; 
            }
        }
        
        return manoRepartida;
    }

    // Para meter las que vienen de la fabrica
    public void agregarCartas(List<? extends Carta> nuevasCartas) {
        this.cartas.addAll(nuevasCartas);
    }
    
    
    public List<Carta> getCartas() {
        return new ArrayList<>(cartas);
    }
} 