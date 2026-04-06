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
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║   ¡DUELO DE YU-GI-OH!        ║");
        System.out.println("╚══════════════════════════════╝");

        // 1. Repartir cartas iniciales
        repartirCartasIniciales();

        // 2. Elegir al azar quién empieza
        Random random = new Random();
        jugadorActivo = random.nextBoolean() ? jugador1 : jugador2;

        System.out.println("\n🎲 " + jugadorActivo.getNombre() + " empieza el duelo!\n");

        // 3. Bucle del duelo
        while (!hayGanador()) {
            ejecutarTurno();
            // Verificar ganador después de cada turno (ej: LlamadaDelAbismo)
            if (hayGanador()) break;
        }

        // 4. Anunciar ganador
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

        // CORRECCIÓN: resetear estado del jugador al INICIO de su turno
        jugadorActivo.resetTurno();

        if (esPrimerTurno) {
            // El jugador que empieza NO roba carta y NO puede atacar (US-13)
            System.out.println("[Primer turno] " + jugadorActivo.getNombre()
                + " no roba carta y no puede atacar.");

            // Bloquear ataques a todos los monstruos del campo (por si acaso)
            for (CartaMonstruo m : jugadorActivo.getCampo()) {
                m.marcarComoAtacado();
            }
        } else {
            // Robo automático al inicio del turno (US-11)
            if (!jugadorActivo.tieneCartasEnMazo()) {
                System.out.println(jugadorActivo.getNombre()
                    + " no tiene cartas en el mazo. ¡Pierde el duelo!");
                return;
            }
            jugadorActivo.robarCarta();
            System.out.println(jugadorActivo.getNombre() + " robó una carta.");

            // Limpiar boosts temporales al inicio del turno del jugador que los recibió
            for (CartaMonstruo m : jugadorActivo.getCampo()) {
                m.decrementarMejora();
            }
        }

        // Turno interactivo
        Contexto ctx = new Contexto(jugadorActivo, getOponente(), this);
        jugadorActivo.turnoActivo(ctx);

        cambiarTurno();
    }

    public void cambiarTurno() {
        // CORRECCIÓN: ya NO llamamos resetTurno() aquí, se hace al inicio del turno
        jugadorActivo = (jugadorActivo == jugador1) ? jugador2 : jugador1;
        esPrimerTurno = false;
        System.out.println("\nTurno de " + jugadorActivo.getNombre() + ".");
    }

    /**
     * Resuelve el combate ATK vs ATK entre dos monstruos en posición de ataque.
     * US-14: si ATK atacante > ATK defensor → defensor destruido, diferencia resta LP al oponente.
     *         si ATK atacante == ATK defensor → ambos destruidos.
     *         si ATK atacante < ATK defensor → atacante repelido, diferencia resta LP al jugador activo.
     */
    public void resolverCombate(CartaMonstruo atacante, CartaMonstruo defensor, Jugador jugActivo, Jugador oponente) {
        System.out.println(atacante.getNombre() + " ataca a " + defensor.getNombre() + "!");

        if (atacante.getAtk() > defensor.getAtk()) {
            // Atacante gana: defensor destruido, daño al oponente
            int danio = atacante.getAtk() - defensor.getAtk();
            eliminarMonstruo(defensor, oponente);
            oponente.recibirDanio(danio);
            System.out.println(defensor.getNombre() + " destruido. " + oponente.getNombre() + " pierde " + danio + " LP.");

        } else if (atacante.getAtk() == defensor.getAtk()) {
            // Empate: ambos destruidos, sin daño a LP
            eliminarMonstruo(defensor, oponente);
            eliminarMonstruo(atacante, jugActivo);
            System.out.println("¡Empate! Ambos monstruos fueron destruidos.");

        } else {
            // Atacante pierde: CORRECCIÓN — el jugador activo recibe la diferencia en daño
            int danio = defensor.getAtk() - atacante.getAtk();
            jugActivo.recibirDanio(danio);
            System.out.println(atacante.getNombre() + " fue repelido. "
                + jugActivo.getNombre() + " pierde " + danio + " LP.");
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