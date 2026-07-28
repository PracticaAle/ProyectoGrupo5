package com.mycompany.proyectomundial;

import javax.swing.JOptionPane;

/**
 * Módulo 2: creación y visualización de la fase de grupos.
 */
public class ModuloGrupos {

    private static final int EQUIPOS_POR_GRUPO = 4;

    // Determina cuántos grupos se necesitan.
    public static int calcularCantidadGrupos(int cantidadEquipos) {
        return cantidadEquipos / EQUIPOS_POR_GRUPO;
    }

    // Devuelve una copia de los equipos en orden aleatorio.
    public static Equipo[] mezclarEquipos(Equipo[] equipos) {
        Equipo[] equiposMezclados = new Equipo[equipos.length];

        for (int i = 0; i < equipos.length; i++) {
            equiposMezclados[i] = equipos[i];
        }

        // Algoritmo Fisher-Yates.
        for (int i = equiposMezclados.length - 1; i > 0; i--) {
            int posicionAleatoria = (int) (Math.random() * (i + 1));

            Equipo temporal = equiposMezclados[i];
            equiposMezclados[i] = equiposMezclados[posicionAleatoria];
            equiposMezclados[posicionAleatoria] = temporal;
        }

        return equiposMezclados;
    }

    // Crea los grupos y distribuye cuatro equipos en cada uno.
    public static Grupo[] crearGrupos(Equipo[] equipos) {
        if (!equiposCompletos(equipos)) {
            JOptionPane.showMessageDialog(null,
                    "Debe registrar todos los equipos antes de crear los grupos.\n"
                    + "Puede utilizar la opción Modo Demo para realizar una prueba.");
            return null;
        }

        if (equipos.length % EQUIPOS_POR_GRUPO != 0) {
            JOptionPane.showMessageDialog(null,
                    "La cantidad de equipos debe ser divisible entre 4.");
            return null;
        }

        int cantidadGrupos = calcularCantidadGrupos(equipos.length);
        Grupo[] grupos = new Grupo[cantidadGrupos];
        Equipo[] equiposMezclados = mezclarEquipos(equipos);

        int posicionEquipo = 0;

        for (int i = 0; i < grupos.length; i++) {
            String nombreGrupo = "Grupo " + (char) ('A' + i);
            grupos[i] = new Grupo(nombreGrupo, EQUIPOS_POR_GRUPO);

            for (int j = 0; j < EQUIPOS_POR_GRUPO; j++) {
                grupos[i].asignarEquipo(j, equiposMezclados[posicionEquipo]);
                posicionEquipo++;
            }
        }

        iniciarTablaGrupo(grupos);

        JOptionPane.showMessageDialog(null,
                "Se crearon " + cantidadGrupos + " grupos correctamente.");

        return grupos;
    }

    // Coloca las estadísticas de todos los equipos en cero.
    public static void iniciarTablaGrupo(Grupo[] grupos) {
        if (grupos == null) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe crear los grupos.");
            return;
        }

        for (int i = 0; i < grupos.length; i++) {
            Equipo[] equiposGrupo = grupos[i].getEquipos();

            for (int j = 0; j < equiposGrupo.length; j++) {
                if (equiposGrupo[j] != null) {
                    equiposGrupo[j].setGolesFavor(0);
                    equiposGrupo[j].setGolesContra(0);
                    equiposGrupo[j].setPuntos(0);
                }
            }
        }
    }

    // Muestra únicamente la distribución de equipos por grupo.
    public static void mostrarGrupos(Grupo[] grupos) {
        if (grupos == null) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe crear los grupos.");
            return;
        }

        String mensaje = "===== FASE DE GRUPOS =====\n\n";

        for (int i = 0; i < grupos.length; i++) {
            mensaje += grupos[i].getNombreGrupo() + "\n";
            mensaje += "----------------------------\n";

            Equipo[] equiposGrupo = grupos[i].getEquipos();

            for (int j = 0; j < equiposGrupo.length; j++) {
                mensaje += (j + 1) + ". " + equiposGrupo[j].getNombre() + "\n";
            }

            mensaje += "\n";
        }

        JOptionPane.showMessageDialog(null, mensaje);
    }

    // Muestra la tabla de posiciones de cada grupo.
    public static void mostrarTablaGrupo(Grupo[] grupos) {
        if (grupos == null) {
            JOptionPane.showMessageDialog(null,
                    "Primero debe crear los grupos.");
            return;
        }

        String mensaje = "===== TABLAS DE POSICIONES =====\n\n";

        for (int i = 0; i < grupos.length; i++) {
            Equipo[] equiposOrdenados = ordenarTabla(grupos[i].getEquipos());

            mensaje += grupos[i].getNombreGrupo() + "\n";
            mensaje += "Pos  Equipo             GF  GC  DG  Pts\n";
            mensaje += "--------------------------------------\n";

            for (int j = 0; j < equiposOrdenados.length; j++) {
                Equipo equipo = equiposOrdenados[j];
                int diferenciaGoles = equipo.getGolesFavor() - equipo.getGolesContra();

                mensaje += (j + 1) + ".   "
                        + ajustarTexto(equipo.getNombre(), 17)
                        + ajustarNumero(equipo.getGolesFavor(), 4)
                        + ajustarNumero(equipo.getGolesContra(), 4)
                        + ajustarNumero(diferenciaGoles, 4)
                        + ajustarNumero(equipo.getPuntos(), 5)
                        + "\n";
            }

            mensaje += "\n";
        }

        JOptionPane.showMessageDialog(null, mensaje);
    }

    // Verifica que el arreglo no tenga posiciones vacías.
    private static boolean equiposCompletos(Equipo[] equipos) {
        if (equipos == null || equipos.length == 0) {
            return false;
        }

        for (int i = 0; i < equipos.length; i++) {
            if (equipos[i] == null) {
                return false;
            }
        }

        return true;
    }

    // Ordena por puntos, diferencia de goles y goles a favor.
    private static Equipo[] ordenarTabla(Equipo[] equipos) {
        Equipo[] copia = new Equipo[equipos.length];

        for (int i = 0; i < equipos.length; i++) {
            copia[i] = equipos[i];
        }

        for (int i = 0; i < copia.length - 1; i++) {
            for (int j = 0; j < copia.length - 1 - i; j++) {
                if (debeIntercambiar(copia[j], copia[j + 1])) {
                    Equipo temporal = copia[j];
                    copia[j] = copia[j + 1];
                    copia[j + 1] = temporal;
                }
            }
        }

        return copia;
    }

    private static boolean debeIntercambiar(Equipo primero, Equipo segundo) {
        if (primero.getPuntos() < segundo.getPuntos()) {
            return true;
        }

        if (primero.getPuntos() == segundo.getPuntos()) {
            int diferenciaPrimero = primero.getGolesFavor() - primero.getGolesContra();
            int diferenciaSegundo = segundo.getGolesFavor() - segundo.getGolesContra();

            if (diferenciaPrimero < diferenciaSegundo) {
                return true;
            }

            if (diferenciaPrimero == diferenciaSegundo
                    && primero.getGolesFavor() < segundo.getGolesFavor()) {
                return true;
            }
        }

        return false;
    }

    private static String ajustarTexto(String texto, int largo) {
        if (texto.length() > largo) {
            return texto.substring(0, largo - 1) + " ";
        }

        while (texto.length() < largo) {
            texto += " ";
        }

        return texto;
    }

    private static String ajustarNumero(int numero, int largo) {
        String texto = String.valueOf(numero);

        while (texto.length() < largo) {
            texto = " " + texto;
        }

        return texto;
    }
}
