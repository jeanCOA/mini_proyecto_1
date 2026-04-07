# Simulación de Duelo Yu-Gi-Oh! en Consola

> *"Confía en el corazón de las cartas"* — Yugi Muto

Mini Proyecto 1 — Programación Orientada a Eventos | Universidad | Java 21

---

##  Descripción del Proyecto

Este proyecto es una simulación completa de un duelo de Yu-Gi-Oh! que se ejecuta enteramente por consola, desarrollado como Mini Proyecto 1 de la asignatura **Programación Orientada a Eventos**.

El objetivo fue construir un sistema funcional que modele las mecánicas esenciales del juego de cartas, aplicando correctamente los principios fundamentales de la **Programación Orientada a Objetos**: encapsulamiento, herencia, clases abstractas, interfaces y polimorfismo. El juego permite que dos jugadores se enfrenten en turnos, jueguen cartas de monstruo y magia, realicen combates y acumulen o pierdan puntos de vida hasta que uno de los dos gane el duelo.

Todo fue implementado en **Java 21** sin librerías externas, usando únicamente el estándar `java.util`.

---

## Integrantes del Grupo

| Nombre | 
|---|
| Joseph Andrey Puerta |
| Juan Pablo Rada |
| Jean Carlo Ospina |

---

##  Requisitos Técnicos

- **Java Development Kit (JDK) 21** o superior
- Terminal o consola del sistema operativo (CMD, PowerShell, bash, zsh, etc.)
- No se requieren dependencias externas ni frameworks adicionales

Para verificar tu versión de Java instalada:

```bash
java -version
```

---

## Cómo Compilar y Ejecutar

### 1. Compilar todos los archivos `.java`

Desde la raíz del proyecto, ejecuta:

```bash
javac *.java
```

### 2. Ejecutar el juego

```bash
java App
```

Al iniciar, el sistema pedirá el nombre de cada jugador y comenzará el duelo automáticamente.



---

##  Características Implementadas

### Sistema de Cartas
- Clase abstracta `Carta` como base de la jerarquía
- `CartaMonstruo`: con atributos ATK, DEF y nivel
- `CartaMagica`: abstracta, implementa la interfaz `Activable`
- **6 cartas mágicas** únicas con efectos distintos:
  -  **Pot of Greed** — Roba 2 cartas del mazo
  -  **Espada de Zeus** — +500 ATK a un monstruo propio por 1 turno
  -  **Escudo de Atenea** — +800 DEF a un monstruo propio por 1 turno
  -  **Cura Milagrosa** — Recupera 1000 LP
  -  **Fisura** — Destruye el monstruo con menor ATK del oponente
  -  **Llamada del Abismo** — Roba 1 carta, pero pierdes 500 LP

### Sistema de Turnos
- Inicio al azar con anuncio en consola
- Robo automático de 1 carta al inicio de cada turno
- Límite de **1 carta por turno** (monstruo o magia)
- El jugador elige sus acciones mediante menús numerados

### Sistema de Combate
- Combate entre monstruos: ATK vs ATK, con daño a LP si aplica
- Modo defensa: el atacante no inflige daño a LP si no supera la DEF
- Ataque directo cuando el oponente no tiene monstruos en campo
- Cada monstruo puede atacar una sola vez por turno

### Condiciones de Victoria
- Un jugador llega a **0 LP**
- Un jugador intenta robar y su **mazo está vacío**

### Interfaz de Consola
- Menú de bienvenida con los nombres de los duelistas
- Estado completo del campo en cada turno: LP, cartas en mano, monstruos en campo
- Mensajes descriptivos para cada acción
- Mensaje temático al anunciar al ganador

---




##  Estructura del Proyecto

```
 proyecto-yugioh
 ├── App.java                  # Punto de entrada, lee nombres y lanza el duelo
 ├── Carta.java                # Clase abstracta base de la jerarquía
 ├── CartaMonstruo.java        # Subclase concreta con ATK, DEF, nivel y modo
 ├── CartaMagica.java          # Subclase abstracta que implementa Activable
 ├── Activable.java            # Interfaz con método activar(Contexto)
 ├── Contexto.java             # Objeto que agrupa jugador activo, oponente y campo
 ├── Jugador.java              # Estado del jugador: mano, campo, LP, lógica de turno
 ├── Mazo.java                 # Gestión del mazo: barajar, robar, repartir
 ├── CampoBatalla.java         # Lógica del duelo: turnos, combate, condiciones de victoria
 ├── FabricaDeCartas.java      # Crea e instancia el mazo completo de 40 cartas
 ├── PotOfGreed.java           # Carta mágica: robar 2 cartas
 ├── EspadaDeZeus.java         # Carta mágica: +500 ATK temporal
 ├── EscudoDeAtenea.java       # Carta mágica: +800 DEF temporal
 ├── CuraMilagrosa.java        # Carta mágica: +1000 LP
 ├── Fisura.java               # Carta mágica: destruir monstruo con menor ATK
 └── LlamadaDelAbismo.java     # Carta mágica: robar 1 carta / -500 LP
```

---


- La jerarquía `Carta → CartaMonstruo / CartaMagica → subclases de magia`
- La interfaz `Activable` y quién la implementa
- Las relaciones entre `Jugador`, `Mazo`, `CampoBatalla` y `Contexto`

---

