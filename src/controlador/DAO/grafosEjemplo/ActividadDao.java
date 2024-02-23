/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.DAO.grafosEjemplo;

import Controlador.TDA.grafos.GrafoFloydBellman;
import controlador.DAO.DaoImplement;
import controlador.TDA.grafos.GrafosEtiquetadosDirigidos;
import controlador.TDA.listas.DynamicList;
import java.io.FileReader;
import java.io.FileWriter;
import modelo.Actividad;
import modelo.Tarea;

/**
 *
 * @author Santiago
 */
public class ActividadDao extends DaoImplement<Actividad> {

    private DynamicList<Tarea> tareas = new DynamicList<>();
    private Actividad actividad;
    private GrafoFloydBellman<Tarea> grafo;

    public DynamicList<Tarea> getTareas() {
        if (tareas.isEmpty()) {
            tareas = new DynamicList<>();
        }
        return tareas;
    }

    public void setEscuelas(DynamicList<Tarea> lista) {
        this.tareas = lista;
    }

    public Actividad getActividad() {
        if (actividad == null) {
            actividad = new Actividad();
        }
        return actividad;
    }

    public void setEscuela(Actividad actividad) {
        this.actividad = actividad;
    }

    public Boolean persist() {
        getActividad().setId(all().getLength() + 1);
        return persist(getActividad());
    }

    public void guardarGrafo() throws Exception {
        getConection().toXML(grafo, new FileWriter("files/grafo.json"));
    }

    public ActividadDao() {
        super(Actividad.class);
    }

    /**
     * @return the grafo
     */
    public GrafosEtiquetadosDirigidos<Tarea> getGrafo() throws Exception {
        if (grafo == null) {
            DynamicList<Tarea> list = getTareas();
            if (!list.isEmpty()) {
                grafo = new GrafoFloydBellman(list.getLength(), Tarea.class);
                for (int i = 0; i < list.getLength(); i++) {
                    grafo.labelVertice((i + 1), list.getInfo(i));
                }
            }
        }
        return grafo;
    }

    public void loadGraph() throws Exception {
        grafo = (GrafoFloydBellman<Tarea>) 
                getConection()
                        .fromXML(new FileReader("files/grafo.json"));
        tareas.reset();
        for (int i = 1; i < grafo.num_vertice(); i++) {
            tareas.add(grafo.getLabelE(i));
        }
    }

    /**
     * @param grafo the grafo to set
     */
    public void setGrafo(GrafoFloydBellman<Tarea> grafo) {
        this.grafo = grafo;
    }

}
