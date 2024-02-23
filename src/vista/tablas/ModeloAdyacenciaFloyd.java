/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.tablas;

import Controlador.TDA.grafos.GrafoFloydBellman;
import controlador.Utiles.UtilesFoto;

import javax.swing.table.AbstractTableModel;
import modelo.Tarea;


/**
 *
 * @author Alejandro
 */
public class ModeloAdyacenciaFloyd extends AbstractTableModel {

    private GrafoFloydBellman<Tarea> grafoTarea;

    @Override
    public int getRowCount() {
        return grafoTarea.num_vertice();
    }

    @Override
    public int getColumnCount() {
        return grafoTarea.num_vertice() + 1;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Pozos Luz ";
        } else {
            try {
                return grafoTarea.getLabelE(column).toString();
            } catch (Exception e) {
                return "";
            }
        }
    }

    public GrafoFloydBellman<Tarea> getGrafoTarea() {
        return grafoTarea;
    }

    public void setGrafoTarea(GrafoFloydBellman<Tarea> grafo1) {
        this.grafoTarea = grafo1;
    }
    
    

    @Override
    public Object getValueAt(int i, int i1) {
        try {
            if (i1 == 0) {
                return grafoTarea.getLabelE(i + 1).toString();
            } else {
                Tarea o = grafoTarea.getLabelE(i + 1);
                Tarea d = grafoTarea.getLabelE(i1);
                if (grafoTarea.isEdgeE(o, d)) {
                    return UtilesFoto.redondear(grafoTarea.peso_arista(i + 1, i1)).toString();
                } else {
                    return "--";
                }
            }
        } catch (Exception e) {
            return "";
        }
    }
}
