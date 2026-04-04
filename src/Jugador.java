import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private short Lp = 8000;
    private boolean yaJugoCartaEsteTurno = false;
    private List<Carta> mano;
    private Mazo mazo;
    private List<CartaMonstruo> campo;

    public Jugador(String nombre, Mazo mazo) {
        this.nombre = nombre;
        this.mazo = mazo;
        this.Lp = 8000;
        this.mano = new ArrayList<>();
        this.campo = new ArrayList<>();
        this.yaJugoCartaEsteTurno = false;
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

    public void jugarCarta(int indice, Contexto ctx) {
        if (indice < 0 || indice >= mano.size()) return;

        Carta carta = mano.get(indice);

        if (carta.getTipo().equals("MONSTRUO")) {
            if (!yaJugoCartaEsteTurno) {
                campo.add((CartaMonstruo) carta);
                mano.remove(indice);
                yaJugoCartaEsteTurno = true;
                System.out.println(nombre + " invocó a " + carta.getNombre());
            } else {
                System.out.println("Ya has invocado un monstruo este turno.");
            }
        } else if (carta.getTipo().equals("MAGICA")) {
            if (carta instanceof Activable) {
                ((Activable) carta).activar(ctx);
                mano.remove(indice);
                System.out.println(nombre + " activó la magia " + carta.getNombre());
            }
        }
    }

    public void recibirDanio(int pts) {
        this.Lp -= pts;
        if (this.Lp < 0) this.Lp = 0;
        System.out.println(nombre + " pierde " + pts + " LP.");
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
        
    }
}