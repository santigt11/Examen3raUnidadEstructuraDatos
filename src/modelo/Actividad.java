/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba3.modelo;

import ExamenU3EstructuraDatos.modelo.Tarea;

/**
 *
 * @author Alejandro
 */
public class Actividad {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Tarea grafoTarea; 
    private Integer numTareas;

    public Actividad(Integer id, String nombre, String descripcion, Tarea grafoTarea, Integer numTareas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.grafoTarea = grafoTarea;
        this.numTareas = numTareas;
    }
    
    public Actividad() {
        this.id = null;
        this.nombre = null;
        this.descripcion = null;
        this.grafoTarea = null;
        this.numTareas =null;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tarea getGrafoTarea() {
        return grafoTarea;
    }

    public void setGrafoTarea(Tarea grafoTarea) {
        this.grafoTarea = grafoTarea;
    }

    public Integer getNumTareas() {
        return numTareas;
    }

    public void setNumTareas(Integer numTareas) {
        this.numTareas = numTareas;
    }
    
    
        
}
