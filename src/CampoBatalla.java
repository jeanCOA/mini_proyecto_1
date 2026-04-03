public class CampoBatalla {
    private Jugador jugador1;
    private Jugador jugador2;
    private boolean esPrimerTurno = true; // Variable para controlar el primer turno
    private int turnoActual = 0; // Variable para llevar el conteo de los turnos

    public CampoBatalla(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }
    
    public void iniciarDuelo(){
    }

    public void turnoInicial(){
    }

    public void ejecutarTurno(){
    }

    public void cambiarTurno(){
    }

    public void resolverCombate(CartaMonstruo atacante, CartaMonstruo defensor) {
        // Lógica de combate 
    }

    public void ataqueDirecto(CartaMonstruo atacante, Jugador oponente) {
        // Lógica de ataque directo 
    }

    public boolean hayGanador(){
        return false; // Implementar lógica para determinar si hay un ganador
    }

    public Jugador getGanador(){
        return null; // Implementar lógica para determinar quién es el ganador
    }

    public Jugador getJugadorActivo() {
    }

    public Jugador getOponente() {
        return null; // Implementar lógica para determinar quién es el oponente
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void reiniciarAtaques(){

    }
    
    public void eliminarMonstruo(m: CartaMonstruo, j: Jugador){
    }
    
    public void mostrarEstadoCampo(){
    }
}
