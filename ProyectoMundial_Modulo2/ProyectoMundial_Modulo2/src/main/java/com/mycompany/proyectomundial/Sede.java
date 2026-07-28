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
public class Sede {
    private String nombre;
    private String ciudad;
    private int capacidad;
    private double precioEntrada;

    //Creación del constructor.
    public Sede(String nombre, String ciudad, int capacidad, double precioEntrada) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.precioEntrada = precioEntrada;
    }

    //Gets y Sets.
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(double precioEntrada) {
        this.precioEntrada = precioEntrada;
    }
    
    //Método para añadir Equipos.
    public static void agregarSede(Sede[] sedes) {
        //Buscar la primera posición libre
        int posicion = -1;

        for (int i = 0; i < sedes.length; i++) {
            if (sedes[i] == null) {
                posicion = i;
                break;
            }
        }

        if (posicion == -1) {
            JOptionPane.showMessageDialog(null, "No hay espacio para más sedes.");
            return;
        }

        //Pedir datos
        String nombre = JOptionPane.showInputDialog("Nombre de la sede: ");
        String ciudad = JOptionPane.showInputDialog("Ciudad donde se ubica la sede: ");
        int capacidad = Integer.parseInt(JOptionPane.showInputDialog("Capacidad: "));
        double precioEntrada = Double.parseDouble(JOptionPane.showInputDialog("Precio por entrada: "));

        //Crear el objeto
        sedes[posicion] = new Sede(nombre, ciudad, capacidad, precioEntrada);

        JOptionPane.showMessageDialog(null, "Sede agregada correctamente.");
    }
    
    //Método para mostrar Sedes.
    public static void mostrarSedes(Sede[] sedes) {
        String mensaje = "";

        for (int i = 0; i < sedes.length; i++) {
            if (sedes[i] != null) {
                mensaje += "Sede #" + (i + 1) + "\n";
                mensaje += "Nombre: " + sedes[i].getNombre() + "\n";
                mensaje += "Ciudad: " + sedes[i].getCiudad()+ "\n";
                mensaje += "Capacidad: " + sedes[i].getCapacidad()+ "\n";
                mensaje += "Precio por Entrada: " + sedes[i].getPrecioEntrada()+ "\n";
                mensaje += "----------------------------\n";
            }
        }

        if (mensaje.equals("")) {
            mensaje = "No hay sedes registradas.";
        }

        JOptionPane.showMessageDialog(null, mensaje);
    }
    
    //Método para actualizar Sedes.
    public static void actualizarSedes(Sede[] sedes) {

        String nombrePorBuscar = JOptionPane.showInputDialog("Digite el nombre de la sede que desea actualizar:");

        boolean encontrado = false;

        for (int i = 0; i < sedes.length; i++) {

            if (sedes[i] != null &&
                nombrePorBuscar.equalsIgnoreCase(sedes[i].getNombre())) {

                String nombre = JOptionPane.showInputDialog("Nuevo nombre de la sede:");
                String ciudad = JOptionPane.showInputDialog("Nueva ciudad:");
                int capacidad = Integer.parseInt(JOptionPane.showInputDialog("Capacidad: "));
                double precioEntrada = Double.parseDouble(JOptionPane.showInputDialog("Precio por entrada: "));

                sedes[i].setNombre(nombre);
                sedes[i].setCiudad(ciudad);
                sedes[i].setCapacidad(capacidad);
                sedes[i].setPrecioEntrada(precioEntrada);

                JOptionPane.showMessageDialog(null, "Sede actualizada correctamente.");

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(null,
                    "La sede no se encuentra registrada.");
        }
    }
    
    //Método de Modo Demo para completar la lista del objeto Sede.
    public static void modoDemoSedes(Sede[] sedes) {

        for (int i = 0; i < sedes.length; i++) {

            if (sedes[i] == null) {

                String nombre = "Sede #" + (i + 1);
                String ciudadad = "Sede #" + (i + 1);
                int capacidad = ((int)(Math.random() * 50) + 1);
                double precioEntrada = ((double)(Math.random() * 50) + 1);

                sedes[i] = new Sede(nombre, ciudadad, capacidad, precioEntrada);
            }
        }
    }
}
