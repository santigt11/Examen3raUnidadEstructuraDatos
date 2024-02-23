/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.grafos.exception.VerticeException;

/**
 *
 * @author Santiago
 */
public class GrafosEtiquetadosNoDirigidos<E> extends GrafosEtiquetadosDirigidos<E> {

    public GrafosEtiquetadosNoDirigidos(Integer num_vertices, Class clazz) {
        super(num_vertices, clazz);
    }

    @Override
    public void insertar_arista(Integer v1, Integer v2, Double peso) throws Exception {
        if (v1.intValue() <= num_vertice() && v2.intValue() <= num_vertice()) {
            if (!existe_arista(v1, v2)) {
                setNum_aristas(num_aristas() + 1);
                getListaAdyacencias()[v1].add(new Adyacencia(v2, peso));
                getListaAdyacencias()[v2].add(new Adyacencia(v1, peso));
//                num_aristas++;
//                listaAdyacencias[v1].add(new Adyacencia(v2, peso));
            }
        } else {
            throw new VerticeException();
        }
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
        System.out.println(ged.toString());
    }
}
