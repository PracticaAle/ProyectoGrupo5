package com.mycompany.proyectomundial;

import javax.swing.JOptionPane;

/**
 *
 * @author Dylan
 */
public class Equipo {

    private String nombre;
    private String dt;
    private Jugador[] jugadores;
    private int golesFavor;
    private int golesContra;
    private int puntos;

    // Constructor
    public Equipo(String nombre, String dt, Jugador[] jugadores, int golesFavor, int golesContra, int puntos) {
        this.nombre = nombre;
        this.dt = dt;
        this.jugadores = jugadores;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.puntos = puntos;
    }

    // Gets y Sets
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public void setJugadores(Jugador[] jugadores) {
        this.jugadores = jugadores;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Metodo para agregar un equipo.
     * @param equipos 
     */
    public static void agregarEquipo(Equipo[] equipos) {

        // Buscar la primera posición libre
        int posicion = -1;

        for (int i = 0; i < equipos.length; i++) {

            if (equipos[i] == null) {
                posicion = i;
                break;
            }
        }

        if (posicion == -1) {

            JOptionPane.showMessageDialog(null,"No hay espacio para más equipos.");
            return;
        }

        // Pedir datos del equipo
        String nombre = JOptionPane.showInputDialog("Nombre del país:");

        String dt = JOptionPane.showInputDialog("Director Técnico:");

        // Crear arreglo de 11 jugadores
        Jugador[] jugadores = new Jugador[11];

        // Registrar los 11 jugadores
        for (int i = 0; i < jugadores.length; i++) {

            String nombreJugador = JOptionPane.showInputDialog("Ingrese el nombre del jugador #" + (i + 1) + ":");

            jugadores[i] = new Jugador(nombreJugador, 0, 0, 0);
        }

        // Crear el equipo
        equipos[posicion] = new Equipo(nombre, dt, jugadores, 0, 0, 0);

        JOptionPane.showMessageDialog(null, "Equipo agregado correctamente.");
    }

    /**
     * Metodo para mostrar los equipos registrados
     * @param equipos 
     */
    public static void mostrarEquipos(Equipo[] equipos) {

        String mensaje = "";

        for (int i = 0; i < equipos.length; i++) {

            if (equipos[i] != null) {

                mensaje += "Equipo #" + (i + 1) + "\n";
                mensaje += "Nombre: "+ equipos[i].getNombre() + "\n";
                mensaje += "Director Técnico: "+ equipos[i].getDt() + "\n";
                mensaje += "Jugadores:\n";

                for (int j = 0; j < equipos[i].getJugadores().length; j++) {

                    mensaje += "  " + (j + 1) + ". " + equipos[i].getJugadores()[j].getNombre() + "\n";
                }

                mensaje += "Goles: " + equipos[i].getGolesFavor()+ "\n";

                mensaje += "Goles en contra: " + equipos[i].getGolesContra() + "\n";

                mensaje += "Puntos: " + equipos[i].getPuntos() + "\n";

                mensaje += "----------------------------\n";
            }
        }

        if (mensaje.equals("")) {
            mensaje = "No hay equipos registrados.";
        }

        JOptionPane.showMessageDialog(null, mensaje);
    }

    /**
     * Metodo para actualizar los datos de un equipo previamente registrado.
     * @param equipos 
     */
    public static void actualizarEquipos(Equipo[] equipos) {

        String nombrePorBuscar = JOptionPane.showInputDialog("Digite el nombre del equipo que desea actualizar:");

        boolean encontrado = false;

        for (int i = 0; i < equipos.length; i++) {

            if (equipos[i] != null && nombrePorBuscar.equalsIgnoreCase(equipos[i].getNombre())) {

                String nombre = JOptionPane.showInputDialog("Nuevo nombre del país:");

                String dt = JOptionPane.showInputDialog("Nuevo Director Técnico:");

                equipos[i].setNombre(nombre);
                equipos[i].setDt(dt);

                // Actualizar los 11 jugadores
                for (int j = 0; j < equipos[i].getJugadores().length; j++) {

                    String nombreJugador = JOptionPane.showInputDialog("Nombre del jugador #" + (j + 1) + ":");

                    equipos[i].getJugadores()[j].setNombre(nombreJugador);
                }

                JOptionPane.showMessageDialog(null, "Equipo actualizado correctamente.");

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(null, "El equipo no se encuentra registrado.");
        }
    }

    /**
     * Metodo para generar todos los equipos con valores aleatorios - Modo Demo.
     * @param equipos 
     */
    public static void modoDemoEquipos(Equipo[] equipos) {

        for (int i = 0; i < equipos.length; i++) {

            if (equipos[i] == null) {
                String nombre = "País #" + (i + 1);

                String dt = "Director Técnico #" + (i + 1);

                // Crear los 11 jugadores
                Jugador[] jugadores = new Jugador[11];

                for (int j = 0; j < jugadores.length; j++) {

                    String nombreJugador = "Jugador " + (j + 1) + " - " + nombre;

                    jugadores[j] = new Jugador(nombreJugador, 0, 0, 0);
                }

                // Crear equipo
                equipos[i] = new Equipo(nombre, dt, jugadores, 0, 0, 0);
            }
        }
    }
}
