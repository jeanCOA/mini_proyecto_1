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

        // 1. Barajar los mazos de ambos jugadores
        jugador1.getMazo().barajar();
        jugador2.getMazo().barajar();

        // 2. Repartir 5 cartas iniciales a cada jugador
        jugador1.getMano().addAll(jugador1.getMazo().repartir(5));
        jugador2.getMano().addAll(jugador2.getMazo().repartir(5));

        System.out.println(jugador1.getNombre() + " recibió 5 cartas.");
        System.out.println(jugador2.getNombre() + " recibió 5 cartas.");

        // 3. Elegir al azar quién empieza
        Random random = new Random();
        jugadorActivo = random.nextBoolean() ? jugador1 : jugador2;

        System.out.println("\n🎲 " + jugadorActivo.getNombre() + " empieza el duelo!\n");

        // 4. Arrancar el bucle del duelo
        while (!hayGanador()) {
            ejecutarTurno();
        }

        // 5. Anunciar ganador
        Jugador ganador = getGanador();
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║  ¡" + ganador.getNombre() + " GANA EL DUELO!  ║");
        System.out.println("╚══════════════════════════════╝");
        System.out.println("\"Confía en el corazón de las cartas\" — Yugi Muto");
    }

    public void ejecutarTurno() {
        turnoActual++;
        System.out.println("\n── Turno " + turnoActual + " ──");

        // 1. El jugador activo roba 1 carta
        if (jugadorActivo.tieneCartasEnMazo()) {
            jugadorActivo.robarCarta();
            System.out.println(jugadorActivo.getNombre() + " robó una carta.");
        } else {
            System.out.println(jugadorActivo.getNombre() 
                + " no tiene cartas en el mazo. ¡Pierde el duelo!");
            return;
        }

        
        for (CartaMonstruo m : jugadorActivo.getCampo()) {
            m.decrementarMejora();
        }

        
        mostrarEstadoCampo();

        
        Contexto ctx = new Contexto(jugadorActivo, getOponente(), this);
        jugadorActivo.turnoActivo(ctx);

        
        cambiarTurno();
    }

    public void cambiarTurno() {
        jugadorActivo.resetTurno();
        jugadorActivo = (jugadorActivo == jugador1) ? jugador2 : jugador1;
        esPrimerTurno = false;
        System.out.println("\nTurno de " + jugadorActivo.getNombre() + ".");
    }

    public void resolverCombate(CartaMonstruo atacante, CartaMonstruo defensor) {
        System.out.println(atacante.getNombre() + " ataca a " + defensor.getNombre() + "!");

        Jugador oponente = getOponente();

        if (atacante.getAtk() > defensor.getAtk()) {
            int danio = atacante.getAtk() - defensor.getAtk();
            eliminarMonstruo(defensor, oponente);
            oponente.recibirDanio(danio);
            System.out.println(defensor.getNombre() + " destruido. Daño: " + danio + " LP");
        } else if (atacante.getAtk() == defensor.getAtk()) {
            eliminarMonstruo(defensor, oponente);
            eliminarMonstruo(atacante, jugadorActivo);
            System.out.println("¡Empate! Ambos monstruos destruidos.");
        } else {
            System.out.println(atacante.getNombre() + " no pudo destruir a " 
                + defensor.getNombre() + ".");
        }

        atacante.marcarComoAtacado();
    }

    public void ataqueDirecto(CartaMonstruo atacante, Jugador oponente) {
        System.out.println(atacante.getNombre() + " ataca directamente a " 
            + oponente.getNombre() + "!");
        oponente.recibirDanio(atacante.getAtk());
        atacante.marcarComoAtacado();
    }

    public void aplicarBoostAtk(Jugador j, short boost) {
        if (!j.getCampo().isEmpty()) {
            j.getCampo().get(0).aplicarBoostAtk(boost);
        }
    }

    public void aplicarBoostDef(Jugador j, short boost) {
        if (!j.getCampo().isEmpty()) {
            j.getCampo().get(0).aplicarBoostDef(boost);
        }
    }

    public void destruirMenorAtkOponente(Jugador jugadorActivo) {
        Jugador oponente = (jugadorActivo == jugador1) ? jugador2 : jugador1;
        if (oponente.getCampo().isEmpty()) {
            System.out.println("El oponente no tiene monstruos en campo.");
            return;
        }

        CartaMonstruo menor = oponente.getCampo().get(0);
        for (CartaMonstruo m : oponente.getCampo()) {
            if (m.getAtk() < menor.getAtk()) menor = m;
        }
        eliminarMonstruo(menor, oponente);
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