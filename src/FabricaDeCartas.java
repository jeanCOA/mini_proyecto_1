import java.util.ArrayList;
import java.util.List;
 
public class FabricaDeCartas {
 
    public static List<Carta> crearMazoCompleto() {
        List<Carta> mazo = new ArrayList<>();
        mazo.addAll(crearMonstruos());
        mazo.addAll(crearMagicas());
        return mazo;
    }
 
    public static List<CartaMonstruo> crearMonstruos() {
        List<CartaMonstruo> monstruos = new ArrayList<>();
 
        for (int i = 0; i < 5; i++) {
            monstruos.add(new CartaMonstruo("Guerrero De La Luz",  (byte) 3,  (short) 1200, (short) 1000));
            monstruos.add(new CartaMonstruo("Bestia del Bosque",   (byte) 4,  (short) 1500, (short) 1200));
            monstruos.add(new CartaMonstruo("Guardian del Hierro", (byte) 5,  (short) 1000, (short) 2000));
            monstruos.add(new CartaMonstruo("Hechicero del Caos",  (byte) 4,  (short) 1800, (short) 1500));
        }
 
        for (int i = 0; i < 6; i++) {
            monstruos.add(new CartaMonstruo("Caballero Real",      (byte) 6,  (short) 2300, (short) 2000));
        }
 
        for (int i = 0; i < 4; i++) {
            monstruos.add(new CartaMonstruo("Dragon Ancestral",    (byte) 8,  (short) 3000, (short) 2500));
        }
 
        return monstruos;
    }
 
    public static List<CartaMagica> crearMagicas() {
        List<CartaMagica> magicas = new ArrayList<>();
 
        magicas.add(new PotOfGreed());
        magicas.add(new PotOfGreed());
 
        magicas.add(new EspadaDeZeus());
        magicas.add(new EspadaDeZeus());
 
        magicas.add(new EscudoDeAtenea());
        magicas.add(new EscudoDeAtenea());
 
        magicas.add(new CuraMilagrosa());
        magicas.add(new CuraMilagrosa());
 
        magicas.add(new Fisura());
        magicas.add(new LlamadaDelAbismo());
 
        return magicas;
    }
}