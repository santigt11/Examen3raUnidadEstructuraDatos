/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.tablas;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import javax.swing.table.AbstractTableModel;
import modelo.Actividad;

/**
 *
 * @author santi
 */
public class ModeloTablaActividad extends AbstractTableModel {

    private DynamicList<Actividad> actividades;

    @Override
    public int getRowCount() {
        return actividades.getLength();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Actividad ac = actividades.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (ac != null) ? ac.getNombre(): " ";
                case 1:
                    return (ac != null) ? ac.getDescripcion(): " ";
                case 2:
                    return (ac != null) ? ac.getNumTareas(): "";
                default:
                    return null;
            }
        } catch (EmptyException ex) {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "NOMBRE";
            case 1:
                return "DESCRIPCION";
            case 2:
                return "NRO_TAREAS";
            default:
                return null;
        }
    }

    public DynamicList<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(DynamicList<Actividad> actividad) {
        this.actividades = actividad;
    }

}
