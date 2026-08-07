package com.mycompany.proyectomundial;

import javax.swing.JOptionPane;

/**
 *
 * @author Dylan
 */
public class CuerpoArbitral {

    private String nombre;
    private String nacionalidad;
    private int partidosDirigidos;
    private int tarjetasMostradas;

    // Constructor
    public CuerpoArbitral(String nombre, String nacionalidad, int partidosDirigidos, int tarjetasMostradas) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.partidosDirigidos = partidosDirigidos;
        this.tarjetasMostradas = tarjetasMostradas;
    }

    // Gets y Sets
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getPartidosDirigidos() {
        return partidosDirigidos;
    }

    public void setPartidosDirigidos(int partidosDirigidos) {
        this.partidosDirigidos = partidosDirigidos;
    }

    public int getTarjetasMostradas() {
        return tarjetasMostradas;
    }

    public void setTarjetasMostradas(int tarjetasMostradas) {
        this.tarjetasMostradas = tarjetasMostradas;
    }

    /**
     * Metodo para agregar un arbitro.
     * @param arbitros 
     */
    public static void agregarArbitro(CuerpoArbitral[] arbitros) {
        int posicion = -1;

        for (int i = 0; i < arbitros.length; i++) {
            if (arbitros[i] == null) {
                posicion = i;
                break;
            }
        }

        if (posicion == -1) {
            JOptionPane.showMessageDialog(null, "No hay espacio para más arbitros.");

            return;
        }

        String nombre = JOptionPane.showInputDialog("Nombre del arbitro:");

        String nacionalidad = JOptionPane.showInputDialog("Nacionalidad:");

        arbitros[posicion] = new CuerpoArbitral(nombre,nacionalidad,0,0);

        JOptionPane.showMessageDialog(null,"Arbitro agregado correctamente.");
    }

    /**
     * Metodo para mostrar los arbitros registrados
     * @param arbitros 
     */
    public static void mostrarArbitros(CuerpoArbitral[] arbitros) {
        String mensaje = "";

        for (int i = 0; i < arbitros.length; i++) {

            if (arbitros[i] != null) {

                mensaje += "Arbitro #" + (i + 1) + "\n";

                mensaje += "Nombre: " + arbitros[i].getNombre() + "\n";

                mensaje += "Nacionalidad: " + arbitros[i].getNacionalidad() + "\n";

                mensaje += "Partidos Dirigidos: " + arbitros[i].getPartidosDirigidos() + "\n";

                mensaje += "Tarjetas Mostradas: " + arbitros[i].getTarjetasMostradas() + "\n";

                mensaje += "----------------------------\n";
            }
        }

        if (mensaje.equals("")) {
            mensaje = "No hay arbitros registrados.";
        }

        JOptionPane.showMessageDialog(null, mensaje);
    }

    /**
     * Metodo para actualizar los datos de un arbitro previamente registrado.
     * @param arbitros 
     */
    public static void actualizarArbitros(CuerpoArbitral[] arbitros) {

        String nombrePorBuscar = JOptionPane.showInputDialog("Digite el nombre del arbitro que desea actualizar:");

        boolean encontrado = false;

        for (int i = 0; i < arbitros.length; i++) {

            if (arbitros[i] != null && nombrePorBuscar.equalsIgnoreCase(arbitros[i].getNombre())) {

                String nombre = JOptionPane.showInputDialog("Ingrese el nombre del arbitro:");

                String nacionalidad = JOptionPane.showInputDialog("Ingrese la nacionalidad:");

                arbitros[i].setNombre(nombre);
                arbitros[i].setNacionalidad(nacionalidad);

                JOptionPane.showMessageDialog(null, "Arbitro actualizado correctamente.");

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {

            JOptionPane.showMessageDialog(null, "El arbitro no se encuentra registrado.");
        }
    }

    /**
     * Metodo para generar todos los arbitros con valores aleatorios - Modo Demo.
     * @param arbitros 
     */
    public static void modoDemoArbitros(CuerpoArbitral[] arbitros) {

        for (int i = 0; i < arbitros.length; i++) {

            if (arbitros[i] == null) {

                String nombre = "Arbitro #" + (i + 1);

                String nacionalidad = "Nacionalidad #" + ((int) (Math.random() * 50) + 1);

                arbitros[i] = new CuerpoArbitral(nombre, nacionalidad, 0, 0);
            }
        }
    }
}
