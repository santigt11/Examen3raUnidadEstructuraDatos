/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

/**
 *
 * @author Alejandro
 */
public class Tarea {
    private Integer id;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFinal;
    private String tituloTarea;

    public Tarea(Integer id, String descripcion, Date fechaInicio, Date fechaFinal, String tituloTarea) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.tituloTarea = tituloTarea;
    }
    public Tarea() {
        this.id = null;
        this.descripcion =null;
        this.fechaInicio = null;
        this.fechaFinal = null;
        this.tituloTarea = null;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getTituloTarea() {
        return tituloTarea;
    }

    public void setTituloTarea(String tituloTarea) {
        this.tituloTarea = tituloTarea;
    }

    @Override
    public String toString() {
        return "Tarea: " + getTituloTarea();
    }
    
    
}
