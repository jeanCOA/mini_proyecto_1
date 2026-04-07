import java.util.ArrayList;
import java.util.List;
 
public class FabricaDeCartas {


    //Une los monstruos y las magicas en una sola lista de tipo 'Carta'.
    public static List<Carta> crearMazoCompleto() {
        List<Carta> mazo = new ArrayList<>();
        mazo.addAll(crearMonstruos());// Ejecuta el metodo crearMonstruos() y añade todos los resultados al mazo
        mazo.addAll(crearMagicas());// Ejecuta el metodo crearMagicas() y añade todos los resultados al mazo
        return mazo;
    }
    //Se encarga de fabricar las cartas de tipo Monstruo.
    public static List<CartaMonstruo> crearMonstruos() {
        List<CartaMonstruo> monstruos = new ArrayList<>();
        
        // Bucle que se repite 5 veces para crear cada carta 5 veces
        for (int i = 0; i < 5; i++) {
            monstruos.add(new CartaMonstruo("Guerrero De La Luz",  (byte) 3,  (short) 1200, (short) 1000));
            monstruos.add(new CartaMonstruo("Bestia del Bosque",   (byte) 4,  (short) 1500, (short) 1200));
            monstruos.add(new CartaMonstruo("Guardian del Hierro", (byte) 5,  (short) 1000, (short) 2000));
            monstruos.add(new CartaMonstruo("Hechicero del Caos",  (byte) 4,  (short) 1800, (short) 1500));
        }

        // Bucle para añadir 6 copias de "Caballero Real"
        for (int i = 0; i < 6; i++) {
            monstruos.add(new CartaMonstruo("Caballero Real",      (byte) 6,  (short) 2300, (short) 2000));
        }

        // Bucle para añadir 4 copias de "Dragon Ancestral"
        for (int i = 0; i < 4; i++) {
            monstruos.add(new CartaMonstruo("Dragon Ancestral",    (byte) 8,  (short) 3000, (short) 2500));
        }
 
        return monstruos;
    }
 
    //Se encarga de fabricar las cartas magicas, aqui aparece que cada carta es una clase propia.
    public static List<CartaMagica> crearMagicas() {
        List<CartaMagica> magicas = new ArrayList<>();
        // Añade 2 unidades de la carta "Pot Of Greed"
        magicas.add(new PotOfGreed());
        magicas.add(new PotOfGreed());
        
        // Añade 2 unidades de "Espada De Zeus"
        magicas.add(new EspadaDeZeus());
        magicas.add(new EspadaDeZeus());
 
        // Añade 2 unidades de "Escudo De Atenea"
        magicas.add(new EscudoDeAtenea());
        magicas.add(new EscudoDeAtenea());
 
        // Añade 2 unidades de "Cura Milagrosa"
        magicas.add(new CuraMilagrosa());
        magicas.add(new CuraMilagrosa());

        //Añade una sola carta de cada una
        magicas.add(new Fisura());
        magicas.add(new LlamadaDelAbismo());
 
        return magicas;
    }
}