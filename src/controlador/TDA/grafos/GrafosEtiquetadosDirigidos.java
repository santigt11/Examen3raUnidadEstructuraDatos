/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.colas.QueueUltimate;
import controlador.TDA.grafos.exception.LabelEdgeException;
import controlador.TDA.grafos.exception.VerticeException;
import controlador.TDA.listas.DynamicList;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Random;
import modelo.SubEstacion;
import vista.grafoEjemplo.utilidades.UtilesVistaSubEstacion;

/**
 *
 * @author Santiago
 */
public class GrafosEtiquetadosDirigidos<E> extends GrafoDirigido {

    private Double[][] distancias;
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

    public void insertEdgeE(E o, E d) throws Exception {
        if (isAllLabelsGraph()) {
            insertar_arista(getVerticeE(o), getVerticeE(d), Double.NaN);
        } else {
            throw new LabelEdgeException();
        }
    }

    public void insertEdgeE(E o, E d, Double peso) throws Exception {
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

//========================================================================================================
//========================================================================================================
    public String encontrarCaminoMasCorto(int origen, int destino) throws Exception {
        if (distancias[origen][destino] == Double.POSITIVE_INFINITY) {
            return "No hay ruta entre " + getLabelE(origen) + " y " + getLabelE(destino);
        }

        StringBuilder rutaMasCorta = new StringBuilder();
        rutaMasCorta.append("Camino más corto desde ").append(getLabelE(origen)).append(" hasta ").append(getLabelE(destino)).append(":\n");

        int nodoActual = origen;
        double sumaPesos = 0.0;
        rutaMasCorta.append(getLabelE(nodoActual));

        while (nodoActual != destino) {
            int siguienteNodo = encontrarVecinoMasCercano(nodoActual, destino);
            if (siguienteNodo == -1) {
                break; // No hay vecinos o no se encontró un camino, salir del bucle
            }

            sumaPesos += distancias[nodoActual][siguienteNodo];
            rutaMasCorta.append(" -> ").append(getLabelE(siguienteNodo));
            nodoActual = siguienteNodo;
        }

        rutaMasCorta.append("\nSuma de los pesos: ").append(sumaPesos);

        return rutaMasCorta.toString();
    }

    private int encontrarVecinoMasCercano(int nodoActual, int destino) throws Exception {
        int vecinoMasCercano = -1;
        double distanciaMasCorta = Double.POSITIVE_INFINITY;

        for (int k = 1; k <= num_vertice(); k++) {
            if (nodoActual != k && existe_arista(nodoActual, k) && distancias[k][destino] < distanciaMasCorta) {
                vecinoMasCercano = k;
                distanciaMasCorta = distancias[k][destino];
            }
        }

        return vecinoMasCercano;
    }

//========================================================================================================
//========================================================================================================
//FLOYD
    public String aplicarAlgoritmoFloydConEtiquetas() throws Exception {
        inicializarDistancias();

        for (int k = 1; k <= num_vertice(); k++) {
            for (int i = 1; i <= num_vertice(); i++) {
                for (int j = 1; j <= num_vertice(); j++) {
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
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

//========================================================================================================
//========================================================================================================
//BELLMAN
    public String aplicarAlgoritmoBellmanFord(int nodoOrigen) throws Exception {
        inicializarDistancias(nodoOrigen);

        for (int i = 1; i <= num_vertice() - 1; i++) {
            for (int u = 1; u <= num_vertice(); u++) {
                for (int v = 1; v <= num_vertice(); v++) {
                    if (existe_arista(u, v)) {
                        Double pesoUV = peso_arista(u, v);
                        if (distancias[nodoOrigen][u] + pesoUV < distancias[nodoOrigen][v]) {
                            distancias[nodoOrigen][v] = distancias[nodoOrigen][u] + pesoUV;
                        }
                    }
                }
            }
        }

        for (int u = 1; u <= num_vertice(); u++) {
            for (int v = 1; v <= num_vertice(); v++) {
                if (existe_arista(u, v)) {
                    Double pesoUV = peso_arista(u, v);
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

    public boolean DFS(Integer verticeInicial) throws Exception {
        boolean[] visitados = new boolean[num_vertice()];
        Stack<Integer> pila = new Stack<>();
        pila.push(verticeInicial);

        while (!pila.isEmpty()) {
            Integer verticeActual = pila.pop();
            if (!visitados[verticeActual - 1]) {
                visitados[verticeActual - 1] = true;
                DynamicList<Adyacencia> adyacentes = obtenerAdyacentes(verticeActual);
                for (int i = 0; i < adyacentes.getLength(); i++) {
                    Integer verticeAdyacente = adyacentes.getInfo(i).getDestino();
                    if (!visitados[verticeAdyacente - 1]) {
                        pila.push(verticeAdyacente);
                    }
                }
            }
        }

        for (boolean visitado : visitados) {
            if (!visitado) {
                return false;
            }
        }
        return true;
    }

    public boolean BFS(Integer verticeInicial) throws Exception {
        boolean[] visitados = new boolean[num_vertice()];
        QueueUltimate<Integer> cola = new QueueUltimate<>(num_vertice());
        cola.queue(verticeInicial);

        while (!cola.isEmpty()) {
            Integer verticeActual = cola.dequeue();
            if (!visitados[verticeActual - 1]) {
                visitados[verticeActual - 1] = true;
                DynamicList<Adyacencia> adyacentes = obtenerAdyacentes(verticeActual);
                for (int i = 0; i < adyacentes.getLength(); i++) {
                    Integer verticeAdyacente = adyacentes.getInfo(i).getDestino();
                    if (!visitados[verticeAdyacente - 1]) {
                        cola.queue(verticeAdyacente);
                    }
                }
            }
        }

        for (boolean visitado : visitados) {
            if (!visitado) {
                return false;
            }
        }
        return true;
    }

    public void conectarAleatoriamente() throws Exception {
        Random rand = new Random();
        boolean[][] conexiones = new boolean[num_vertice() + 1][num_vertice() + 1];

        for (int i = 1; i <= num_vertice(); i++) {
            int numConexiones = rand.nextInt(2) + 2;
            int conexionesRealizadas = 0;

            while (conexionesRealizadas < numConexiones) {
                int nodoDestino = rand.nextInt(num_vertice()) + 1;

                if (nodoDestino != i && !conexiones[i][nodoDestino]) {
                    Double dist = calcularDistanciaSubEstaciones((SubEstacion) getLabelE(i), (SubEstacion) getLabelE(nodoDestino));
                    insertar_arista(i, nodoDestino, dist);
                    conexiones[i][nodoDestino] = true;
                    conexiones[nodoDestino][i] = true;
                    conexionesRealizadas++;
                }
            }
        }
    }

    public GrafosEtiquetadosDirigidos<SubEstacion> generarGrafoAleatorio(int numVertices) throws Exception {
        GrafosEtiquetadosDirigidos<SubEstacion> grafo = new GrafosEtiquetadosDirigidos<>(numVertices, SubEstacion.class);
        DynamicList<SubEstacion> subEstacionesAleatorias = generarSubEstacionesAleatorias(numVertices);

        for (int i = 1; i <= numVertices; i++) {
            grafo.labelVertice(i, subEstacionesAleatorias.getInfo(i - 1));
        }

        return grafo;
    }

    private DynamicList<SubEstacion> generarSubEstacionesAleatorias(int numVertices) {
        DynamicList<SubEstacion> subEstaciones = new DynamicList<>();
        for (int i = 0; i < numVertices; i++) {
            SubEstacion subEstacion = new SubEstacion();
            subEstacion.setNombre("Subestacion " + (i + 1));
            subEstacion.getCoordenada().setLongitud(Math.random() * 360 - 180);
            subEstacion.getCoordenada().setLatitud(Math.random() * 180 - 90);
            subEstaciones.add(subEstacion);
        }
        return subEstaciones;
    }

    public static void main(String[] args) throws Exception {
        // Crear el grafo
        int cantidad = 10;
        GrafosEtiquetadosDirigidos grafo = new GrafosEtiquetadosDirigidos(cantidad, SubEstacion.class);
        GrafosEtiquetadosDirigidos grafoAletorio = grafo.generarGrafoAleatorio(cantidad);

        // Medir el tiempo de ejecución del algoritmo de Floyd
        long inicioFloyd = System.currentTimeMillis();
        System.out.println(grafoAletorio.aplicarAlgoritmoFloydConEtiquetas());
        long finFloyd = System.currentTimeMillis();
        long tiempoFloyd = finFloyd - inicioFloyd;
        System.out.println("Tiempo de ejecución del algoritmo de Floyd: " + tiempoFloyd + " milisegundos");

        // Medir el tiempo de ejecución del algoritmo de Bellman-Ford
        long inicioBellman = System.currentTimeMillis();
        System.out.println(grafoAletorio.aplicarAlgoritmoFloydConEtiquetas());
        long finBellman = System.currentTimeMillis();
        long tiempoBellman = finBellman - inicioBellman;
        System.out.println("Tiempo de ejecución del algoritmo de Bellman-Ford: " + tiempoBellman + " milisegundos");
    }
}
