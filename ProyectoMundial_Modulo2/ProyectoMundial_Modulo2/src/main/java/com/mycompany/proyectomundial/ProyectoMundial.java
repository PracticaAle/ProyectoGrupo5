/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectomundial;

import javax.swing.JOptionPane;

/**
 * Proyecto para administrar un Mundial de fútbol.
 */
public class ProyectoMundial {

    public static void main(String[] args) {

        int opcionCapacidad;
        int cantidadSedes;
        int capacidad = 0;

        Partido[] calendario = null;

        do {

            opcionCapacidad = Integer.parseInt(JOptionPane.showInputDialog("Seleccione la cantidad de países participantes:\n"
                    + "1. 24 Equipos\n"
                    + "2. 32 Equipos\n"
                    + "3. 48 Equipos\n"
                    + "4. 64 Equipos"));

            switch (opcionCapacidad) {

                case 1:
                    capacidad = 24;
                    break;

                case 2:
                    capacidad = 32;
                    break;

                case 3:
                    capacidad = 48;
                    break;

                case 4:
                    capacidad = 64;
                    break;

                default:
                    capacidad = 0;
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            }

        } while (capacidad != 24 && capacidad != 32 && capacidad != 48 && capacidad != 64);

        Equipo equipos[] = new Equipo[capacidad];

        CuerpoArbitral arbitros[] = new CuerpoArbitral[30];

        cantidadSedes = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de sedes del torneo:"));

        Sede[] sedes = new Sede[cantidadSedes];

        Grupo[] grupos = null;

        int opcion;

        do {

            opcion = Integer.parseInt(JOptionPane.showInputDialog("===== MENÚ PRINCIPAL =====\n"
                    + "1. Nuevo Equipo\n"
                    + "2. Ver Equipos\n"
                    + "3. Actualizar Equipo\n"
                    + "4. Nuevo Árbitro\n"
                    + "5. Ver Árbitros\n"
                    + "6. Actualizar Árbitro\n"
                    + "7. Nueva Sede\n"
                    + "8. Ver Sedes\n"
                    + "9. Actualizar Sede\n"
                    + "10. Modo Demo\n"
                    + "\n===== MÓDULO 2 =====\n"
                    + "11. Crear grupos\n"
                    + "12. Mostrar grupos\n"
                    + "13. Reiniciar tablas de grupos\n"
                    + "14. Mostrar tablas de grupos\n"
                    + "\n===== MÓDULO 3 =====\n"
                    + "15. Generar calendario\n"
                    + "16. Mostrar partidos por grupo\n"
                    + "17. Simular siguiente partido\n"
                    + "18. Simular fase completa\n"
                    + "19. Salir\n"
                    + "Seleccione una opción:"));

            switch (opcion) {

                case 1:
                    Equipo.agregarEquipo(equipos);
                    break;

                case 2:
                    Equipo.mostrarEquipos(equipos);
                    break;

                case 3:
                    Equipo.actualizarEquipos(equipos);
                    break;

                case 4:
                    CuerpoArbitral.agregarArbitro(arbitros);
                    break;

                case 5:
                    CuerpoArbitral.mostrarArbitros(arbitros);
                    break;

                case 6:
                    CuerpoArbitral.actualizarArbitros(arbitros);
                    break;

                case 7:
                    Sede.agregarSede(sedes);
                    break;

                case 8:
                    Sede.mostrarSedes(sedes);
                    break;

                case 9:
                    Sede.actualizarSedes(sedes);
                    break;

                case 10:
                    Equipo.modoDemoEquipos(equipos);
                    CuerpoArbitral.modoDemoArbitros(arbitros);
                    Sede.modoDemoSedes(sedes);
                    JOptionPane.showMessageDialog(null, "Modo demo completado.");
                    break;

                case 11:
                    Grupo[] nuevosGrupos = ModuloGrupos.crearGrupos(equipos);

                    if (nuevosGrupos != null) {
                        grupos = nuevosGrupos;
                    }

                    break;

                case 12:
                    ModuloGrupos.mostrarGrupos(grupos);
                    break;

                case 13:
                    ModuloGrupos.iniciarTablaGrupo(grupos);

                    if (grupos != null) {
                        JOptionPane.showMessageDialog(null, "Las tablas se reiniciaron correctamente.");
                    }

                    break;

                case 14:
                    ModuloGrupos.mostrarTablaGrupo(grupos);
                    break;

                case 15:
                    calendario = ModuloCalendario.generarCalendarioGrupos(grupos, sedes, arbitros);
                    break;

                case 16:
                    ModuloCalendario.mostrarPartidosPorGrupo(calendario, grupos);
                    break;

                case 17:
                    Partido siguientePartido = ModuloCalendario.obtenerSiguientePartido(calendario);
                    ModuloCalendario.simularPartidoAPartido(siguientePartido, true);
                    break;

                case 18:
                    ModuloCalendario.simularFaseCompleta(calendario);
                    break;

                case 19:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            }

        } while (opcion != 19);
    }
}
