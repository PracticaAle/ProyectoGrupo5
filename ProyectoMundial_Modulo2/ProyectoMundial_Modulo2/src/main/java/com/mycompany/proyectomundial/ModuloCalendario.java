/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectomundial;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author usuario
 */
public class ModuloCalendario {
    
    /**
     * Este metodo genera partidos grupo por grupo y les asigna arbitro y sede automaticamente
     * @param grupos
     * @return 
     */
    public static Partido[][] generarCalendarioGrupos(Grupo[] grupos, Sede[] sedes, CuerpoArbitral[] arbitros){
        
        //Verificar primero que hayan grupos ya creados
        if (grupos==null){
            JOptionPane.showMessageDialog(null, "Primero debe generar los grupos.");
            
            //finalizar el metodo de no haber grupos creados
            return null;
        }

        //Matriz donde cada fila va a representar un grupo y cada grupo con 6 parttidos
        Partido[][] calendario= new Partido[grupos.length][6];

        //Recorrer todos los grupos 
        for (int i = 0; i < grupos.length; i++) {
            
            //Obtiene los cuatro equipos del grupo actual
            Equipo[] equipos= grupos[i].getEquipos();
            
            int posicion=0;
            
            //Seleccione el primer equipo del enfrentamiento
            for (int j = 0; j < equipos.length; j++) {
                
                //Seleccionar el segundo
                //j+1 para que no se repitan partidos que un equipo no juegue contra si mismo
                for (int k = j+1; k < equipos.length; k++) {
                    
                    //Crear el partido
                    calendario[i][posicion]= new Partido(equipos[j], equipos[k]);
                    
                    posicion++;
                }
                
            }
            
        }
        JOptionPane.showMessageDialog(null, "\"Calendarios generados correctamente\");");
        
        //Asignar la sede y arbitros automaticamente 
        asignarSedesCalendario(calendario, sedes);
        asignarArbitrosAleatorios(calendario, arbitros);
        return calendario;
        
     
    }
    /**
     * Muestra los partidos que le va a tocar a cada grupo
     * @param calendario
     * @param grupos 
     */
    public static void mostrarPartidosPorGrupo(Partido[][] calendario, Grupo[] grupos){
        //verificar que se haya creado el calendario
        if (calendario == null){
            JOptionPane.showMessageDialog(null, "Primero debe generar los partidos.");
            return;
        }
                
        //Recorrer los grupos
        for (int i = 0; i < calendario.length; i++) {
            //Muestre grupo
            JOptionPane.showMessageDialog(null, "\n====== "+grupos[i].getNombreGrupo()+" ======");
            
            //recorre partidos del grupo actual
            for (int j = 0; j < calendario[i].length; j++) {
                Partido partido=calendario[i][j];
                JOptionPane.showMessageDialog(null, (j+1)+". "+
                        partido.getEquipoLocal().getNombre()+
                        " vs "+ partido.getEquipoVisitante().getNombre());
            }
        
            
        }
    }
    /**
     * Asigna la sede a los partifos
     * @param calendario
     * @param sedes 
     */
    public static void asignarSedesCalendario(Partido[][] calendario, Sede[] sedes){
        if (calendario==null){
            JOptionPane.showMessageDialog(null, "Genere primero el calendario");
            return;
        }
        
        //Ver si ya hay sedes
        if (sedes==null){
            JOptionPane.showMessageDialog(null, "Primero registre las sedes");
            return;
        }
        
        int posicionSede= 0;
        
        for (int i = 0; i < calendario.length; i++) {
            for (int j = 0; j < calendario[i].length; j++) {
                Partido partido=calendario[i][j];
                
                //Asignar la sede
                partido.setSede(sedes[posicionSede]);
                
                //Siguiente sede
                posicionSede++;
                
                if (posicionSede==sedes.length){
                    posicionSede=0;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Sede asignada correctamente");
        }
    /**
     * Asigna arbitro a cada partido
     * @param calendario
     * @param arbitros 
     */   
    public static void asignarArbitrosAleatorios(Partido[][] calendario, CuerpoArbitral[] arbitros){
        if (calendario==null){
            JOptionPane.showMessageDialog(null, "Genere el calendario");
            return;
        }
        
        if (arbitros.length==0){
            JOptionPane.showMessageDialog(null, "Registre primero los arbitros del torneo");
            return;
        }
        //GENERAR NUMEROS ALEATORIOS
        Random aleatorio= new Random();
                
        for (int i = 0; i < calendario.length; i++) {
                for (int j = 0; j < calendario[i].length; j++) {
                Partido partido=calendario[i][j];
                
                int posicionAleatoria= aleatorio.nextInt(arbitros.length);
                
                partido.setArbitroAsignado(arbitros[posicionAleatoria]);
            }
        }
        
    }
    
    public static Partido obtenerSiguientePartido(Partido[][] calendario){
        if (calendario==null){
            JOptionPane.showMessageDialog(null, "Genere el calendario");
            return null;
        }
        for (int i = 0; i < calendario.length; i++) {
            for (int j = 0; j < calendario[i].length; j++) {
                Partido partido=calendario[i][j];
                
                if (partido.isJugado()==false){
                    JOptionPane.showMessageDialog(null, "Siguiente partido: "+"\n"+partido.getEquipoLocal().getNombre()+"\nvs\n "+ partido.getEquipoVisitante().getNombre());
                    return partido;
                   
                }
                
            }
            JOptionPane.showMessageDialog(null, "Ya no quedaron partidos pendeintes");
        }
        return null;
    }
    
}
    


