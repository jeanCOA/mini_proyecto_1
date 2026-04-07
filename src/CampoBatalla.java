import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CampoBatalla {
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActivo;
    private boolean esPrimerTurno = true;
    private byte turnoActual = 0;

    public CampoBatalla(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    public void iniciarDuelo() {
        

       // llamo al metodo para gestionar y repartir cartas
        repartirCartasIniciales();

        // aqui se crea el objeto para poder randomizar quien empieza
        Random random = new Random();
        jugadorActivo = random.nextBoolean() ? jugador1 : jugador2;

        System.out.println("\n " + jugadorActivo.getNombre() + " empieza el duelo!\n");

        // este es el bucle que controla la partida siempre y cuando no haya ganador
        while (!hayGanador()) {

            // llama al metodo donde se ejecuta el turno
            ejecutarTurno();
            
            if (hayGanador()) break;
        }

        // llama al metodo getGanador para determinar quien fue el ganador
        Jugador ganador = getGanador();
        if (ganador != null) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║  ¡" + ganador.getNombre() + " GANA EL DUELO!  ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.println("\"Confía en el corazón de las cartas\" — Yugi Muto");
        }
    }

    private void repartirCartasIniciales() {

        // se llama directamente a FabricaDeCartas para usar el metodo de crear mazo completo una sola vez
        List<Carta> mazoCompleto = FabricaDeCartas.crearMazoCompleto();

        // se hace el uso de libreria de java para desordenar el mazo
        Collections.shuffle(mazoCompleto);


        // aqui se reparte a cada mazo de a 20 cartas
        List<Carta> mazo1 = new ArrayList<>(mazoCompleto.subList(0, 20));
        List<Carta> mazo2 = new ArrayList<>(mazoCompleto.subList(20, 40));

        // con los atributos anteriores (listas) se llama a el metodo agregarCartas para pasarle como argumento el mazo para cada jugador
        jugador1.getMazo().agregarCartas(mazo1);
        jugador2.getMazo().agregarCartas(mazo2);


        // se utiliza el metodo de Mazo de repartir 5 a cada mano, y agregarlo a la mano de los jugadores.
        jugador1.getMano().addAll(jugador1.getMazo().repartir(5));
        jugador2.getMano().addAll(jugador2.getMazo().repartir(5));

        System.out.println("Cartas repartidas: " + jugador1.getNombre() + " y " + jugador2.getNombre());
    }

    public void ejecutarTurno() {

        // al iniciar el codigo se debe asegurar que turnoActual aumente en 1
        turnoActual++;
        System.out.println("\n══════════════════════════════");
        System.out.println("  TURNO " + turnoActual + " : " + jugadorActivo.getNombre());
        System.out.println("══════════════════════════════");

        // primero se llama a jugadorActivo.resetTurno, para resetear lo del turno anterior del jugador activo.
        
        jugadorActivo.resetTurno();


        //Condicional para establecer la regla de no robar y atacar en el primer turno
        if (esPrimerTurno) {
            
            System.out.println("[Primer turno] " + jugadorActivo.getNombre()
                + " no roba carta y no puede atacar.");

            
            // se hace un ciclo que recorra todos los monstruos en el campo del jugador Activo para que marque que no pueden atacar
            for (CartaMonstruo m : jugadorActivo.getCampo()) {
                m.marcarComoAtacado();
            }
        } else {
            // esto es lo que se ejecuta SIEMPRE despues del primer turno

            // primero se hace una verificacion si el jugador activo no tiene cartas en mazo, inmediatamente pierde el duelo
            if (!jugadorActivo.tieneCartasEnMazo()) {
                System.out.println(jugadorActivo.getNombre()
                    + " no tiene cartas en el mazo. ¡Pierde el duelo!");
                return;
            }

            // aqui se hace el proceso correspondiente de robar carta al inicio de cada turno
            jugadorActivo.robarCarta();
            System.out.println(jugadorActivo.getNombre() + " robó una carta.");

            // un ciclo que recorre todos los monstruos del campo del jugador activo, para quitarle las mejoras a todos. (de las magicas)
            for (CartaMonstruo m : jugadorActivo.getCampo()) {
                m.decrementarMejora();
            }
        }

        // se crea un nuevo contexto en cada turno para estar al dia de el contexto de el duelo
        Contexto ctx = new Contexto(jugadorActivo, getOponente(), this);
        //lo anterior se hace para poder ejecutar el metodo de el turno activo directamente en Jugador
        jugadorActivo.turnoActivo(ctx);

        cambiarTurno();
    }

    public void cambiarTurno() {
        
        jugadorActivo = (jugadorActivo == jugador1) ? jugador2 : jugador1;
        esPrimerTurno = false;
        System.out.println("\nTurno de " + jugadorActivo.getNombre() + ".");
    }


    // recibe como argumentos las cartas del atacante, las cartas del defensor, el jugador activo y el oponente
    public void resolverCombate(CartaMonstruo atacante, CartaMonstruo defensor, Jugador jugActivo, Jugador oponente) {

        // mensaje para imprimir el nombre de la carta y a cual ataca
        System.out.println(atacante.getNombre() + " ataca a " + defensor.getNombre() + "!");


        // se inicializa una variable que recibe el ATK de el atacante
        int atkAtacante = atacante.getAtk();

        // se inicializa una variable que recibe un True o False si el monstruo defensor esta en Defensa
        boolean defensorEnDefensa = defensor.estaEnModoDefensa();


        // condicional para ejecutar codigo si el oponente tiene la carta en modo defensa
        if (defensorEnDefensa) {
            //variable para guardar la cantidad de DEF de la carta oponente
            int defDefensor = defensor.getDef();
            System.out.println(defensor.getNombre() + " está en MODO DEFENSA (DEF: " + defDefensor + ").");


            // si mi carta supera la defensa del oponente entonces
            if (atkAtacante > defDefensor) {
                // entonces se elimina el monstruo del oponente
                eliminarMonstruo(defensor, oponente);
                System.out.println(defensor.getNombre() + " fue destruido en modo defensa.");
                
            } else {
                // pero si no supera la defensa, entonces esa carta bloquea el ataque
                System.out.println("¡Ataque bloqueado! La defensa de " + defensor.getNombre() + " es demasiado alta.");
            }
        } else {
            // SI EL DEFENSOR NO ESTA EN DEFENSA, EJECUTA ESTE BLOQUE DE CODIGO

            
            if (atkAtacante > defensor.getAtk()) { // este bloque se ejecuta si el ATK de mi carta es mayor que el ATK de la carta del oponente
                // crea una variable para guardar lo que se le hace de daño al oponente
                int danio = atkAtacante - defensor.getAtk();
                // se elimina el monstruo
                eliminarMonstruo(defensor, oponente);
                // y la diferencia restante de daño, afecta a los LP del oponente:
                oponente.recibirDanio(danio);
                System.out.println(defensor.getNombre() + " destruido. " + oponente.getNombre() + " pierde " + danio + " LP.");


            } else if (atkAtacante == defensor.getAtk()) { // este bloque se ejecuta si hay un empate
                eliminarMonstruo(defensor, oponente);
                eliminarMonstruo(atacante, jugActivo);
                // si hay un empate de ataque, los dos se eliminan y nadie sufre daño
                System.out.println("¡Empate! Ambos monstruos fueron destruidos.");
            } else { // este bloque se ejecuta como ultima opcion, donde la ultima opcion es que mi ATK es menor al ATK del rival (y lo sobrante se afecta a los Life Points del jugadora ctivo)
                // se crea una variable de daño, donde se calcula cuanto daño va a recibir el jugador activo
                int danio = defensor.getAtk() - atkAtacante;

                //llamo al metodo para recibir el daño
                jugActivo.recibirDanio(danio);
                System.out.println(atacante.getNombre() + " fue repelido. " 
                    + jugActivo.getNombre() + " pierde " + danio + " LP.");
            }
        }

        // llamo al metodo para que la carta que ataco la marque y no pueda atacar mas (puedeAtacar = False)
        atacante.marcarComoAtacado();
    }


    public void ataqueDirecto(CartaMonstruo atacante, Jugador oponente) {
        System.out.println(atacante.getNombre() + " ataca directamente a "
            + oponente.getNombre() + "!");
        oponente.recibirDanio(atacante.getAtk());
        System.out.println(oponente.getNombre() + " pierde " + atacante.getAtk() + " LP.");
        atacante.marcarComoAtacado();
    }

    public void aplicarBoostAtk(Jugador j, short boost) {
        if (!j.getCampo().isEmpty()) {
            j.getCampo().get(0).aplicarBoostAtk(boost);
        } else {
            System.out.println("No tienes monstruos en campo para aplicar el boost.");
        }
    }

    public void aplicarBoostDef(Jugador j, short boost) {
        if (!j.getCampo().isEmpty()) {
            j.getCampo().get(0).aplicarBoostDef(boost);
        } else {
            System.out.println("No tienes monstruos en campo para aplicar el boost.");
        }
    }

    public void destruirMenorAtkOponente(Jugador jugActivo) {
        Jugador oponente = (jugActivo == jugador1) ? jugador2 : jugador1;
        if (oponente.getCampo().isEmpty()) {
            System.out.println("El oponente no tiene monstruos en campo.");
            return;
        }

        CartaMonstruo menor = oponente.getCampo().get(0);
        for (CartaMonstruo m : oponente.getCampo()) {
            if (m.getAtk() < menor.getAtk()) menor = m;
        }
        eliminarMonstruo(menor, oponente);
        System.out.println(">>> Fisura destruyó a " + menor.getNombre() + ".");
    }

    public boolean hayGanador() {
        return jugador1.getLp() <= 0
            || jugador2.getLp() <= 0
            || !jugador1.tieneCartasEnMazo()
            || !jugador2.tieneCartasEnMazo();
    }

    public Jugador getGanador() {
        if (jugador1.getLp() <= 0 || !jugador1.tieneCartasEnMazo()) return jugador2;
        if (jugador2.getLp() <= 0 || !jugador2.tieneCartasEnMazo()) return jugador1;
        return null;
    }

    public Jugador getJugadorActivo() {
        return jugadorActivo;
    }

    public Jugador getOponente() {
        return (jugadorActivo == jugador1) ? jugador2 : jugador1;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public boolean isEsPrimerTurno() {
        return esPrimerTurno;
    }

    public void reiniciarAtaques() {
        for (CartaMonstruo m : jugadorActivo.getCampo()) {
            m.resetTurno();
        }
    }

    public void eliminarMonstruo(CartaMonstruo m, Jugador j) {
        j.getCampo().remove(m);
        System.out.println(m.getNombre() + " fue al cementerio.");
    }

    public void mostrarEstadoCampo() {
        System.out.println("\n--- Estado del campo ---");
        System.out.println(jugador1.getNombre() + " | LP: " + jugador1.getLp()
            + " | Cartas en mano: " + jugador1.getMano().size()
            + " | Monstruos en campo: " + jugador1.getCampo().size());
        for (CartaMonstruo m : jugador1.getCampo()) {
            System.out.println("   " + m);
            System.out.println("-----------------------------------------------");
        }
        System.out.println(jugador2.getNombre() + " | LP: " + jugador2.getLp()
            + " | Cartas en mano: " + jugador2.getMano().size()
            + " | Monstruos en campo: " + jugador2.getCampo().size());
        for (CartaMonstruo m : jugador2.getCampo()) {
            System.out.println("   " + m);
        }
        System.out.println("------------------------");
    }
}