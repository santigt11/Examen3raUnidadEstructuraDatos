/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;


import Controlador.grafos.exception.LabelEdgeException;
import Controlador.grafos.exception.VerticeException;
import controlador.TDA.listas.DynamicList;
import controlador.utiles.Utilidades;
import java.util.HashMap;
import java.lang.reflect.Array;
/**
 *
 * @author Alejandro
 */
public class GrafosEtiquetadosDirigidos<E> extends GrafoDirigido {
    protected E []labels;
    protected HashMap<E,Integer>dicVertices;  //HasMap E= objeto
    private Class<E> clazz;
    
    public GrafosEtiquetadosDirigidos(Integer num_vertice,Class clazz) {
        super(num_vertice);
        this.clazz=clazz;
        labels=(E[]) Array.newInstance(clazz, num_vertice+1);
        dicVertices=new HashMap<>(num_vertice);
    }
    //Método que permite rescatar el nro de vertice asociado a la etiqueta
    public Integer getVerticeE(E label) throws VerticeException{
        Integer aux=dicVertices.get(label);
        if(aux !=null)
        return aux;
        else 
            throw new VerticeException("No se en encuentra el vertice asociado");
        
    }
    //dic vertice asociación vertice con etiqueta
    public E getLabelE(Integer v) throws VerticeException{
        
        if(v<=num_vertice())
        return labels[v];
        else 
            throw new VerticeException("No se encuentra ese vertice");
    }
    /**
     * 
     */
   public Boolean isEdgeE(E o,E d) throws Exception{
       return existe_arista(getVerticeE(o),getVerticeE(d));
   }
   /**
    * 
    * @param o origen
    * @param d destino
    * @param weith  peso
    */
   public void insertEdge(E o,E d,Double weigth) throws Exception{
       if(isAllLabelIsGraph()){
           insertar_arista(getVerticeE(o), getVerticeE(d), weigth);
       }else
           throw new LabelEdgeException();
       
   }
    
   public void insertEdge(E o,E d) throws Exception{
       insertar_arista(getVerticeE(o), getVerticeE(d), Double.NaN);
   }
   public DynamicList<Adyacencia> adjacents(E label) throws LabelEdgeException, VerticeException{
       if(isAllLabelIsGraph()){
            return adyacentes(getVerticeE(label));
       }else
           throw new LabelEdgeException();
      
   } 
   //Método principal
   public void labelVertice(Integer v,E label){
       labels[v]=label;
       dicVertices.put(label, v);
       
   }
   public Boolean isAllLabelIsGraph(){
       Boolean band=true;
       for (int i = 1; i < labels.length; i++) {
           if(labels[i]==null){
               band=false;
               break;
           }
       }
       return band;
   }
   
    @Override
    public String toString() {
        StringBuilder grafo = new StringBuilder("GRAFO").append("\n");
        try {
            for (int i = 1; i <= num_vertice(); i++) {
                E label = (E) clazz.getSimpleName();
                 grafo.append("[").append(i).append("] ").append(getLabelE(i)).append("\n");
                DynamicList<Adyacencia> list = adyacentes(i);
                for (int j = 0; j < list.getLength(); j++) {
                    Adyacencia a = list.getInfo(j);
                    grafo.append("adyacente ").append(a.getDestino()).append(" peso ").append(a.getPeso()).append("\n");
                }
            }
        } catch (Exception e) {
        }
        return grafo.toString();
    }
    
   
    
   
    public static void main(String[] args) {
        try {
            GrafosEtiquetadosDirigidos<String> ged=new GrafosEtiquetadosDirigidos(6,String.class);
            ged.labelVertice(1, "Estefania");
             ged.labelVertice(2, "Luna");
              ged.labelVertice(3, "Jimenez");
               ged.labelVertice(4, "Criollo");
               ged.labelVertice(5, "Maritza");
               ged.labelVertice(6, "Nivelo");
               ged.insertEdge("Estefania", "Nivelo",100.0);
            System.out.println(ged.toString());
             PaintGraph p = new PaintGraph();
            p.updateFile(ged);
             Utilidades.abrirNavegadorPredeterminadorWindows("d3/grafo.html");
        } catch (Exception e) {
            System.out.println("Error main "+e);
        }
    }
}
