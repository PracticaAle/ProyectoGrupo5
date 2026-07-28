package com.mycompany.proyectomundial;

/**
 * Representa un grupo de la primera fase del Mundial.
 * Cada grupo posee un nombre y cuatro equipos.
 */
public class Grupo {
    private String nombreGrupo;
    private Equipo[] equipos;

    public Grupo(String nombreGrupo, int cantidadEquipos) {
        this.nombreGrupo = nombreGrupo;
        this.equipos = new Equipo[cantidadEquipos];
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Equipo[] getEquipos() {
        return equipos;
    }

    public void setEquipos(Equipo[] equipos) {
        this.equipos = equipos;
    }

    public void asignarEquipo(int posicion, Equipo equipo) {
        if (posicion >= 0 && posicion < equipos.length) {
            equipos[posicion] = equipo;
        }
    }
}
