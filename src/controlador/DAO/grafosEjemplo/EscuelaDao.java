/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.DAO.grafosEjemplo;

import controlador.DAO.DaoImplement;
import controlador.TDA.grafos.GrafosEtiquetadosNoDirigidos;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import modelo.grafos.modelo.Escuela;

/**
 *
 * @author Santiago
 */
public class EscuelaDao extends DaoImplement<Escuela> {

    private DynamicList<Escuela> escuelas = new DynamicList<>();
    private Escuela escuela;
    private GrafosEtiquetadosNoDirigidos<Escuela> grafo;

    public DynamicList<Escuela> getEscuelas() {
        if (escuelas.isEmpty()) {
            escuelas = all();
        }
        return escuelas;
    }

    public void setEscuelas(DynamicList<Escuela> lista) {
        this.escuelas = lista;
    }

    public Escuela getEscuela() {
        if (escuela == null) {
            escuela = new Escuela();
        }
        return escuela;
    }

    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

    public Boolean persist() {
        getEscuela().setId(all().getLength() + 1);
        getEscuela().getCoordenada().setId(all().getLength() + 1);
        return persist(getEscuela());
    }

    public void guardarGrafo() throws Exception {
        getConection().toXML(grafo, new FileWriter("files/grafo.json"));
    }

    public EscuelaDao() {
        super(Escuela.class);
    }

    /**
     * @return the grafo
     */
    public GrafosEtiquetadosNoDirigidos<Escuela> getGrafo() throws Exception {
        if (grafo == null) {
            DynamicList<Escuela> list = getEscuelas();
            if (!list.isEmpty()) {
                grafo = new GrafosEtiquetadosNoDirigidos(list.getLength(), Escuela.class);
                for (int i = 0; i < list.getLength(); i++) {
                    grafo.labelVertice((i + 1), list.getInfo(i));
                }
            }
        }
        return grafo;
    }

    public void loadGraph() throws Exception {
        grafo = (GrafosEtiquetadosNoDirigidos<Escuela>) 
                getConection()
                        .fromXML(new FileReader("files/grafo.json"));
        escuelas.reset();
        for (int i = 1; i < grafo.num_vertice(); i++) {
            escuelas.add(grafo.getLabelE(i));
        }
    }

    /**
     * @param grafo the grafo to set
     */
    public void setGrafo(GrafosEtiquetadosNoDirigidos<Escuela> grafo) {
        this.grafo = grafo;
    }

}
