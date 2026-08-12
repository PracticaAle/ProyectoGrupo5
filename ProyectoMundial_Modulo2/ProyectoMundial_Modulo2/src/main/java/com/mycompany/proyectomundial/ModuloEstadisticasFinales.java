/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Modulo 6: estadisticas finales y resumen global del torneo.
 */
public class ModuloEstadisticasFinales {

    private static boolean torneoCerrado = false;

    /**
     * Indica si el resumen final ya fue generado.
     * @return
     */
    public static boolean isTorneoCerrado() {
        return torneoCerrado;
    }

    /**
     * Valida que aun se puedan simular partidos.
     * @return
     */
    public static boolean validarSimulacionPermitida() {

        if (torneoCerrado || torneoFinalizado()) {
            JOptionPane.showMessageDialog(null,
                    "El torneo ya finalizo.\n"
                    + "No se permiten simulaciones adicionales.");

            return false;
        }

        return true;
    }

    /**
     * Verifica si la final ya fue disputada y existe campeon.
     * @return
     */
    public static boolean torneoFinalizado() {

        Partido finalTorneo = Eliminacion.obtenerFinal();

        return finalTorneo != null
                && finalTorneo.isJugado()
                && Eliminacion.campeon != null;
    }

    /**
     * Registra asistencia y recaudacion para un partido jugado.
     * @param partido
     */
    public static void registrarMetricasComerciales(Partido partido) {

        if (partido == null) {
            return;
        }

        if (partido.getAsistencia() > 0 || partido.getRecaudacion() > 0) {
            return;
        }

        Random random = new Random();
        Sede sede = partido.getSede();

        if (sede != null) {
            int minimo = (int) (sede.getCapacidad() * 0.60);
            int espacio = sede.getCapacidad() - minimo + 1;
            int asistencia = minimo + random.nextInt(espacio);

            partido.setAsistencia(asistencia);
            partido.setRecaudacion(asistencia * sede.getPrecioEntrada());

        } else {
            int asistencia = 15000 + random.nextInt(50001);
            double precioEntrada = 20 + random.nextInt(81);

            partido.setAsistencia(asistencia);
            partido.setRecaudacion(asistencia * precioEntrada);
        }
    }

    /**
     * Muestra el resumen global del torneo.
     * @param equipos
     * @param calendario
     */
    public static void mostrarResumenFinal(Equipo[] equipos, Partido[] calendario) {

        Partido finalTorneo = Eliminacion.obtenerFinal();

        if (finalTorneo == null || !finalTorneo.isJugado() || Eliminacion.campeon == null) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe finalizar el ultimo partido de la fase eliminatoria.");

            return;
        }

        torneoCerrado = true;

        Equipo campeon = Eliminacion.campeon;
        Equipo subcampeon = obtenerSubcampeon(finalTorneo);

        String mensaje = "===== MODULO 6: RESUMEN FINAL DEL TORNEO =====\n\n";

        mensaje += "===== CORONACION DEL CAMPEON =====\n";
        mensaje += "CAMPEON DEL MUNDO: " + campeon.getNombre() + "\n";

        if (subcampeon != null) {
            mensaje += "SUBCAMPEON: " + subcampeon.getNombre() + "\n";
        }

        mensaje += "\n" + generarTablaGoleadores(equipos);
        mensaje += "\n" + generarReporteDisciplinario(equipos);
        mensaje += "\n" + generarResumenFinanciero(calendario);

        JOptionPane.showMessageDialog(null, mensaje);
    }

    /**
     * Obtiene el equipo perdedor de la final.
     * @param finalTorneo
     * @return
     */
    public static Equipo obtenerSubcampeon(Partido finalTorneo) {

        if (finalTorneo.getGanador() == finalTorneo.getEquipoLocal()) {
            return finalTorneo.getEquipoVisitante();
        }

        if (finalTorneo.getGanador() == finalTorneo.getEquipoVisitante()) {
            return finalTorneo.getEquipoLocal();
        }

        return null;
    }

    /**
     * Genera la tabla Top 5 de goleadores.
     * @param equipos
     * @return
     */
    public static String generarTablaGoleadores(Equipo[] equipos) {

        Jugador[] mejores = new Jugador[5];
        String[] paises = new String[5];

        if (equipos != null) {

            for (int i = 0; i < equipos.length; i++) {

                if (equipos[i] != null && equipos[i].getJugadores() != null) {

                    Jugador[] jugadores = equipos[i].getJugadores();

                    for (int j = 0; j < jugadores.length; j++) {

                        if (jugadores[j] != null) {
                            insertarGoleador(mejores, paises, jugadores[j], equipos[i].getNombre());
                        }
                    }
                }
            }
        }

        String mensaje = "===== TABLA DE GOLEADORES - BOTA DE ORO =====\n";

        for (int i = 0; i < mejores.length; i++) {

            if (mejores[i] != null) {
                mensaje += (i + 1)
                        + ". "
                        + mejores[i].getNombre()
                        + " | Pais: "
                        + paises[i]
                        + " | Goles: "
                        + mejores[i].getGoles()
                        + "\n";
            }
        }

        if (mejores[0] == null) {
            mensaje += "No hay goles registrados.\n";
        }

        return mensaje;
    }

    /**
     * Inserta un jugador en el Top 5 si corresponde.
     * @param mejores
     * @param paises
     * @param jugador
     * @param pais
     */
    private static void insertarGoleador(Jugador[] mejores, String[] paises, Jugador jugador, String pais) {

        for (int i = 0; i < mejores.length; i++) {

            if (mejores[i] == null || jugador.getGoles() > mejores[i].getGoles()) {

                for (int j = mejores.length - 1; j > i; j--) {
                    mejores[j] = mejores[j - 1];
                    paises[j] = paises[j - 1];
                }

                mejores[i] = jugador;
                paises[i] = pais;
                return;
            }
        }
    }

    /**
     * Genera el reporte disciplinario global.
     * @param equipos
     * @return
     */
    public static String generarReporteDisciplinario(Equipo[] equipos) {

        int mayorAmarillas = obtenerMayorAmarillas(equipos);
        int cantidadInfractores = contarInfractores(equipos, mayorAmarillas);
        Jugador[] infractores = new Jugador[cantidadInfractores];
        String[] paises = new String[cantidadInfractores];

        int posicion = 0;

        if (equipos != null) {

            for (int i = 0; i < equipos.length; i++) {

                if (equipos[i] != null && equipos[i].getJugadores() != null) {

                    Jugador[] jugadores = equipos[i].getJugadores();

                    for (int j = 0; j < jugadores.length; j++) {

                        if (esInfractor(jugadores[j], mayorAmarillas)) {
                            infractores[posicion] = jugadores[j];
                            paises[posicion] = equipos[i].getNombre();
                            posicion++;
                        }
                    }
                }
            }
        }

        ordenarInfractores(infractores, paises);

        String mensaje = "===== REPORTE DISCIPLINARIO GLOBAL =====\n";

        if (infractores.length == 0) {
            mensaje += "No hay jugadores con tarjetas rojas ni acumulacion destacada de amarillas.\n";
            return mensaje;
        }

        for (int i = 0; i < infractores.length; i++) {

            int totalIncidencias = infractores[i].getTarjetasAmarillas()
                    + infractores[i].getTarjetasRojas();

            mensaje += (i + 1)
                    + ". "
                    + infractores[i].getNombre()
                    + " | Pais: "
                    + paises[i]
                    + " | Amarillas: "
                    + infractores[i].getTarjetasAmarillas()
                    + " | Rojas: "
                    + infractores[i].getTarjetasRojas()
                    + " | Total incidencias: "
                    + totalIncidencias
                    + "\n";
        }

        return mensaje;
    }

    /**
     * Obtiene la mayor cantidad de tarjetas amarillas acumuladas por un jugador.
     * @param equipos
     * @return
     */
    private static int obtenerMayorAmarillas(Equipo[] equipos) {

        int mayor = 0;

        if (equipos == null) {
            return mayor;
        }

        for (int i = 0; i < equipos.length; i++) {

            if (equipos[i] != null && equipos[i].getJugadores() != null) {

                Jugador[] jugadores = equipos[i].getJugadores();

                for (int j = 0; j < jugadores.length; j++) {

                    if (jugadores[j] != null && jugadores[j].getTarjetasAmarillas() > mayor) {
                        mayor = jugadores[j].getTarjetasAmarillas();
                    }
                }
            }
        }

        return mayor;
    }

    /**
     * Cuenta los jugadores que deben aparecer en el reporte disciplinario.
     * @param equipos
     * @param mayorAmarillas
     * @return
     */
    private static int contarInfractores(Equipo[] equipos, int mayorAmarillas) {

        int cantidad = 0;

        if (equipos == null) {
            return cantidad;
        }

        for (int i = 0; i < equipos.length; i++) {

            if (equipos[i] != null && equipos[i].getJugadores() != null) {

                Jugador[] jugadores = equipos[i].getJugadores();

                for (int j = 0; j < jugadores.length; j++) {

                    if (esInfractor(jugadores[j], mayorAmarillas)) {
                        cantidad++;
                    }
                }
            }
        }

        return cantidad;
    }

    /**
     * Determina si un jugador debe aparecer como infractor destacado.
     * @param jugador
     * @param mayorAmarillas
     * @return
     */
    private static boolean esInfractor(Jugador jugador, int mayorAmarillas) {

        if (jugador == null) {
            return false;
        }

        return jugador.getTarjetasRojas() > 0
                || (mayorAmarillas > 0 && jugador.getTarjetasAmarillas() == mayorAmarillas);
    }

    /**
     * Ordena los infractores de mayor a menor por total de incidencias.
     * @param infractores
     * @param paises
     */
    private static void ordenarInfractores(Jugador[] infractores, String[] paises) {

        for (int i = 0; i < infractores.length; i++) {

            int posicionMayor = i;

            for (int j = i + 1; j < infractores.length; j++) {

                if (totalIncidencias(infractores[j]) > totalIncidencias(infractores[posicionMayor])) {
                    posicionMayor = j;
                }
            }

            Jugador jugadorTemporal = infractores[i];
            String paisTemporal = paises[i];

            infractores[i] = infractores[posicionMayor];
            paises[i] = paises[posicionMayor];

            infractores[posicionMayor] = jugadorTemporal;
            paises[posicionMayor] = paisTemporal;
        }
    }

    /**
     * Calcula el total de incidencias disciplinarias.
     * @param jugador
     * @return
     */
    private static int totalIncidencias(Jugador jugador) {

        if (jugador == null) {
            return 0;
        }

        return jugador.getTarjetasAmarillas() + jugador.getTarjetasRojas();
    }

    /**
     * Genera el resumen financiero y de asistencia.
     * @param calendario
     * @return
     */
    public static String generarResumenFinanciero(Partido[] calendario) {

        Partido[] eliminacion = Eliminacion.obtenerPartidosEliminacion();

        int partidosJugados = contarPartidosJugados(calendario)
                + contarPartidosJugados(eliminacion);

        int asistenciaTotal = sumarAsistencia(calendario)
                + sumarAsistencia(eliminacion);

        double recaudacionTotal = sumarRecaudacion(calendario)
                + sumarRecaudacion(eliminacion);

        int promedioAsistencia = 0;

        if (partidosJugados > 0) {
            promedioAsistencia = asistenciaTotal / partidosJugados;
        }

        String mensaje = "===== RESUMEN FINANCIERO Y ASISTENCIA =====\n";
        mensaje += "Partidos jugados: " + partidosJugados + "\n";
        mensaje += "Asistencia total: " + asistenciaTotal + "\n";
        mensaje += "Promedio de asistencia por partido: " + promedioAsistencia + "\n";
        mensaje += "Recaudacion total: $" + String.format("%.2f", recaudacionTotal) + "\n";

        return mensaje;
    }

    /**
     * Cuenta partidos jugados dentro de un arreglo.
     * @param partidos
     * @return
     */
    public static int contarPartidosJugados(Partido[] partidos) {

        int total = 0;

        if (partidos == null) {
            return total;
        }

        for (int i = 0; i < partidos.length; i++) {

            if (partidos[i] != null && partidos[i].isJugado()) {
                total++;
            }
        }

        return total;
    }

    /**
     * Suma la asistencia de los partidos jugados.
     * @param partidos
     * @return
     */
    private static int sumarAsistencia(Partido[] partidos) {

        int total = 0;

        if (partidos == null) {
            return total;
        }

        for (int i = 0; i < partidos.length; i++) {

            if (partidos[i] != null && partidos[i].isJugado()) {
                total += partidos[i].getAsistencia();
            }
        }

        return total;
    }

    /**
     * Suma la recaudacion de los partidos jugados.
     * @param partidos
     * @return
     */
    private static double sumarRecaudacion(Partido[] partidos) {

        double total = 0;

        if (partidos == null) {
            return total;
        }

        for (int i = 0; i < partidos.length; i++) {

            if (partidos[i] != null && partidos[i].isJugado()) {
                total += partidos[i].getRecaudacion();
            }
        }

        return total;
    }
}

