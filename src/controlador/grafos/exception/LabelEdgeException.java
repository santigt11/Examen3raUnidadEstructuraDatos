/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package Controlador.grafos.exception;

/**
 *
 * @author Alejandro
 */
public class LabelEdgeException extends Exception {

    /**
     * Creates a new instance of <code>VerticeException</code> without detail message.
     */
    
    public LabelEdgeException(String msg) {
        super(msg);
    }

    public LabelEdgeException() {
        super("No esta etiuquetado de forma correcta");
    }
}
