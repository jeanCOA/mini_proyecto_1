public class CartaMonstruo extends Carta {

    private byte nivelCarta;
    private short atk;
    private short def;
    private short boostAtk;
    private short boostDef;
    private boolean puedeAtacar;

    public CartaMonstruo(String nombre, int nivelCarta, int atk, int def) {
        super(nombre);
        this.nivelCarta = nivelCarta;
        this.atk = atk;
        this.def = def;
        this.boostAtk = 0;
        this.boostDef = 0;
        this.puedeAtacar = false;
    }

    public int getnivelCarta() {
        return nivelCarta;
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

 
public String getTipo() {
    return "MONSTRUO";
}
    @Override
    public String toString() {
        return "[MONSTRUO] " + getNombre() + " | nivelCarta: " + nivelCarta + " | ATK: " + getAtk() + " | DEF: " + getDef();
    }
    
}