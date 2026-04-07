import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jugador {
    private String nombre;
    private short Lp = 8000;
    private boolean yaJugoCartaEsteTurno = false;
    private boolean yaAtacoEsteTurno = false; 
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
        this.yaAtacoEsteTurno = false;
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


    private void cambiarPosicionDesdeMenu(Contexto ctx) {
        //  condicional para ver si el campo esta vacio
        if (campo.isEmpty()) {
            System.out.println("No tienes monstruos en campo para cambiar de posición.");
            return;
        }


        // Elegir monstruo
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

        // se elige del campo y se llama a cambiarPosicion
        campo.get(eleccion - 1).cambiarPosicion();
    }
    

    public void jugarCarta(int indice, Contexto ctx) {
        // vuelve a verificar el indice, por si algo fallo en la verificacion de jugarCartaDesdeMenu()
        if (indice < 0 || indice >= mano.size()) return;

        // vuelve a verificar si ya jugo carta este turno
        if (yaJugoCartaEsteTurno) {
            System.out.println("Ya has jugado una carta este turno. No puedes jugar más cartas.");
            return;
        }

        // crea una variable carta para guardar la carta que el usuario quiere jugar
        Carta carta = mano.get(indice);

        // .getTipo trae el tipo de la carta, y con .equals lo compara con lo que hay dentro de los parentesis
        // si retorna TRUE entonces ejecuta el if
        if (carta.getTipo().equals("MONSTRUO")) {

            // crea una variable monstruo, y convierte lo que habia guardado en carta, a CartaMonstruo (asi como cuando se convierte un string a int)
            // se hace esa conversion para poder acceder a todos los atributos y metodos de CartaMonstruo. (Para aclarar, eso solo funciona si es un monstruo, por eso se ejecuta esto dentro del IF)
            CartaMonstruo monstruo = (CartaMonstruo) carta;
            // se añade el monstruo elegido al campo de batalla del jugador activo
            campo.add(monstruo);
            // y aqui se remueve de la mano
            mano.remove(indice);

            // la flag se pone en true para evitar errores
            yaJugoCartaEsteTurno = true;

            // aqui se tiene un if comprimido donde se evalua si es el primer turno, para que las cartas jugadas en el 
            // primer turno no puedan atacar. (todo lo que hay entre parentesis retorna un booleano, y eso se manda como argumento) a .setPuedeAtacar
            monstruo.setPuedeAtacar(ctx.getCampo().isEsPrimerTurno() ? false : true);
            
            System.out.println(nombre + " invocó a " + carta.getNombre() + " en modo ATAQUE.");
            
        } else if (carta.getTipo().equals("MAGICA")) { 
            
            //hace lo mismo que el if pero con los tipos de cartas magicas
            // para este condicional se aprovecha que las cartas magicas tienen implementado el Activable
            if (carta instanceof Activable) {
                // se hace el casteo, y se activa la carta con el contexto de el duelo
                // para entenderlo mejor, basicamente se llama a la carta especifica y ejecuta el .activar de esa carta
                ((Activable) carta).activar(ctx);
                // se remueve de la mano porque ya se jugo
                mano.remove(indice);
                yaJugoCartaEsteTurno = true;
                
                // evalua nuevamente si hay ganador
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
        this.yaAtacoEsteTurno = false; 
        for (CartaMonstruo m : campo) {
            m.setPuedeAtacar(true);
        }
    }

    public void turnoActivo(Contexto ctx) {
        System.out.println("\n=== TURNO DE: " + nombre.toUpperCase() + " (LP: " + Lp + ") ===");

        // al inicio del metodo se pone en false para evitar problemas
        boolean turnoTerminado = false;

        // mientras el turnoTerminado siga siendo false, Y mediante el metodo hay ganador, revisa y retorna un booleano
        // while (FALSE AND FALSE) ejecuta el bloque de codigo
        while (!turnoTerminado && !ctx.getCampo().hayGanador()) {

            // Llama al metodo que retorna toda la informacion a consola de los atributos del jugador activo, para poder
            // tener la informacion necesaria para jugar
            mostrarEstado(ctx);
            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("  1. Jugar una carta");

            // Verifica si existe al menos un monstruo en el campo capaz de atacar.
            // Usa un Stream para recorrer la lista y se detiene (anyMatch) en cuanto encuentra el primero que cumpla la condición.
            boolean hayAtacantes = campo.stream().anyMatch(CartaMonstruo::puedeAtacar);
            

            // entonces lo anterior era para el condicional y mostrar en el menu si puede o no atacar con un monstruo

            // (si no puede es porque no tiene monstruos en campo)
            if (hayAtacantes) {
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

                    // aqui no llama ningun metodo sino que directamente termina el turno con la flag en true
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
        // Se crea un objeto jugador llamado oponente, el cual recibe toda la informacion del oponente mediante contexto
        Jugador oponente = ctx.getOponente();

        // Se muestran primero en consola los datos de el jugador activo:
        System.out.println("\n──────────────────────────────");
        System.out.println("  " + nombre + " | LP: " + Lp
                                //Aqui se hace uso directo del size
            + " | Cartas en mano: " + mano.size()
                                //Aqui se hace uso del size mediante un metodo que retorna el size de mazo
            + " | Cartas en mazo: " + mazo.tamano());
        System.out.println("  Monstruos en campo:");

        // condicional para verificar si hay monstruos en campo
        if (campo.isEmpty()) {
            System.out.println("    (ninguno)");
        } else {
            // si SI HAY, se hace un bucle para recorrer y imprimir cuales hay
            for (int i = 0; i < campo.size(); i++) {
                // aqui hago un .get para tener el monstruo i de la lista
                CartaMonstruo m = campo.get(i);
                // Aqui hago un condicional comprimido para poder guardar en una variable el estado del monstruo
                // el metodo .puedeAtacar() retorna un boolean, si es true "puede atacar" y si es false, "no puede atacar este turno"
                String estado = m.puedeAtacar() ? "puede atacar" : "no puede atacar este turno";
                //Aqui se imprime directamente porque es un bucle entonces para imprimir todos los mosntruos en campo
                System.out.println("    " + (i + 1) + ". " + m + " [" + estado + "]");
            }
        }

        // SE HACE LO MISMO PERO LISTANDO LA INFORMACION DEL OPONENTE
        // lo unico que no se hace es imprimir el estado de cartas del oponente si pueden atacar o no
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

        // condicional para imprimir que si queria jugar una carta, le imprima que no tiene cartas en la mano
        if (mano.isEmpty()) {
            System.out.println("No tienes cartas en la mano.");
            return;
        }

        // condicional para que imprima si ya jugo una carta, que no puede jugar mas cartas
        if (yaJugoCartaEsteTurno) {
            System.out.println("Ya jugaste una carta este turno. No puedes jugar otra.");
            return;
        }

        // si llego hasta este punto, es porque todo esta normal, entonces empieza el bucle para imprimir que cartas
        // tiene en la mano
        System.out.println("\n── Tu mano ──");
        for (int i = 0; i < mano.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + mano.get(i));
        }
        System.out.println("  0. Cancelar");
        System.out.print("Elige una carta: ");

        String input = scanner.nextLine().trim();
        int eleccion;
        // hace un try catch por si el usuario escribe datos raros
        try {
            // el input, que es un string, lo convierte a un integer
            eleccion = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        // condicionales para validar la seleccion del usuario
        if (eleccion == 0) return;
        

        // si la eleccion es <1 OR eleccion supera el tamaño de la mano, manda mensaje y return
        if (eleccion < 1 || eleccion > mano.size()) {
            System.out.println("Número fuera de rango.");
            return;
        }

        // llama al metodo jugar carta ( se resta 1 porque las listas empiezan desde 0)
        // y si yo NO pusiera -1, en realidad estaria buscando una posicion mas adelante que la que yo quiero
        jugarCarta(eleccion - 1, ctx);
    }

    private void atacarDesdeMenu(Contexto ctx) {
        // condicional para que no pueda atacar 2 veces
        if (yaAtacoEsteTurno) {
            System.out.println("Ya realizaste un ataque este turno.");
            return;
        }

        // condicional para que no pueda atacar sin monstruos en campo
        if (campo.isEmpty()) {
            System.out.println("No tienes monstruos en campo.");
            return;
        }


        // nueva lista vacia para llenarla con los monstruos que pueden atacar
        List<CartaMonstruo> disponibles = new ArrayList<>();
        // bucle que recorre los monstruos en campo y los agrega a los disponibles para atacar
        for (CartaMonstruo m : campo) {
            if (m.puedeAtacar()) disponibles.add(m);
        }

        // condicional para evaluar si hay disponibles, si esta vacio entonces no puede atacar ninguno en el turno
        if (disponibles.isEmpty()) {
            System.out.println("Ningún monstruo puede atacar este turno.");
            return;
        }


        // si llego hasta aqui es porque si tienes monstruos disponibles y si puede atacar

        System.out.println("\n── Elige el monstruo atacante ──");

        //bucle que imprime los monstruos para atacar
        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + disponibles.get(i));
        }
        System.out.println("  0. Cancelar");
        System.out.print("Opción: ");

        String input = scanner.nextLine().trim();
        int eleccion;

        //try catch para manejar errores de entrada
        try {
            // para convertir de string a int
            eleccion = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        if (eleccion == 0) return;
        //condicional para evaluar si el INT esta en rango
        if (eleccion < 1 || eleccion > disponibles.size()) {
            System.out.println("Número fuera de rango.");
            return;
        }

        // atributo de tipo CartaMonstruo para guardar la eleccion, (se elimina en los disponibles, la eleccion con el indice )
        CartaMonstruo atacante = disponibles.get(eleccion - 1);
        
        
        // se trae la informacion del oponente para poder atacar sus monstruos o sus Life POints
        Jugador oponente = ctx.getOponente();


        // condicional para evaluar si el campo oponente esta vacio
        if (oponente.getCampo().isEmpty()) {
            
            // si esta vacio entonces ataca directamente sus Life Points
            ctx.getCampo().ataqueDirecto(atacante, oponente);
        } else {
            
            //si no esta vacio, le da a el usuario para elegir que mounstro quiere atacar
            System.out.println("\n── Elige el monstruo a atacar ──");

            // crea una lista con los monstruos del oponente
            List<CartaMonstruo> defensores = oponente.getCampo();

            // la recorre para imprimirla
            for (int i = 0; i < defensores.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + defensores.get(i));
            }
            System.out.println("  0. Cancelar");
            System.out.print("Opción: ");


            String inputDef = scanner.nextLine().trim();
            int eleccionDef;
            
            // otra vez try catch para evitar errores de entrada
            try {
                // conversion de String a int
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

            // de los monstruos en campo del defensor, se elimina el que yo elegi (porque lo ataque)
            CartaMonstruo defensor = defensores.get(eleccionDef - 1);
            
            // se llama al metodo para resolverCombate (para hacer los procesos de ataque ENTRE monstruos)
            ctx.getCampo().resolverCombate(atacante, defensor, this, oponente);
        }

        // al final de todo el codigo pone la flag en True, para que no permita que ataque mas veces
        yaAtacoEsteTurno = true;
    }
}   