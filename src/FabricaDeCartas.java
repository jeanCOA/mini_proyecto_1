import java.util.ArrayList;
import java.util.List;


public class FabricaDeCartas {

    public static List<Carta> crearMazoCompleto() {
        List<Carta> mazo = new ArrayList<>();
        mazo.addAll(crearMonstruos());
        mazo.addAll(crearMagicas());
        return mazo;
    }


    
    public static List<CartaMonstruo> crearMonstruos(){
        List<CartaMonstruo> monstruos = new ArrayList<>();

        for(int i=0;i<5;i++){
        monstruos.add(new CartaMonstruo("Guerrero De La Luz", 3, 1200, 1000));
        monstruos.add(new CartaMonstruo("Bestia del bosque", 4, 1500, 1200));
        monstruos.add(new CartaMonstruo("Guardian del hierro", 5, 1000, 2000));
        monstruos.add(new CartaMonstruo("Hechicero del caos", 4, 1800, 1500));
        }


        for(int i=0;i<6;i++){
        monstruos.add(new CartaMonstruo("Caballero Real", 6, 2300, 2000));
        }


        for(int i=0;i<4;i++){
        monstruos.add(new CartaMonstruo("Dragon Ancestral", 8, 3000, 2500));
        }

        return monstruos;
    }





    public static List<CartaMagica> crearMagicas() {
        List<CartaMagica> magicas = new ArrayList<>();
        
        // Según el diagrama: 2 de cada una, excepto Fisura y Llamada (x1)
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