/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;
//s
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author usuario
 */
public class ModuloCalendario {

    /**
     * Este metodo genera partidos grupo por grupo y les asigna arbitro y sede
     * automaticamente
     *
     * @param grupos
     * @return
     */
    public static Partido[] generarCalendarioGrupos(Grupo grupos[], Sede sedes[], CuerpoArbitral arbitros[]) {

        //Verificar primero que hayan grupos ya creados
        if (grupos == null) {
            JOptionPane.showMessageDialog(null, "Primero debe generar los grupos.");

            //finalizar el metodo de no haber grupos creados
            return null;
        }

        //Cada grupo tendra 6 partidos
        Partido calendario[] = new Partido[grupos.length * 6];

        //Posicion de cada partido
        int posicion = 0;

        //Recorrer grupos
        for (int i = 0; i < grupos.length; i++) {

            //obtener los equipos de cada grupo
            Equipo equipos[] = grupos[i].getEquipos();

            //primer equipo
            for (int j = 0; j < equipos.length; j++) {

                //j+1 pq sino se hacen partidos repetidos
                for (int k = j + 1; k < equipos.length; k++) {

                    calendario[posicion] = new Partido(equipos[j], equipos[k]);

                    posicion = posicion + 1;
                }
            }
        }

        //Asignar sedes y arbitros de una
        asignarArbitrosAleatorios(calendario, arbitros);
        asignarSedesCalendario(calendario, sedes);

        JOptionPane.showMessageDialog(null, "Calendario generado correctamente");

        return calendario;
    }


    /**
     * Asigna la sede a los partidos
     *
     * @param calendario
     * @param sedes
     */
    public static void asignarSedesCalendario(Partido calendario[], Sede sedes[]) {

        if (calendario == null) {
            JOptionPane.showMessageDialog(null, "Genere primero el calendario");
            return;
        }

        //Ver si ya hay sedes
        if (sedes == null || sedes.length == 0) {
            JOptionPane.showMessageDialog(null, "Primero registre las sedes");
            return;
        }

        int posicionSede = 0;

        for (int i = 0; i < calendario.length; i++) {

            //Asignar sede
            calendario[i].setSede(sedes[posicionSede]);

            posicionSede++;

            //Si llega al final del arreglo de sedes vuelve al inicio
            if (posicionSede == sedes.length) {
                posicionSede = 0;
            }
        }
    }


    /**
     * Asigna arbitro a cada partido
     *
     * @param calendario
     * @param arbitros
     */
    public static void asignarArbitrosAleatorios(Partido calendario[], CuerpoArbitral arbitros[]) {

        if (calendario == null) {
            JOptionPane.showMessageDialog(null, "Primero genere el calendario");
            return;
        }

        if (arbitros.length == 0) {
            JOptionPane.showMessageDialog(null, "Registre primero los arbitros del torneo");
            return;
        }

        Random aleatorio = new Random();

        for (int i = 0; i < calendario.length; i++) {

            int posicionRandom = aleatorio.nextInt(arbitros.length);

            calendario[i].setArbitroAsignado(arbitros[posicionRandom]);
        }
    }


    /**
     * Muestra los partidos que le va a tocar a cada grupo
     *
     * @param calendario
     * @param grupos
     */
    public static void mostrarPartidosPorGrupo(Partido calendario[], Grupo grupos[]) {

        //verificar que se haya creado el calendario
        if (calendario == null) {
            JOptionPane.showMessageDialog(null, "Primero debe generar los partidos.");
            return;
        }

        int posicion = 0;

        //Recorrer grupos
        for (int i = 0; i < grupos.length; i++) {

            String mensajeGrupo = "=======" + grupos[i].getNombreGrupo() + "=======";

            //Ahora sus 6 partidos
            for (int j = 0; j < 6; j++) {

                Partido partido = calendario[posicion];

                mensajeGrupo += "\n" + j + ". "
                        + partido.getEquipoLocal().getNombre()
                        + " vs "
                        + partido.getEquipoVisitante().getNombre()
                        + "\n";

                posicion++;
            }

            JOptionPane.showMessageDialog(null, mensajeGrupo);
        }
    }


    /**
     * Obtiene el siguiente partido que no haya sido jugado
     *
     * @param calendario
     * @return
     */
    public static Partido obtenerSiguientePartido(Partido calendario[]) {

        if (calendario == null) {
            JOptionPane.showMessageDialog(null, "Genere el calendario primero");
            return null;
        }

        for (int i = 0; i < calendario.length; i++) {

            //Almacena partido actual
            Partido partido = calendario[i];

            //verificar si el partido ya se jugo
            if (partido.isJugado() == false) {

                JOptionPane.showMessageDialog(
                        null,
                        "Siguiente partido: \n"
                        + partido.getEquipoLocal().getNombre()
                        + "\nvs \n"
                        + partido.getEquipoVisitante().getNombre()
                );

                return partido;
            }
        }

        JOptionPane.showMessageDialog(null, "Ya no quedaron partidos pendientes");

        return null;
    }


    /**
     * Este metodo simula un solo partido y muestra los resultados
     *
     * @param partido
     * @param mostrarResultado
     */
    public static void simularPartidoAPartido(Partido partido, boolean mostrarResultado) {

        if (partido == null) {
            JOptionPane.showMessageDialog(null, "No hay partido por simular");
            return;
        }

        if (partido.isJugado() == true) {
            JOptionPane.showMessageDialog(null, "Este partido ya fue jugado");
            return;
        }

        //Goles random para cada equipo
        Random random = new Random();

        int golesLocal = random.nextInt(10);
        int golesVisitante = random.nextInt(10);

        //Asignar goles del partido
        partido.setGolesLocal(golesLocal);
        partido.setGolesVisitante(golesVisitante);

        //Asignar a los equipos goles a favor y en contra
        partido.getEquipoLocal().setGolesFavor(
                partido.getEquipoLocal().getGolesFavor() + golesLocal
        );

        partido.getEquipoLocal().setGolesContra(
                partido.getEquipoLocal().getGolesContra() + golesVisitante
        );

        partido.getEquipoVisitante().setGolesFavor(
                partido.getEquipoVisitante().getGolesFavor() + golesVisitante
        );

        partido.getEquipoVisitante().setGolesContra(
                partido.getEquipoVisitante().getGolesContra() + golesLocal
        );

        //Simular goleadores del partido
        String goleadoresLocal = simularGoles(
                partido.getEquipoLocal(),
                golesLocal
        );

        String goleadoresVisitante = simularGoles(
                partido.getEquipoVisitante(),
                golesVisitante
        );

        //Generar las tarjetas para los jugadores
        String tarjetasLocal = generarTarjetas(
                partido.getEquipoLocal()
        );

        String tarjetasVisitante = generarTarjetas(
                partido.getEquipoVisitante()
        );

        //Actualizar tabla de grupo
        calcularPuntos(partido);
        //Registrar asistencia y recaudacion
        ModuloEstadisticasFinales.registrarMetricasComerciales(partido);

        //Partido ya jugado
        partido.setJugado(true);

        if (mostrarResultado == true) {

            JOptionPane.showMessageDialog(
                    null,
                    "RESULTADO\n\n"
                    + partido.getEquipoLocal().getNombre()
                    + " " + golesLocal
                    + " - "
                    + golesVisitante + " "
                    + partido.getEquipoVisitante().getNombre()

                    + "\n\nGOLEADORES\n\n"

                    + partido.getEquipoLocal().getNombre() + ":\n"
                    + goleadoresLocal

                    + "\n"
                    + partido.getEquipoVisitante().getNombre() + ":\n"
                    + goleadoresVisitante

                    + "\nTARJETAS\n\n"

                    + partido.getEquipoLocal().getNombre() + ":\n"
                    + tarjetasLocal

                    + "\n"
                    + partido.getEquipoVisitante().getNombre() + ":\n"
                    + tarjetasVisitante
            );
        }
    }


    /**
     * Este metodo genera goles a jugadores de manera random
     *
     * @param equipo
     * @param cantidadGoles
     */
    public static String simularGoles(Equipo equipo, int cantidadGoles) {

        //obtener jugadores del equipo
        Jugador jugadores[] = equipo.getJugadores();

        //filtro
        if (jugadores == null || jugadores.length == 0) {
            return "No hay jugadores registrados\n";
        }

        Random random = new Random();

        String goleadores = "";

        for (int i = 0; i < cantidadGoles; i++) {

            int index = random.nextInt(jugadores.length);

            Jugador goleador = jugadores[index];

            goleador.setGoles(
                    goleador.getGoles() + 1
            );

            goleadores = goleadores
                    + goleador.getNombre()
                    + "\n";
        }

        return goleadores;
    }


    /**
     * Este metodo asigna tarjetas a jugadores de forma random
     *
     * @param equipo
     * @return
     */
    public static String generarTarjetas(Equipo equipo) {

        Jugador jugadores[] = equipo.getJugadores();

        //filtro
        if (jugadores == null || jugadores.length == 0) {
            return "No hay jugadores registrados\n";
        }

        Random random = new Random();

        //Generar cantidad random de amarillas
        int cantidadAmarillas = random.nextInt(9);

        //maximo cuatro rojas
        int cantidadRojas = random.nextInt(5);

        String tarjetas = "";

        //Asignacion de amarillas
        for (int i = 0; i < cantidadAmarillas; i++) {

            int index = random.nextInt(jugadores.length);

            Jugador jugadorAmonestado = jugadores[index];

            jugadorAmonestado.setTarjetasAmarillas(
                    jugadorAmonestado.getTarjetasAmarillas() + 1
            );

            tarjetas = tarjetas
                    + jugadorAmonestado.getNombre()
                    + " - Amarilla\n";

            //Dos amarillas generan una tarjeta roja
            if (jugadorAmonestado.getTarjetasAmarillas() == 2) {

                jugadorAmonestado.setTarjetasRojas(
                        jugadorAmonestado.getTarjetasRojas() + 1
                );

                jugadorAmonestado.setTarjetasAmarillas(0);

                tarjetas = tarjetas
                        + jugadorAmonestado.getNombre()
                        + " - Roja por doble amarilla\n";
            }
        }

        //Asignacion de rojas directas
        for (int i = 0; i < cantidadRojas; i++) {

            int index = random.nextInt(jugadores.length);

            Jugador jugadorAmonestado = jugadores[index];

            jugadorAmonestado.setTarjetasRojas(
                    jugadorAmonestado.getTarjetasRojas() + 1
            );

            tarjetas = tarjetas
                    + jugadorAmonestado.getNombre()
                    + " - Roja directa\n";
        }

        if (tarjetas.equals("")) {
            tarjetas = "No hubo tarjetas\n";
        }

        return tarjetas;
    }


    /**
     * Calcula los puntos de los equipos dependiendo del resultado
     *
     * @param partido
     */
    public static void calcularPuntos(Partido partido) {

        Equipo local = partido.getEquipoLocal();

        Equipo visitante = partido.getEquipoVisitante();

        int golesLocal = partido.getGolesLocal();

        int golesVisitante = partido.getGolesVisitante();

        //Si gana el equipo local
        if (golesLocal > golesVisitante) {

            local.setPuntos(
                    local.getPuntos() + 3
            );

        //Si gana el equipo visitante
        } else if (golesVisitante > golesLocal) {

            visitante.setPuntos(
                    visitante.getPuntos() + 3
            );

        //Si empatan
        } else {

            local.setPuntos(
                    local.getPuntos() + 1
            );

            visitante.setPuntos(
                    visitante.getPuntos() + 1
            );
        }
    }


    /**
     * Simula todos los partidos pendientes del calendario
     *
     * @param calendario
     */
    public static void simularFaseCompleta(Partido[] calendario) {

        //Verifica que exista el calendario
        if (calendario == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Primero debe generar el calendario."
            );

            return;
        }

        int partidosSimulados = 0;

        //Recorre todos los partidos del calendario
        for (int i = 0; i < calendario.length; i++) {

            //Solo simula los partidos que todavía no se han jugado
            if (calendario[i].isJugado() == false) {

                simularPartidoAPartido(
                        calendario[i],
                        false
                );

                partidosSimulados++;
            }
        }

        JOptionPane.showMessageDialog(
                null,
                "La fase completa simulada.\n"
                + "Partidos simulados: "
                + partidosSimulados
        );
    }
}
    


