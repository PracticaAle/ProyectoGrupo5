/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

import javax.swing.JOptionPane;

/**
 *
 * @author Dylan
 */
public class Equipo {
    private String nombre;
    private String dt;
    private String jugador;
    private int golesFavor;
    private int golesContra;
    private int puntos;

    //Creación del constructor.
    public Equipo(String nombre, String dt, String jugador, int golesFavor, int golesContra, int puntos) {
        this.nombre = nombre;
        this.dt = dt;
        this.jugador = jugador;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.puntos = puntos;
    }

    //Gets y Sets.
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

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
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
    
    //Método para añadir Equipos.
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
            JOptionPane.showMessageDialog(null, "No hay espacio para más equipos.");
            return;
        }

        // Pedir datos
        String nombre = JOptionPane.showInputDialog("Nombre del país:");
        String dt = JOptionPane.showInputDialog("Director Técnico:");
        String jugador = JOptionPane.showInputDialog("Jugador:");

        // Crear el objeto
        equipos[posicion] = new Equipo(nombre, dt, jugador, 0, 0, 0);

        JOptionPane.showMessageDialog(null, "Equipo agregado correctamente.");
    }
    
    //Método para mostrar Equipos.
    public static void mostrarEquipos(Equipo[] equipos) {
        String mensaje = "";

        for (int i = 0; i < equipos.length; i++) {
            if (equipos[i] != null) {
                mensaje += "Equipo #" + (i + 1) + "\n";
                mensaje += "Nombre: " + equipos[i].getNombre() + "\n";
                mensaje += "Director Técnico: " + equipos[i].getDt() + "\n";
                mensaje += "Jugador estrella: " + equipos[i].getJugador() + "\n";
                mensaje += "Goles: " + equipos[i].getGolesFavor()+ "\n";
                mensaje += "----------------------------\n";
            }
        }

        if (mensaje.equals("")) {
            mensaje = "No hay equipos registrados.";
        }

        JOptionPane.showMessageDialog(null, mensaje);
    }
    
    //Método para actualizar Equipos.
    public static void actualizarEquipos(Equipo[] equipos) {

        String nombrePorBuscar = JOptionPane.showInputDialog("Digite el nombre del equipo que desea actualizar:");

        boolean encontrado = false;

        for (int i = 0; i < equipos.length; i++) {

            if (equipos[i] != null &&
                nombrePorBuscar.equalsIgnoreCase(equipos[i].getNombre())) {

                String nombre = JOptionPane.showInputDialog("Nuevo nombre del país:");
                String dt = JOptionPane.showInputDialog("Nuevo Director Técnico:");
                String jugador = JOptionPane.showInputDialog("Nuevo jugador estrella:");

                equipos[i].setNombre(nombre);
                equipos[i].setDt(dt);
                equipos[i].setJugador(jugador);

                JOptionPane.showMessageDialog(null, "Equipo actualizado correctamente.");

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(null,
                    "El equipo no se encuentra registrado.");
        }
    }
    
    //Método de Modo Demo para completar la lista del objeto Equipo.
    public static void modoDemoEquipos(Equipo[] equipos) {

        for (int i = 0; i < equipos.length; i++) {

            if (equipos[i] == null) {

                String nombre = "País #" + (i + 1);
                String dt = "Director Técnico #" + (i + 1);
                String jugador = "Jugador #" + ((int)(Math.random() * 50) + 1);

                equipos[i] = new Equipo(nombre, dt, jugador, 0, 0, 0);
            }
        }
    }
}
