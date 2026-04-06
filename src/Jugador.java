import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jugador {
    private String nombre;
    private short Lp = 8000;
    private boolean yaJugoCartaEsteTurno = false;
    private List<Carta> mano;
    private Mazo mazo;
    private List<CartaMonstruo> campo;

    private static final Scanner scanner = new Scanner(System.in);

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mazo = new Mazo(false);
        this.Lp = 8000;
        this.mano = new ArrayList<>();
        this.campo = new ArrayList<>();
        this.yaJugoCartaEsteTurno = false;
    }

    public void setMazo(Mazo mazo) {
        this.mazo = mazo;
    }

    public String getNombre() {
        return nombre;
    }

    public short getLp() {
        return Lp;
    }

    public void setLp(int lp) {
        this.Lp = (short) lp;
    }

    public List<Carta> getMano() {
        return mano;
    }

    public List<CartaMonstruo> getCampo() {
        return campo;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public void robarCarta() {
        if (mazo != null) {
            Carta c = mazo.robar();
            if (c != null) {
                mano.add(c);
            }
        }
    }

    // public void jugarCarta(int indice, Contexto ctx) {
    //     if (indice < 0 || indice >= mano.size()) return;

    //     Carta carta = mano.get(indice);

    //     if (carta.getTipo().equals("MONSTRUO")) {
    //         if (!yaJugoCartaEsteTurno) {


    //             CartaMonstruo monstruo = (CartaMonstruo) carta;

    //             campo.add((CartaMonstruo) carta);
    //             mano.remove(indice);
    //             yaJugoCartaEsteTurno = true;


    //             if (ctx.getCampo().isEsPrimerTurno()) {
    //                 monstruo.setPuedeAtacar(false);
    //             } else {
    //                 monstruo.setPuedeAtacar(true);
    //             }
                
    //             System.out.println(nombre + " invocó a " + carta.getNombre());
    //         } else {
    //             System.out.println("Ya has invocado un monstruo este turno.");
    //         }
            
    //     } else if (carta.getTipo().equals("MAGICA")) {
    //         if (carta instanceof Activable) {
    //             ((Activable) carta).activar(ctx);
    //             mano.remove(indice);
                
    //             yaJugoCartaEsteTurno = true;
                
    //             if (ctx.getCampo().hayGanador()) return;
    //         }
    //     }
    // }
    private void cambiarPosicionDesdeMenu(Contexto ctx) {
        if (campo.isEmpty()) {
            System.out.println("No tienes monstruos en campo para cambiar de posición.");
            return;
        }

        System.out.println("\n── Elige el monstruo para cambiar posición ──");
        for (int i = 0; i < campo.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + campo.get(i));
        }
        System.out.println("  0. Cancelar");
        System.out.print("Opción: ");

        String input = scanner.nextLine().trim();
        int eleccion;
        try {
            eleccion = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        if (eleccion == 0) return;
        if (eleccion < 1 || eleccion > campo.size()) {
            System.out.println("Número fuera de rango.");
            return;
        }

        campo.get(eleccion - 1).cambiarPosicion();
    }
    

    //---------------------------------------------------------------

    public void jugarCarta(int indice, Contexto ctx) {
        if (indice < 0 || indice >= mano.size()) return;

        if (yaJugoCartaEsteTurno) {
            System.out.println("Ya has jugado una carta este turno. No puedes jugar más cartas.");
            return;
        }

        Carta carta = mano.get(indice);

        if (carta.getTipo().equals("MONSTRUO")) {
            CartaMonstruo monstruo = (CartaMonstruo) carta;
            campo.add(monstruo);
            mano.remove(indice);
            yaJugoCartaEsteTurno = true;

            
            monstruo.setPuedeAtacar(ctx.getCampo().isEsPrimerTurno() ? false : true);
            
            System.out.println(nombre + " invocó a " + carta.getNombre() + " en modo ATAQUE.");
            
        } else if (carta.getTipo().equals("MAGICA")) {
            if (carta instanceof Activable) {
                ((Activable) carta).activar(ctx);
                mano.remove(indice);
                yaJugoCartaEsteTurno = true;
                
                if (ctx.getCampo().hayGanador()) return;
            }
        }
    }

    public void recibirDanio(int pts) {
        this.Lp -= pts;
        if (this.Lp < 0) this.Lp = 0;
        System.out.println(nombre + " pierde " + pts + " LP. (LP restante: " + this.Lp + ")");
    }

    public void curarDanio(int pts) {
        this.Lp += pts;
        System.out.println(nombre + " recupera " + pts + " LP.");
    }

    public boolean tieneMonstruosEnCampo() {
        return !campo.isEmpty();
    }

    public boolean tieneCartasEnMazo() {
        return mazo != null && !mazo.estaVacio();
    }

    public boolean puedeJugarCarta() {
        return !yaJugoCartaEsteTurno;
    }

    public void resetTurno() {
        this.yaJugoCartaEsteTurno = false;
        for (CartaMonstruo m : campo) {
            m.setPuedeAtacar(true);
        }
    }

    public void turnoActivo(Contexto ctx) {
        System.out.println("\n=== TURNO DE: " + nombre.toUpperCase() + " (LP: " + Lp + ") ===");

        boolean turnoTerminado = false;

        while (!turnoTerminado && !ctx.getCampo().hayGanador()) {
            mostrarEstado(ctx);
            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("  1. Jugar una carta");

            
            boolean hayAtacantes = campo.stream().anyMatch(CartaMonstruo::puedeAtacar);
            if (hayAtacantes && !ctx.getOponente().getCampo().isEmpty() || hayAtacantes) {
                System.out.println("  2. Atacar con un monstruo");
            } else {
                System.out.println("  2. Atacar con un monstruo [no disponible]");
            }

            System.out.println("  3. Terminar turno");
            System.out.println("  4. Cambiar posición de un monstruo");
            System.out.print("Opción: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    jugarCartaDesdeMenu(ctx);
                    break;
                case "2":
                    atacarDesdeMenu(ctx);
                    break;
                case "3":
                    turnoTerminado = true;
                    System.out.println(nombre + " termina su turno.");
                    break;
                case "4":                                      
                    cambiarPosicionDesdeMenu(ctx);
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
            }
        }
    }

    private void mostrarEstado(Contexto ctx) {
        Jugador oponente = ctx.getOponente();
        System.out.println("\n──────────────────────────────");
        System.out.println("  " + nombre + " | LP: " + Lp
            + " | Cartas en mano: " + mano.size()
            + " | Cartas en mazo: " + mazo.tamano());
        System.out.println("  Monstruos en campo:");
        if (campo.isEmpty()) {
            System.out.println("    (ninguno)");
        } else {
            for (int i = 0; i < campo.size(); i++) {
                CartaMonstruo m = campo.get(i);
                String estado = m.puedeAtacar() ? "puede atacar" : "no puede atacar este turno";
                System.out.println("    " + (i + 1) + ". " + m + " [" + estado + "]");
            }
        }
        System.out.println("  ── Oponente: " + oponente.getNombre()
            + " | LP: " + oponente.getLp()
            + " | Cartas en mano: " + oponente.getMano().size()
            + " | Cartas en mazo: " + oponente.getMazo().tamano());
        System.out.println("  Monstruos del oponente en campo:");
        if (oponente.getCampo().isEmpty()) {
            System.out.println("    (ninguno)");
        } else {
            for (int i = 0; i < oponente.getCampo().size(); i++) {
                System.out.println("    " + (i + 1) + ". " + oponente.getCampo().get(i));
            }
        }
        System.out.println("──────────────────────────────");
    }

    private void jugarCartaDesdeMenu(Contexto ctx) {
        if (mano.isEmpty()) {
            System.out.println("No tienes cartas en la mano.");
            return;
        }

        
        if (yaJugoCartaEsteTurno) {
            System.out.println("Ya jugaste una carta este turno. No puedes jugar otra.");
            return;
        }

        System.out.println("\n── Tu mano ──");
        for (int i = 0; i < mano.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + mano.get(i));
        }
        System.out.println("  0. Cancelar");
        System.out.print("Elige una carta: ");

        String input = scanner.nextLine().trim();
        int eleccion;
        try {
            eleccion = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        if (eleccion == 0) return;

        if (eleccion < 1 || eleccion > mano.size()) {
            System.out.println("Número fuera de rango.");
            return;
        }

        jugarCarta(eleccion - 1, ctx);
    }

    private void atacarDesdeMenu(Contexto ctx) {
        if (campo.isEmpty()) {
            System.out.println("No tienes monstruos en campo.");
            return;
        }

        // Filtrar monstruos que pueden atacar
        List<CartaMonstruo> disponibles = new ArrayList<>();
        for (CartaMonstruo m : campo) {
            if (m.puedeAtacar()) disponibles.add(m);
        }

        if (disponibles.isEmpty()) {
            System.out.println("Ningún monstruo puede atacar este turno.");
            return;
        }

        System.out.println("\n── Elige el monstruo atacante ──");
        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + disponibles.get(i));
        }
        System.out.println("  0. Cancelar");
        System.out.print("Opción: ");

        String input = scanner.nextLine().trim();
        int eleccion;
        try {
            eleccion = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        if (eleccion == 0) return;
        if (eleccion < 1 || eleccion > disponibles.size()) {
            System.out.println("Número fuera de rango.");
            return;
        }

        CartaMonstruo atacante = disponibles.get(eleccion - 1);
        Jugador oponente = ctx.getOponente();

        if (oponente.getCampo().isEmpty()) {
            
            ctx.getCampo().ataqueDirecto(atacante, oponente);
        } else {
            
            System.out.println("\n── Elige el monstruo a atacar ──");
            List<CartaMonstruo> defensores = oponente.getCampo();
            for (int i = 0; i < defensores.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + defensores.get(i));
            }
            System.out.println("  0. Cancelar");
            System.out.print("Opción: ");

            String inputDef = scanner.nextLine().trim();
            int eleccionDef;
            try {
                eleccionDef = Integer.parseInt(inputDef);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
                return;
            }

            if (eleccionDef == 0) return;
            if (eleccionDef < 1 || eleccionDef > defensores.size()) {
                System.out.println("Número fuera de rango.");
                return;
            }

            CartaMonstruo defensor = defensores.get(eleccionDef - 1);
            
            ctx.getCampo().resolverCombate(atacante, defensor, this, oponente);
        }
    }
}