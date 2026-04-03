import java.util.List; // Importa la clase List para manejar las listas de cartas y el campo

public class Jugador {
    private String nombre; 
    private int Lp = 8000;
    private boolean yaJugoCartaEsteTurno = false; // Variable para controlar si el jugador ya jugó una carta este turno
    private List<Carta> mano;
    private Mazo mazo;
    private List<CartaMonstruo> campo;



    
    //Hay que instanciar e implementar el constructor y los métodos necesarios para manejar la mano, el mazo, el campo, los LP, etc.
    //Por ahora solo voy a instanciar los metodos del diagrama UML, pero luego hay que implementar la lógica de cada uno.






    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getLp() {
        return Lp;
    }


    public void setLp(int lp) {
        Lp = lp;
    }


    public List<Carta> getMano() {
        return mano;
    }


    public void setMano(List<Carta> mano) {
        this.mano = mano;
    }


    public Mazo getMazo() {
        return mazo;
    }


    public void setMazo(Mazo mazo) {
        this.mazo = mazo;
    }


    public List<CartaMonstruo> getCampo() {
        return campo;
    }


    public void setCampo(List<CartaMonstruo> campo) {
        this.campo = campo;
    }


    public void robarCarta() {
        // Lógica para robar una carta del mazo a la mano
        // Solo roba, sin logica de derrota.
    }

    public void jugarCarta(int indice, Context ctx) {
        // Invoca o activa segun getTipo()
        // Lógica para jugar una carta de la mano al campo
    }

    public void recibirDanio(int pts) {
        // En los argumentos de este metodo evaluar si se usa el tipo de dato entero tal como esta (int pts)
        // Lógica para recibir daño y reducir los LP del jugador

        // LP = LP - pts; // Ejemplo de cómo reducir los LP del jugador al recibir daño
        // Si los LP < 0 entonces LP = 0; // Para asegurarse de que los LP no sean negativos
    }

    public void curarDanio(int pts) {
        // Lp = LP + pts; // Ejemplo de cómo aumentar los LP del jugador al curar daño
        // Lógica para curar daño y aumentar los LP del jugador

        //recordar que los LP = 8000 solo es la condicion inicial, pero mediante efectos puede sobrepasar los 8000
    }

    public boolean tieneMonstruosEnCampo() {
        // Lógica para verificar si el jugador tiene monstruos en el campo
        return false; // Implementar lógica para verificar si hay monstruos en el campo
    }

    public boolean tieneCartasEnMazo() {
        // Lógica para verificar si el jugador tiene cartas en la mano
        return false; // Implementar lógica para verificar si hay cartas en la mano
    }

    public boolean puedeJugarCarta(){
        // Lógica para verificar si el jugador puede jugar una carta (por ejemplo, si ya jugó una carta este turno)
        return false; // Implementar lógica para determinar si el jugador puede jugar una carta
    }

    public void resetTurno(){
        // Lógica para resetear el estado del jugador al inicio de un nuevo turno (por ejemplo, permitir jugar una carta nuevamente)
        yaJugoCartaEsteTurno = false;

    }


    public void turnoActivo(Contexto ctx){ 

        // Lógica para manejar las acciones del jugador durante su turno activo
        // Por ejemplo, permitir al jugador jugar cartas, atacar, etc.
    }
}
