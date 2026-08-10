package com.mycompany.proyectomundial;

import java.util.Random;
import javax.swing.JOptionPane;

public class Eliminacion {
    public static Equipo campeon;
    public static Jugador botaOro;
    public static Jugador mejorJugador;
    public static CuerpoArbitral mejorArbitro;

    private static Partido[] ronda32;
    private static Partido[] octavos;
    private static Partido[] cuartos;
    private static Partido[] semifinales;
    private static Partido[] finalPartido;

    /**
     * Ordena los equipos por puntos y diferencia de goles.
     * @param equipos 
     */
    public static void ordenarEquipos(Equipo[] equipos) {

        for (int i = 0; i < equipos.length; i++) {

            int posicionMejor = i;

            for (int j = i; j < equipos.length; j++) {

                int diferenciaActual = equipos[j].getGolesFavor() - equipos[j].getGolesContra();

                int diferenciaMejor = equipos[posicionMejor].getGolesFavor() - equipos[posicionMejor].getGolesContra();

                if (equipos[j].getPuntos() > equipos[posicionMejor].getPuntos()) {
                    posicionMejor = j;

                } else if (equipos[j].getPuntos() == equipos[posicionMejor].getPuntos() && diferenciaActual > diferenciaMejor) {
                    posicionMejor = j;
                }
            }

            Equipo temporal = equipos[i];

            equipos[i] = equipos[posicionMejor];

            equipos[posicionMejor] = temporal;
        }
    }

    /**
     * Obtiene el primer equipo de cada grupo.
     * @param grupos
     * @return 
     */
    public static Equipo[] obtenerPrimeros(Grupo[] grupos) {

        Equipo[] primeros = new Equipo[grupos.length];

        for (int i = 0; i < grupos.length; i++) {

            Equipo[] equiposGrupo = copiarEquipos(grupos[i].getEquipos());
            
            ordenarEquipos(equiposGrupo);
            
            primeros[i] = equiposGrupo[0];
        }

        return primeros;
    }

    /**
     * Obtiene el segundo equipo de cada grupo.
     * @param grupos
     * @return 
     */
    public static Equipo[] obtenerSegundos(Grupo[] grupos) {
        
        Equipo[] segundos = new Equipo[grupos.length];

        for (int i = 0; i < grupos.length; i++) {

            Equipo[] equiposGrupo = copiarEquipos(grupos[i].getEquipos());

            ordenarEquipos(equiposGrupo);

            segundos[i] = equiposGrupo[1];
        }

        return segundos;
    }

    /**
     * Obtiene el tercer equipo de cada grupo.
     * @param grupos
     * @return 
     */
    public static Equipo[] obtenerTerceros(Grupo[] grupos) {

        Equipo[] terceros = new Equipo[grupos.length];

        for (int i = 0; i < grupos.length; i++) {

            Equipo[] equiposGrupo = copiarEquipos(grupos[i].getEquipos());

            ordenarEquipos(equiposGrupo);

            terceros[i] = equiposGrupo[2];
        }

        return terceros;
    }

    /**
     * Obtiene los mejores terceros según la cantidad de equipos que sea necesaria.
     * @param grupos
     * @param cantidadEquipos
     * @return 
     */
    public static Equipo[] obtenerMejoresTerceros(Grupo[] grupos, int cantidadEquipos) {

        Equipo[] terceros = obtenerTerceros(grupos);
        ordenarEquipos(terceros);

        int cantidadMejores = 0;

        if (cantidadEquipos == 24) {
            cantidadMejores = 4;

        } else if (cantidadEquipos == 48) {
            cantidadMejores = 8;
        }

        Equipo[] mejoresTerceros = new Equipo[cantidadMejores];

        for (int i = 0; i < cantidadMejores; i++) {
            mejoresTerceros[i] = terceros[i];
        }

        return mejoresTerceros;
    }

    /**
     * Obtiene los equipos clasificados a la fase de eliminación directa.
     * @param grupos
     * @param cantidadEquipos
     * @return 
     */
    public static Equipo[] obtenerClasificados(Grupo[] grupos, int cantidadEquipos) {

        Equipo[] primeros = obtenerPrimeros(grupos);
        Equipo[] segundos = obtenerSegundos(grupos);
        Equipo[] mejoresTerceros = obtenerMejoresTerceros(grupos, cantidadEquipos);

        int cantidadClasificados = 16;

        if (cantidadEquipos == 48 || cantidadEquipos == 64) {
            cantidadClasificados = 32;
        }

        Equipo[] clasificados = new Equipo[cantidadClasificados];

        int posicion = 0;

        for (int i = 0; i < primeros.length; i++) {
            clasificados[posicion] = primeros[i];
            posicion++;
        }

        for (int i = 0; i < segundos.length; i++) {
            clasificados[posicion] = segundos[i];
            posicion++;
        }

        for (int i = 0; i < mejoresTerceros.length; i++) {
            clasificados[posicion] = mejoresTerceros[i];
            posicion++;
        }

        return clasificados;
    }

    /**
     * Muestra los equipos clasificados a la fase de eliminación directa.
     * @param grupos
     * @param cantidadEquipos 
     */
    public static void mostrarClasificados(Grupo[] grupos, int cantidadEquipos) {
        Equipo[] clasificados = obtenerClasificados(grupos, cantidadEquipos);

        String mensaje = "===== EQUIPOS CLASIFICADOS =====\n\n";

        for (int i = 0; i < clasificados.length; i++) {
            mensaje += (i + 1)
                    + ". "
                    + clasificados[i].getNombre()
                    + "\n";
        }

        JOptionPane.showMessageDialog(null, mensaje);
    }

    /**
     * Crea las llaves en la fase de eliminación directa.
     * @param grupos
     * @param cantidadEquipos 
     */
    public static void crearLlaves(Grupo[] grupos, int cantidadEquipos) {
        Equipo[] clasificados = obtenerClasificados(grupos, cantidadEquipos);

        if (clasificados.length == 16) {
            octavos = crearRonda(clasificados, "Octavos");
            ronda32 = null;

        } else {
            ronda32 = crearRonda(clasificados, "Ronda de 32");
            octavos = null;
        }

        cuartos = null;
        semifinales = null;
        finalPartido = null;
        campeon = null;

        JOptionPane.showMessageDialog(null,"Llaves creadas correctamente.");
    }

    /**
     * Crea los partidos correspondientes a una ronda.
     * @param equipos
     * @param fase
     * @return 
     */
    private static Partido[] crearRonda(Equipo[] equipos, String fase) {
        Partido[] partidos = new Partido[equipos.length / 2];

        for (int i = 0; i < partidos.length; i++) {
            partidos[i] = new Partido(equipos[i * 2], equipos[i * 2 + 1]);
            partidos[i].setFase(fase);
        }
        return partidos;
    }

    /**
     * Simula el siguiente partido de la fase de eliminación directa.
     */
    public static void simularSiguientePartido() {

        Partido partido = obtenerSiguientePartidoEliminacion();

        if (partido == null) {
            JOptionPane.showMessageDialog(null, "No hay partidos eliminatorios pendientes.");
            return;
        }

        simularPartidoEliminacion(partido, true);
    }

    /**
     * Simula un partido y asigna un equipo ganador.
     * @param partido
     * @param mostrarResultado 
     */
    public static void simularPartidoEliminacion(Partido partido, boolean mostrarResultado) {

        if (partido.isJugado()) {
            return;
        }

        Random random = new Random();

        int golesLocal = random.nextInt(6);
        int golesVisitante = random.nextInt(6);

        partido.setGolesLocal(golesLocal);
        partido.setGolesVisitante(golesVisitante);

        Equipo ganador;

        String penales = "";

        if (golesLocal > golesVisitante) {
            ganador = partido.getEquipoLocal();

        } else if (golesVisitante > golesLocal) {
            ganador = partido.getEquipoVisitante();

        } else {
            int penalesLocal = random.nextInt(6);
            int penalesVisitante = random.nextInt(6);

            while (penalesLocal == penalesVisitante) {
                penalesLocal = random.nextInt(6);
                penalesVisitante = random.nextInt(6);
            }

            if (penalesLocal > penalesVisitante) {
                ganador = partido.getEquipoLocal();

            } else {
                ganador = partido.getEquipoVisitante();
            }

            penales = "\nPENALES: "
                    + partido.getEquipoLocal().getNombre()
                    + " "
                    + penalesLocal
                    + " - "
                    + penalesVisitante
                    + " "
                    + partido.getEquipoVisitante().getNombre()
                    + "\n";
        }

        partido.setGanador(ganador);
        partido.setJugado(true);
        prepararSiguienteRonda(partido);

        if (mostrarResultado) {

            JOptionPane.showMessageDialog(null, "===== "
                    + partido.getFase().toUpperCase()
                    + " =====\n\n"
                    + partido.getEquipoLocal().getNombre()
                    + " "
                    + golesLocal
                    + " - "
                    + golesVisitante
                    + " "
                    + partido.getEquipoVisitante().getNombre()
                    + penales
                    + "\nGANADOR: "
                    + ganador.getNombre()
            );
        }
    }

    /**
     * Simula todos los partidos pendientes de la eliminación.
     */
    public static void simularFaseEliminatoriaCompleta() {

        if (ronda32 == null && octavos == null) {
            JOptionPane.showMessageDialog(null, "Primero debe crear las llaves.");
            return;
        }

        simularRonda(ronda32);
        simularRonda(octavos);
        simularRonda(cuartos);
        simularRonda(semifinales);
        simularRonda(finalPartido);

        JOptionPane.showMessageDialog(null, "Fase de eliminación completada.");
    }

    /**
     * Simula todos los partidos de una ronda.
     * @param ronda 
     */
    private static void simularRonda(Partido[] ronda) {

        if (ronda == null) {
            return;
        }

        for (int i = 0; i < ronda.length; i++) {

            if (!ronda[i].isJugado()) {
                simularPartidoEliminacion(ronda[i], false);
            }
        }
    }

    /**
     * Crea la siguiente ronda con los equipos ganadores.
     * @param partido 
     */
    private static void prepararSiguienteRonda(Partido partido) {

        Partido[] rondaActual = obtenerRondaDePartido(partido);

        if (rondaActual == null) {
            return;
        }

        if (!todosJugados(rondaActual)) {
            return;
        }

        Equipo[] ganadores = new Equipo[rondaActual.length];

        for (int i = 0; i < rondaActual.length; i++) {
            ganadores[i] = rondaActual[i].getGanador();
        }

        if (rondaActual == ronda32) {
            octavos = crearRonda(ganadores, "Octavos");

        } else if (rondaActual == octavos) {
            cuartos = crearRonda(ganadores,"Cuartos");

        } else if (rondaActual == cuartos) {
            semifinales = crearRonda(ganadores,"Semifinales");

        } else if (rondaActual == semifinales) {
            finalPartido = crearRonda(ganadores,"Final");

        } else if (rondaActual == finalPartido) {
            campeon = rondaActual[0].getGanador();

            JOptionPane.showMessageDialog(null, "CAMPEÓN DEL MUNDO:\n\n" + campeon.getNombre());
        }
    }

    /**
     * Obtiene la ronda a la que pertenece un partido.
     * @param partido
     * @return 
     */
    private static Partido[] obtenerRondaDePartido(Partido partido) {

        if (contienePartido(ronda32, partido)) {
            return ronda32;
        }

        if (contienePartido(octavos, partido)) {
            return octavos;
        }

        if (contienePartido(cuartos, partido)) {
            return cuartos;
        }

        if (contienePartido(semifinales, partido)) {
            return semifinales;
        }

        if (contienePartido(finalPartido, partido)) {
            return finalPartido;
        }

        return null;
    }

    /**
     * Verifica si una ronda contiene un partido específico.
     * @param ronda
     * @param partido
     * @return 
     */
    private static boolean contienePartido(Partido[] ronda, Partido partido) {

        if (ronda == null) {
            return false;
        }

        for (int i = 0; i < ronda.length; i++) {
            if (ronda[i] == partido) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifica que todos los partidos de una ronda han terminado.
     * @param ronda
     * @return 
     */
    private static boolean todosJugados(Partido[] ronda) {

        for (int i = 0; i < ronda.length; i++) {
            if (!ronda[i].isJugado()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Obtiene el siguiente partido eliminatorio pendiente.
     * @return 
     */
    private static Partido obtenerSiguientePartidoEliminacion() {

        Partido[][] rondas = {
            ronda32, octavos, cuartos, semifinales, finalPartido
        };

        for (int i = 0; i < rondas.length; i++) {
            if (rondas[i] != null) {
                for (int j = 0; j < rondas[i].length; j++) {
                    if (!rondas[i][j].isJugado()) {
                        return rondas[i][j];
                    }
                }
            }
        }

        return null;
    }

    /**
     * Muestra el bracket completo de la fase de eliminación directa.
     */
    public static void mostrarBracket() {

        if (ronda32 == null && octavos == null) {
            JOptionPane.showMessageDialog(null, "Primero debe crear las llaves.");

            return;
        }

        String mensaje = "===== BRACKET DEL MUNDIAL =====\n\n";

        mensaje += mostrarRonda(ronda32);
        mensaje += mostrarRonda(octavos);
        mensaje += mostrarRonda(cuartos);
        mensaje += mostrarRonda(semifinales);
        mensaje += mostrarRonda(finalPartido);

        if (campeon != null) {

            mensaje += "\n==============================\n"
                    + "CAMPEÓN DEL MUNDO: "
                    + campeon.getNombre()
                    + "\n==============================\n";
        }

        JOptionPane.showMessageDialog(null, mensaje);
    }

    /**
     * Muestra una ronda de partidos.
     * @param ronda
     * @return 
     */
    private static String mostrarRonda(Partido[] ronda) {

        if (ronda == null) {
            return "";
        }

        String mensaje = "===== " + ronda[0].getFase().toUpperCase() + " =====\n";

        for (int i = 0; i < ronda.length; i++) {

            Partido partido = ronda[i];

            mensaje += partido.getEquipoLocal().getNombre()
                    + " "
                    + partido.getGolesLocal()
                    + " - "
                    + partido.getGolesVisitante()
                    + " "
                    + partido.getEquipoVisitante().getNombre();

            if (partido.isJugado()) {
                mensaje += " → Ganador: " + partido.getGanador().getNombre();

            } else {
                mensaje += " → Pendiente";
            }

            mensaje += "\n";
        }

        mensaje += "\n";

        return mensaje;
    }

    /**
     * Retorna el partido final del torneo.
     * @return
     */
    public static Partido obtenerFinal() {

        if (finalPartido == null || finalPartido.length == 0) {
            return null;
        }

        return finalPartido[0];
    }

    /**
     * Retorna todos los partidos eliminatorios en un solo arreglo.
     * @return
     */
    public static Partido[] obtenerPartidosEliminacion() {

        int total = contarPartidos(ronda32)
                + contarPartidos(octavos)
                + contarPartidos(cuartos)
                + contarPartidos(semifinales)
                + contarPartidos(finalPartido);

        Partido[] partidos = new Partido[total];
        int posicion = 0;

        posicion = copiarPartidos(ronda32, partidos, posicion);
        posicion = copiarPartidos(octavos, partidos, posicion);
        posicion = copiarPartidos(cuartos, partidos, posicion);
        posicion = copiarPartidos(semifinales, partidos, posicion);
        copiarPartidos(finalPartido, partidos, posicion);

        return partidos;
    }

    /**
     * Cuenta los partidos de una ronda.
     * @param ronda
     * @return
     */
    private static int contarPartidos(Partido[] ronda) {

        if (ronda == null) {
            return 0;
        }

        return ronda.length;
    }

    /**
     * Copia partidos de una ronda dentro de un arreglo destino.
     * @param origen
     * @param destino
     * @param posicion
     * @return
     */
    private static int copiarPartidos(Partido[] origen, Partido[] destino, int posicion) {

        if (origen == null) {
            return posicion;
        }

        for (int i = 0; i < origen.length; i++) {
            destino[posicion] = origen[i];
            posicion++;
        }

        return posicion;
    }

    /**
     * Crea una copia del arreglo "equipos" para no alterar el arreglo original.
     * @param equipos
     * @return 
     */
    private static Equipo[] copiarEquipos(Equipo[] equipos) {

        Equipo[] copia = new Equipo[equipos.length];

        for (int i = 0; i < equipos.length; i++) {
            copia[i] = equipos[i];
        }

        return copia;
    }
}
