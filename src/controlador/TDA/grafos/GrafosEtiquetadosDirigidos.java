/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import Controlador.TDA.Listas.ListaEnlazada;
import controlador.TDA.grafos.exception.LabelEdgeException;
import controlador.TDA.grafos.exception.VerticeException;
import controlador.utiles.Utilidades;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author 
 */
public class GrafosEtiquetadosDirigidos<E> extends GrafoDirigido {

    protected E[] labels;
    protected HashMap<E, Integer> dicVertices;
    private Class<E> clazz;
    private Double[][] distancias;

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

    public ListaEnlazada<Adyacencia> adjacents(E label) throws Exception {
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
                ListaEnlazada<Adyacencia> list = adyacentes(i);
                for (int j = 0; j < list.getLength(); j++) {
                    Adyacencia a = list.getInfo(j);
                    grafo.append("ady [").append(a.getDestino()).append("] = ").append(getLabelE(a.getDestino())).append(" " + a.getPeso()).append("\n");
                }
            }
        } catch (Exception e) {
        }
        return grafo.toString();
    }
     public String aplicarAlgoritmoBellmanFord(int nodoOrigen) throws Exception {
        inicializarDistancias(nodoOrigen);

        for (int i = 1; i <= num_vertice() - 1; i++) {
            boolean hayCambios = false;
            for (int u = 1; u <= num_vertice(); u++) {
                for (int v = 1; v <= num_vertice(); v++) {
                    if (existe_arista(u, v)) {
                        double pesoUV = peso_arista(u, v);
                        if (distancias[nodoOrigen][u] + pesoUV < distancias[nodoOrigen][v]) {
                            distancias[nodoOrigen][v] = distancias[nodoOrigen][u] + pesoUV;
                            hayCambios = true;
                        }
                    }
                }
            }
            if (!hayCambios) {
                break;
            }
        }

        for (int u = 1; u <= num_vertice(); u++) {
            for (int v = 1; v <= num_vertice(); v++) {
                if (existe_arista(u, v)) {
                    double pesoUV = peso_arista(u, v);
                    if (distancias[nodoOrigen][u] + pesoUV < distancias[nodoOrigen][v]) {
                        throw new Exception("Hay ciclo negativo");
                    }
                }
            }
        }

        return construirRepresentacionMatrizDistancias();
    }

    private void inicializarDistancias(int nodoOrigen) throws Exception {
        distancias = new Double[num_vertice() + 1][num_vertice() + 1];
        for (int i = 1; i <= num_vertice(); i++) {
            for (int j = 1; j <= num_vertice(); j++) {
                if (i == j) {
                    distancias[i][j] = 0.0;
                } else if (existe_arista(i, j)) {
                    distancias[i][j] = peso_arista(i, j);
                } else {
                    distancias[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        for (int i = 1; i <= num_vertice(); i++) {
            if (i != nodoOrigen) {
                distancias[nodoOrigen][i] = Double.POSITIVE_INFINITY;
            }
        }
    }

    private String construirRepresentacionMatrizDistancias() {
        StringBuilder representacion = new StringBuilder();

        for (int i = 1; i <= num_vertice(); i++) {
            for (int j = 1; j <= num_vertice(); j++) {
                if (distancias[i][j] == Double.POSITIVE_INFINITY) {
                    representacion.append("INF ");
                } else {
                    representacion.append(String.format("%.2f ", distancias[i][j]));
                }
            }
            representacion.append("\n");
        }

        return representacion.toString();
    }

    // metodo de Floyd
    public String aplicarAlgoritmoFloydConEtiquetas() throws Exception {
        inicializarDistancias();

        for (int k = 1; k <= num_vertice(); k++) {
            for (int i = 1; i <= num_vertice(); i++) {
                for (int j = 1; j <= num_vertice(); j++) {
                    if (distancias[i][k] != Double.POSITIVE_INFINITY && distancias[k][j] != Double.POSITIVE_INFINITY) {
                        double nuevaDistancia = distancias[i][k] + distancias[k][j];
                        if (nuevaDistancia < distancias[i][j]) {
                            distancias[i][j] = nuevaDistancia;
                        }
                    }
                }
            }
        }

        return construirRepresentacionMatrizDistancias();
    }

    private void inicializarDistancias() throws Exception {
        distancias = new Double[num_vertice() + 1][num_vertice() + 1];
        for (int i = 1; i <= num_vertice(); i++) {
            for (int j = 1; j <= num_vertice(); j++) {
                if (i == j) {
                    distancias[i][j] = 0.0;
                } else if (existe_arista(i, j)) {
                    distancias[i][j] = peso_arista(i, j);
                } else {
                    distancias[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
    }

    private String construiRepresentacionMatrizDistancias() {
        StringBuilder representacion = new StringBuilder();

        for (int i = 1; i <= num_vertice(); i++) {
            for (int j = 1; j <= num_vertice(); j++) {
                if (distancias[i][j] == Double.POSITIVE_INFINITY) {
                    representacion.append("INF ");
                } else {
                    representacion.append(String.format("%.2f ", distancias[i][j]));
                }
            }
            representacion.append("\n");
        }

        return representacion.toString();
    }

    //metodo de Anchura 
    public boolean BFS(Integer verticeInicial) throws Exception {
        boolean[] visitados = new boolean[num_vertice() + 1];
        Queue<Integer> cola = new LinkedList<>();
        cola.add(verticeInicial);

        while (!cola.isEmpty()) {
            Integer verticeActual = cola.poll();
            visitados[verticeActual] = true;

            ListaEnlazada<Adyacencia> adyacentes = adyacentes(verticeActual);
            for (int i = 0; i < adyacentes.getLength(); i++) {
                Integer vecino = adyacentes.getInfo(i).getDestino();
                if (!visitados[vecino]) {
                    cola.add(vecino);
                }
            }
        }

        for (int i = 1; i <= num_vertice(); i++) {
            if (!visitados[i]) {
                return false;
            }
        }
        return true;
    }

    //metodo Profundidad
    public boolean DFS(Integer verticeInicial) throws Exception {
        boolean[] visitados = new boolean[num_vertice() + 1];
        Stack<Integer> pila = new Stack<>();
        pila.push(verticeInicial);

        while (!pila.isEmpty()) {
            Integer verticeActual = pila.pop();
            if (!visitados[verticeActual]) {
                visitados[verticeActual] = true;
                ListaEnlazada<Adyacencia> adyacentes = adyacentes(verticeActual);
                for (int i = 0; i < adyacentes.getLength(); i++) {
                    Integer vecino = adyacentes.getInfo(i).getDestino();
                    if (!visitados[vecino]) {
                        pila.push(vecino);
                    }
                }
            }
        }

        for (int i = 1; i <= num_vertice(); i++) {
            if (!visitados[i]) {
                return false;
            }
        }
        return true;
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
        Utilidades.abrirNavegadorPredeterminado(url);
        System.out.println(ged.toString());
    }
}
