/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Santiago
 */
public class PaintGraph {
    private String URL = "d3/grafo.js";

    public void updateFile(Grafo graph) throws IOException, EmptyException {
        
        String nodes = "var nodes = new vis.DataSet([\n";
        for (int i = 1; i <= graph.num_vertice(); i++) {
            nodes += "{ id: " + i + ", label: \"Node " + i + "\" },\n";
        }
        nodes += "]);\n\n";
        
        String edges = "var edges = new vis.DataSet([\n";
        for (int i = 1; i <= graph.num_vertice(); i++) {
            DynamicList<Adyacencia> adyacentes = graph.adyacentes(i);
            for (int j = 0; j < adyacentes.getLength(); j++) {
                Adyacencia adyacencia = adyacentes.getInfo(j);
                edges += "{ from: " + i + ", to: " + adyacencia.getDestino() + " },\n";
            }
        }
        edges += "]);\n\n";
        
        String paint = nodes + edges +
                "var container = document.getElementById(\"mynetwork\");\n" +
                "var data = {\n" +
                "  nodes: nodes,\n" +
                "  edges: edges,\n" +
                "};\n" +
                "var options = {};\n" +
                "var network = new vis.Network(container, data, options);";
        
        FileWriter load = new FileWriter(URL);
        load.write(paint);
        load.close();
    }
    
    public void updateFile(GrafosEtiquetadosDirigidos graph) throws IOException, EmptyException, Exception {
        
        String nodes = "var nodes = new vis.DataSet([\n";
        for (int i = 1; i <= graph.num_vertice(); i++) {
            nodes += "{ id: " + i + ", label: \" " + graph.getLabelE(i) + "\" },\n";
        }
        nodes += "]);\n\n";
        
        String edges = "var edges = new vis.DataSet([\n";
        for (int i = 1; i <= graph.num_vertice(); i++) {
            DynamicList<Adyacencia> adyacentes = graph.adyacentes(i);
            for (int j = 0; j < adyacentes.getLength(); j++) {
                Adyacencia adyacencia = adyacentes.getInfo(j);
                edges += "{ from: " + i + ", to: " + adyacencia.getDestino() + ", label: \"" + adyacencia.getPeso() +"\" },\n";
            }
        }
        edges += "]);\n\n";
        
        String paint = nodes + edges +
                "var container = document.getElementById(\"mynetwork\");\n" +
                "var data = {\n" +
                "  nodes: nodes,\n" +
                "  edges: edges,\n" +
                "};\n" +
                "var options = {};\n" +
                "var network = new vis.Network(container, data, options);";
        
        FileWriter load = new FileWriter(URL);
        load.write(paint);
        load.close();
    }
}
