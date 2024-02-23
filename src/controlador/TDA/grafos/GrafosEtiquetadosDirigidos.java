/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.grafos.exception.LabelEdgeException;
import controlador.TDA.grafos.exception.VerticeException;
import controlador.TDA.listas.DynamicList;
import controlador.Utiles.Utiles;
import java.lang.reflect.Array;
import java.util.HashMap;

/**
 *
 * @author Santiago
 */
public class GrafosEtiquetadosDirigidos<E> extends GrafoDirigido {

    protected E[] labels;
    protected HashMap<E, Integer> dicVertices;
    private Class<E> clazz;

    public GrafosEtiquetadosDirigidos(Integer num_vertices, Class clazz) {
        super(num_vertices);
        this.clazz = clazz;
        labels = (E[]) Array.newInstance(clazz, num_vertices + 1);
        dicVertices = new HashMap<>(num_vertices);
    }

    //Metodo que permite recatar el nro de vertice asociado a la etiqueta
    public Integer getVerticeE(E label) throws Exception {
        Integer aux = dicVertices.get(label);
        if (aux != null) {
            return dicVertices.get(label);
        } else {
            throw new VerticeException("No se encuentra ese vertice asociado a esa etiqueta");
        }
    }

    public E getLabelE(Integer v) throws Exception {
        if (v <= num_vertice()) {
            return labels[v];
        } else {
            throw new VerticeException("No se encuentra ese vertice");
        }
    }

    public Boolean isEdge(E o, E d) throws Exception {
        if (isAllLabelsGraph()) {
            return existe_arista(getVerticeE(o), getVerticeE(d));
        } else {
            throw new LabelEdgeException();
        }
    }

    public void insertEdfeE(E o, E d) throws Exception {
        if (isAllLabelsGraph()) {
            insertar_arista(getVerticeE(o), getVerticeE(d), Double.NaN);
        } else {
            throw new LabelEdgeException();
        }
    }

    public void insertEdfeE(E o, E d, Double peso) throws Exception {
        if (isAllLabelsGraph()) {
            insertar_arista(getVerticeE(o), getVerticeE(d), peso);
        } else {
            throw new LabelEdgeException();
        }
    }

    public DynamicList<Adyacencia> adjacents(E label) throws Exception {
        if (isAllLabelsGraph()) {
            return adyacentes(getVerticeE(label));
        } else {
            throw new LabelEdgeException();
        }
    }

    //Metodo principal que permite etiquetar grafos
    public void labelVertice(Integer v, E label) {
        labels[v] = label;
        dicVertices.put(label, v);
    }

    public Boolean isAllLabelsGraph() throws Exception {
        Boolean band = true;
        for (int i = 1; i < num_vertice(); i++) {
            if (labels[i] == null) {
                throw new LabelEdgeException();
            }
        }
        return band;
    }

    @Override
    public String toString() {
        StringBuilder grafo = new StringBuilder("GRAFO").append("\n");
        try {
            for (int i = 1; i <= num_vertice(); i++) {
                grafo.append("[").append(i).append("] ").append(getLabelE(i) + "\n");
                DynamicList<Adyacencia> list = adyacentes(i);
                for (int j = 0; j < list.getLength(); j++) {
                    Adyacencia a = list.getInfo(j);
                    grafo.append("ady [").append(a.getDestino()).append("] = ").append(getLabelE(a.getDestino())).append(" " + a.getPeso()).append("\n");
                }
            }
        } catch (Exception e) {
        }
        return grafo.toString();
    }

    public static void main(String[] args) throws Exception {
        GrafosEtiquetadosNoDirigidos<String> ged = new GrafosEtiquetadosNoDirigidos(6, String.class);
        ged.labelVertice(1, "Estefania");
        ged.labelVertice(2, "Luna");
        ged.labelVertice(3, "Jimenez");
        ged.labelVertice(4, "Criollo");
        ged.labelVertice(5, "Maritza");
        ged.labelVertice(6, "Nivelo");
        ged.insertEdfeE("Estefania", "Jimenez", 50.0);
        ged.insertEdfeE("Nivelo", "Maritza", 50.0);
        ged.insertEdfeE("Luna", "Criollo", 50.0);
        ged.insertEdfeE("Estefania", "Criollo", 50.0);
        ged.insertEdfeE("Maritza", "Jimenez", 50.0);
        PaintGraph p = new PaintGraph();
        p.updateFile(ged);
        String url = "d3/grafo.js";
        Utiles.abrirNavegadorPredeterminado(url);
        System.out.println(ged.toString());
    }
}
