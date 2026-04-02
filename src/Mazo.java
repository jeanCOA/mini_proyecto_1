import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazo {
    // lista de objetos Carta
    private List<Carta> cartas;

    public Mazo() {
        this.cartas = new ArrayList<>();
    }

    // Mezcla las cartas aleatoriamente
    public void barajar() {
        Collections.shuffle(cartas); //collections:Contiene los metodos y shuffle:Mezcla todo.
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

    // Cuántas cartas quedan
    public int tamano() {
        return cartas.size();
    }

    // Para meter las que vienen de la Fábrica
    public void agregarCartas(List<? extends Carta> nuevasCartas) {
        this.cartas.addAll(nuevasCartas);
    }
    
    // Devuelve una copia de la lista (según el UML v4)
    public List<Carta> getCartas() {
        return new ArrayList<>(cartas); // Copia defensiva
    }
}