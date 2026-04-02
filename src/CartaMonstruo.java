public class CartaMonstruo extends Carta {

    private int atk;
    private int def;
    private int nivelCarta;
    private int bonusAtkTemporal;
    private int bonusDefTemporal;
    private int turnosRestantesbonus;
    private boolean haAtacado;
    private boolean puedeAtacar;

    public CartaMonstruo(String nombre, int nivel, int atk, int def) {
        super(nombre);
        this.nivelCarta        = nivel;
        this.atk               = atk;
        this.def               = def;
        this.bonusAtkTemporal  = 0;
        this.bonusDefTemporal  = 0;
        this.turnosRestantesbonus = 0;
        this.haAtacado         = false;
        this.puedeAtacar       = false;
    }



    public int getAtk() {
        return atk + bonusAtkTemporal;
    }

    public int getDef() {
        return def + bonusDefTemporal;
    }

    public int getNivel() {
        return nivelCarta;
    }



    public boolean puedeAtacar() {
        return puedeAtacar && !haAtacado;
    }

    public void setPuedeAtacar(boolean valor) {
        this.puedeAtacar = valor;
    }

    public void marcarComoAtacado() {
        this.haAtacado = true;
    }


    public void resetTurno() {
        this.haAtacado   = false;
        this.puedeAtacar = true;
    }



    public void aplicarbonusAtk(int bonus, int turnos) {
        this.bonusAtkTemporal    = bonus;
        this.turnosRestantesbonus = turnos;
    }

    public void aplicarbonusDef(int bonus, int turnos) {
        this.bonusDefTemporal    = bonus;
        this.turnosRestantesbonus = turnos;
    }


    public void descontarbonus() {
        if (turnosRestantesbonus > 0) {
            turnosRestantesbonus--;
            if (turnosRestantesbonus == 0) {
                bonusAtkTemporal = 0;
                bonusDefTemporal = 0;
            }
        }
    }

    // ── Combate ──────────────────────────────────────────────────

    public void atacar(CartaMonstruo defensor, Jugador jugadorDefensor) {
        System.out.println(getNombre() + " ataca a " + defensor.getNombre() + "!");

        if (this.getAtk() > defensor.getAtk()) {
            int danio = this.getAtk() - defensor.getAtk();
            jugadorDefensor.getCampo().remove(defensor);
            jugadorDefensor.recibirDanio(danio);
            System.out.println(defensor.getNombre() + " fue destruido!");
            System.out.println("Daño causado: " + danio + " LP");
        } else if (this.getAtk() == defensor.getAtk()) {
            jugadorDefensor.getCampo().remove(defensor);
            System.out.println("¡Empate! Ambos monstruos son destruidos.");
        } else {
            System.out.println(getNombre() + " no pudo destruir a " + defensor.getNombre());
        }

        this.marcarComoAtacado();
    }

    public void ataqueDirecto(Jugador jugadorDefensor) {
        System.out.println(getNombre() + " ataca directamente!");
        jugadorDefensor.recibirDanio(this.getAtk());
        System.out.println("Daño causado: " + this.getAtk() + " LP");
        this.marcarComoAtacado();
    }

    // ── toString ─────────────────────────────────────────────────

    @Override
    public String toString() {
        String bonus = bonusAtkTemporal > 0 || bonusDefTemporal > 0
            ? " [bonus activo: " + turnosRestantesbonus + " turno(s)]"
            : "";
        return "[MONSTRUO] " + getNombre()
             + " | Nivel: " + nivelCarta
             + " | ATK: "  + getAtk()
             + " | DEF: "  + getDef()
             + bonus;
    }
}