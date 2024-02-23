/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.TDA.grafos;


import Colas.QueueUltimate;

import controlador.TDA.grafos.Adyacencia;
import controlador.TDA.grafos.GrafosEtiquetadosDirigidos;
import controlador.TDA.grafos.PaintGraph;
import controlador.TDA.listas.DynamicList;
import controlador.utiles.Utilidades;


/**
 *
 * @author Alejandro
 */
public class GrafoFloydBellman<E> extends GrafosEtiquetadosDirigidos<E> {

    public Double[][] distancias;

    public GrafoFloydBellman(Integer num_vertice, Class<E> clazz) {
        super(num_vertice, clazz);

    }

    private int aplicarAlgoritmoFloydConEtiquetas() throws Exception {
    int ciclos = 0;

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

    for (int k = 1; k <= num_vertice(); k++) {
        for (int i = 1; i <= num_vertice(); i++) {
            for (int j = 1; j <= num_vertice(); j++) {
                ciclos++;
                if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                    distancias[i][j] = distancias[i][k] + distancias[k][j];
                }
            }
        }
    }
    System.out.println("Matriz de distancias con etiquetas después de aplicar Floyd:");
    System.out.printf("%-40s", ""); 
    for (int i = 1; i <= num_vertice(); i++) {
        System.out.printf("%-30s", getLabelE(i));
    }
    System.out.println();

    for (int i = 1; i <= num_vertice(); i++) {
        System.out.printf("%-40s", getLabelE(i));
        for (int j = 1; j <= num_vertice(); j++) {
            if (distancias[i][j] == Double.POSITIVE_INFINITY) {
                System.out.printf("%-30s", "INF");
            } else {
                System.out.printf("%-30.2f", distancias[i][j]);
            }
        }
        System.out.println();
    }
    this.distancias = distancias;

    return ciclos;
}

    public String encontrarRutaMasCorta(int origen, int destino) throws Exception {
        try {
        int ciclos = aplicarAlgoritmoFloydConEtiquetas();
        System.out.println("Total de ciclos realizados: " + ciclos);
    } catch (Exception e) {
        return "Error al aplicar el algoritmo de Floyd: " + e.getMessage();
    }

        if (distancias[origen][destino] == Double.POSITIVE_INFINITY) {
            return "No hay ruta entre " + getLabelE(origen) + " y " + getLabelE(destino);
        }

        StringBuilder rutaMasCorta = new StringBuilder();
        rutaMasCorta.append("Camino más corto desde ").append(getLabelE(origen)).append(" hasta ").append(getLabelE(destino)).append(":\n");

        int nodoActual = origen;
        double sumaPesos = 0.0;

        while (nodoActual != destino) {
            rutaMasCorta.append(getLabelE(nodoActual));

            int siguienteNodo = encontrarVecinoMasCercano(nodoActual, destino);
            if (siguienteNodo == -1) {
                break; // No hay vecinos o no se encontró un camino, salir del bucle
            }

            sumaPesos += distancias[nodoActual][siguienteNodo];
            rutaMasCorta.append(" -> ");
            nodoActual = siguienteNodo;
        }

        rutaMasCorta.append(getLabelE(destino));
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
    //Busqueda en profunfidad
    public String DFS(Integer verticeInicial) throws Exception {
    boolean[] visitados = new boolean[num_vertice()];
    DFSUtil(verticeInicial, visitados);
    for (boolean visitado : visitados) {
        if (!visitado) {
            return "El grafo no está conectado";
        }
    }
    return "El grafo está conectado";
}


private void DFSUtil(Integer verticeActual, boolean[] visitados) throws Exception {
    visitados[verticeActual - 1] = true;
    System.out.println("Visitando nodo: " + verticeActual);
    DynamicList<Adyacencia> adyacentes = adyacentes(verticeActual);
    for (int i = 0; i < adyacentes.getLength(); i++) {
        Adyacencia adyacente = adyacentes.getInfo(i);
        Integer verticeAdyacente = adyacente.getDestino();
        if (!visitados[verticeAdyacente - 1]) {
            DFSUtil(verticeAdyacente, visitados);
        }
    }
}
//Busqueda en anchura
    public String BFS() throws Exception {
    int verticeInicial = 1;  
    boolean[] visitados = new boolean[num_vertice()];
    BFSUtil(verticeInicial, visitados);
    for (boolean visitado : visitados) {
        if (!visitado) {
            return "El grafo no está conectado"; 
        }
    }
    return "El grafo está conectado"; 
}

private void BFSUtil(Integer verticeInicial, boolean[] visitados) throws Exception {
    QueueUltimate<Integer> cola = new QueueUltimate<>(num_vertice());
    visitados[verticeInicial - 1] = true;
    cola.queue(verticeInicial);
    while (!cola.isEmpty()) {
        Integer verticeActual = cola.dequeue();
        System.out.println("Visitando nodo: " + verticeActual);
        DynamicList<Adyacencia> adyacentes = adyacentes(verticeActual);
        for (int i = 0; i < adyacentes.getLength(); i++) {
            Adyacencia adyacente = adyacentes.getInfo(i);
            Integer verticeAdyacente = adyacente.getDestino();
            if (!visitados[verticeAdyacente - 1]) {
                visitados[verticeAdyacente - 1] = true;
                cola.queue(verticeAdyacente);
            }
        }
    }
}

private void aplicarAlgoritmoBellmanFord(int nodoOrigen) throws Exception {
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

    int totalCiclos = 0;  // Agrega un contador para contar los ciclos

    for (int i = 1; i <= num_vertice() - 1; i++) {
        for (int u = 1; u <= num_vertice(); u++) {
            for (int v = 1; v <= num_vertice(); v++) {
                if (existe_arista(u, v)) {
                    double pesoUV = peso_arista(u, v);

                    if (distancias[nodoOrigen][u] + pesoUV < distancias[nodoOrigen][v]) {
                        distancias[nodoOrigen][v] = distancias[nodoOrigen][u] + pesoUV;
                        totalCiclos++;  // Incrementa el contador
                    }
                }
            }
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

    System.out.println("Matriz de distancias con etiquetas después de aplicar Bellman-Ford:");
    System.out.printf("%-40s", ""); // Espacios adicionales para ajustar la alineación
    for (int i = 1; i <= num_vertice(); i++) {
        System.out.printf("%-30s", getLabelE(i));
    }
    System.out.println();

    for (int i = 1; i <= num_vertice(); i++) {
        System.out.printf("%-40s", getLabelE(i));

        for (int j = 1; j <= num_vertice(); j++) {
            if (distancias[i][j] == Double.POSITIVE_INFINITY) {
                System.out.printf("%-30s", "INF");
            } else {
                System.out.printf("%-30.2f", distancias[i][j]);
            }
        }
        System.out.println();
    }

    System.out.println("Total de ciclos realizados: " + totalCiclos);  // Imprime el total de ciclos
    this.distancias = distancias;
}


   public String encontrarRutaMasCortaBellman(int origen, int destino) throws Exception {
    try {
        aplicarAlgoritmoBellmanFord(origen);
    } catch (Exception e) {
        return "Error al aplicar el algoritmo de Bellman-Ford: " + e.getMessage();
    }

    if (distancias[origen][destino] == Double.POSITIVE_INFINITY) {
        return "No hay ruta entre " + getLabelE(origen) + " y " + getLabelE(destino);
    }

    StringBuilder rutaMasCorta = new StringBuilder();
    rutaMasCorta.append("Camino más corto desde ").append(getLabelE(origen)).append(" hasta ").append(getLabelE(destino)).append(":\n");

    int nodoActual = origen;
    double sumaPesos = 0.0;

    while (nodoActual != destino) {
        rutaMasCorta.append(getLabelE(nodoActual)).append(" -> ");  // Usamos "->" en lugar de espacio

        int siguienteNodo = encontrarVecinoMasCercano(nodoActual, destino);
        if (siguienteNodo == -1) {
            break; // No hay vecinos o no se encontró un camino, salir del bucle
        }

        sumaPesos += distancias[nodoActual][siguienteNodo];
        nodoActual = siguienteNodo;
    }

    rutaMasCorta.append(getLabelE(destino));
    rutaMasCorta.append("\nSuma de los pesos: ").append(sumaPesos);

    return rutaMasCorta.toString();
}


    public static void main(String[] args) {
        try {
            GrafoFloydBellman<String> grafoFloyd = new GrafoFloydBellman<>(6, String.class);
            grafoFloyd.labelVertice(1, "Estefania");
            grafoFloyd.labelVertice(2, "Luna");
            grafoFloyd.labelVertice(3, "Jimenez");
            grafoFloyd.labelVertice(4, "Criollo");
            grafoFloyd.labelVertice(5, "Maritza");
            grafoFloyd.labelVertice(6, "Nivelo");
            grafoFloyd.insertEdge("Estefania", "Nivelo", 1.0);
            grafoFloyd.insertEdge("Estefania", "Maritza", 3.0);
            grafoFloyd.insertEdge("Estefania", "Luna", 4.0);
            grafoFloyd.insertEdge("Luna", "Jimenez", 3.0);
            grafoFloyd.insertEdge("Maritza", "Luna", 2.0);
            grafoFloyd.insertEdge("Jimenez", "Criollo", 1.0);
            grafoFloyd.insertEdge("Nivelo", "Maritza", 2.0);
            grafoFloyd.insertEdge("Criollo", "Maritza", 5.0);

            PaintGraph p = new PaintGraph();
            p.updateFile(grafoFloyd);
            Utilidades.abrirNavegadorPredeterminadorWindows("d3/grafo.html");

            String resultado = grafoFloyd.encontrarRutaMasCorta(2,6);
            grafoFloyd.DFS(1);
            System.out.println(resultado);
//            grafoFloyd.BFSAutomatico();

        } catch (Exception e) {
            System.out.println("Error main " + e);
        }
    }

}
