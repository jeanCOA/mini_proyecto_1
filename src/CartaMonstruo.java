public class CartaMonstruo extends Carta {

    private int nivel;
    private int atk;
    private int def;
    private int boostAtk;
    private int boostDef;
    private boolean puedeAtacar;

    public CartaMonstruo(String nombre, int nivel, int atk, int def) {
        super(nombre);
        this.nivel = nivel;
        this.atk = atk;
        this.def = def;
        this.boostAtk = 0;
        this.boostDef = 0;
        this.puedeAtacar = false;
    }

    public int getNivel() {
        return nivel;
    }

    public int getAtk() {
        return atk + boostAtk;
    }

    public int getDef() {
        return def + boostDef;
    }

    public boolean puedeAtacar() {
        return puedeAtacar;
    }

    public void setPuedeAtacar(boolean puedeAtacar) {
        this.puedeAtacar = puedeAtacar;
    }

    public void aplicarBoostAtk(int incremento) {
        boostAtk = incremento;
    }

    public void aplicarBoostDef(int incremento) {
        boostDef = incremento;
    }

    public void resetBoosts() {
        boostAtk = 0;
        boostDef = 0;
    }

    public void reiniciarAtaques() {
        puedeAtacar = true;
        resetBoosts();
    }

 

    @Override
    public String toString() {
        return "[MONSTRUO] " + getNombre() + " | Nivel: " + nivel + " | ATK: " + getAtk() + " | DEF: " + getDef();
    }
}