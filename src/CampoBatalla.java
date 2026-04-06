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
        

       
        repartirCartasIniciales();

        
        Random random = new Random();
        jugadorActivo = random.nextBoolean() ? jugador1 : jugador2;

        System.out.println("\n " + jugadorActivo.getNombre() + " empieza el duelo!\n");

        
        while (!hayGanador()) {
            ejecutarTurno();
            
            if (hayGanador()) break;
        }

        
        Jugador ganador = getGanador();
        if (ganador != null) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║  ¡" + ganador.getNombre() + " GANA EL DUELO!  ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.println("\"Confía en el corazón de las cartas\" — Yugi Muto");
        }
    }

    private void repartirCartasIniciales() {
        List<Carta> mazoCompleto = FabricaDeCartas.crearMazoCompleto();
        Collections.shuffle(mazoCompleto);

        List<Carta> mazo1 = new ArrayList<>(mazoCompleto.subList(0, 20));
        List<Carta> mazo2 = new ArrayList<>(mazoCompleto.subList(20, 40));

        jugador1.getMazo().agregarCartas(mazo1);
        jugador2.getMazo().agregarCartas(mazo2);

        jugador1.getMano().addAll(jugador1.getMazo().repartir(5));
        jugador2.getMano().addAll(jugador2.getMazo().repartir(5));

        System.out.println("Cartas repartidas: " + jugador1.getNombre() + " y " + jugador2.getNombre());
    }

    public void ejecutarTurno() {
        turnoActual++;
        System.out.println("\n══════════════════════════════");
        System.out.println("  TURNO " + turnoActual + " : " + jugadorActivo.getNombre());
        System.out.println("══════════════════════════════");

        
        jugadorActivo.resetTurno();

        if (esPrimerTurno) {
            
            System.out.println("[Primer turno] " + jugadorActivo.getNombre()
                + " no roba carta y no puede atacar.");

            
            for (CartaMonstruo m : jugadorActivo.getCampo()) {
                m.marcarComoAtacado();
            }
        } else {
            
            if (!jugadorActivo.tieneCartasEnMazo()) {
                System.out.println(jugadorActivo.getNombre()
                    + " no tiene cartas en el mazo. ¡Pierde el duelo!");
                return;
            }
            jugadorActivo.robarCarta();
            System.out.println(jugadorActivo.getNombre() + " robó una carta.");

            
            for (CartaMonstruo m : jugadorActivo.getCampo()) {
                m.decrementarMejora();
            }
        }

        
        Contexto ctx = new Contexto(jugadorActivo, getOponente(), this);
        jugadorActivo.turnoActivo(ctx);

        cambiarTurno();
    }

    public void cambiarTurno() {
        
        jugadorActivo = (jugadorActivo == jugador1) ? jugador2 : jugador1;
        esPrimerTurno = false;
        System.out.println("\nTurno de " + jugadorActivo.getNombre() + ".");
    }



        public void resolverCombate(CartaMonstruo atacante, CartaMonstruo defensor, Jugador jugActivo, Jugador oponente) {
        System.out.println(atacante.getNombre() + " ataca a " + defensor.getNombre() + "!");

        int atkAtacante = atacante.getAtk();
        boolean defensorEnDefensa = defensor.estaEnModoDefensa();

        if (defensorEnDefensa) {
            int defDefensor = defensor.getDef();
            System.out.println(defensor.getNombre() + " está en MODO DEFENSA (DEF: " + defDefensor + ").");

            if (atkAtacante > defDefensor) {
                eliminarMonstruo(defensor, oponente);
                System.out.println(defensor.getNombre() + " fue destruido en modo defensa.");
                
            } else {
                System.out.println("¡Ataque bloqueado! La defensa de " + defensor.getNombre() + " es demasiado alta.");
            }
        } else {
            
            if (atkAtacante > defensor.getAtk()) {
                int danio = atkAtacante - defensor.getAtk();
                eliminarMonstruo(defensor, oponente);
                oponente.recibirDanio(danio);
                System.out.println(defensor.getNombre() + " destruido. " + oponente.getNombre() + " pierde " + danio + " LP.");
            } else if (atkAtacante == defensor.getAtk()) {
                eliminarMonstruo(defensor, oponente);
                eliminarMonstruo(atacante, jugActivo);
                System.out.println("¡Empate! Ambos monstruos fueron destruidos.");
            } else {
                int danio = defensor.getAtk() - atkAtacante;
                jugActivo.recibirDanio(danio);
                System.out.println(atacante.getNombre() + " fue repelido. " 
                    + jugActivo.getNombre() + " pierde " + danio + " LP.");
            }
        }

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