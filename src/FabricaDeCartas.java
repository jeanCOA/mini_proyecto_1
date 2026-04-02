import java.util.ArrayList;
import java.util.List;

public class FabricaDeCartas {

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