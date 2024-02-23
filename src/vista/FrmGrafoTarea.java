/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.DAO.grafosEjemplo.ActividadDao;
import controlador.TDA.grafos.PaintGraph;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.Utiles.UtilesFoto;
import controlador.utiles.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Actividad;
import modelo.Tarea;
import vista.Utilidades.UtilVistaActividad;
import vista.tablas.ModeloAdyacenciaFloyd;

/**
 *
 * @author Santiago
 */
public class FrmGrafoTarea extends javax.swing.JFrame {
    private ModeloAdyacenciaFloyd maf = new ModeloAdyacenciaFloyd();
    private ActividadDao controlActividad=new ActividadDao();
    
    /**
     * Creates new form FrmActividad
     */
    public FrmGrafoTarea(Actividad actividad, DynamicList<Tarea> tareas) throws Exception {
        initComponents();
        controlActividad.setActividad(actividad);
        controlActividad.setEscuelas(tareas);
        this.setLocationRelativeTo(this);
        limpiar();
    }
    private void cargarTabla() throws Exception {
        try {
            maf.setGrafoTarea(controlActividad.getGrafo());
        } catch (EmptyException ex) {
            System.out.println(ex.getMessage());
        }
        maf.fireTableDataChanged();
        tblMostrar.setModel(maf);
        tblMostrar.updateUI();

    }
    private void limpiar() throws Exception {
        try {
            UtilVistaActividad.cargarComboTareas(controlActividad.getTareas(), cbxOrigen);
            UtilVistaActividad.cargarComboTareas(controlActividad.getTareas(), cbxDestino);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        cargarTabla();

        controlActividad.setGrafo(null);
        cbxOrigen.setSelectedIndex(0);
        cbxDestino.setSelectedIndex(0);
    }

    private void load() throws Exception {
        try {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de cargar el grafo?");
            if (i == JOptionPane.OK_OPTION) {
                System.out.println("HoLA");
                controlActividad.loadGraph();
                limpiar();
                JOptionPane.showMessageDialog(null, "Grafo cargado con exito");
            }
        } catch (Exception e) {
        }

    }

    public void guardarGrafo() {
        try {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de guardar?",
                    "Advertencia", JOptionPane.OK_CANCEL_OPTION);

            if (i == JOptionPane.OK_OPTION) {
                if (controlActividad.getGrafo() != null) {
                    controlActividad.guardarGrafo();
                    JOptionPane.showMessageDialog(null, "Grafo guardado");
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede guardar un grafo vacio");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private void Adyacencia() {
//        try {
//            Integer o = cbxOrigen.getSelectedIndex();
//            Integer d = cbxDestino.getSelectedIndex();
//            if (o.intValue() == d.intValue()) {
//                JOptionPane.showMessageDialog(null, "Escoja escuelas diferentes");
//            } else {
////                Double dist = UtilesVistaEscuela.CalcularDistanciaEscuela(ed.getList().getInfo(o), ed.getList().getInfo(d));
////                dist = UtilesFoto.redondear(dist);
////                ed.getGrafo().insertEdge(ed.getList().getInfo(o), ed.getList().getInfo(d), dist);
////                JOptionPane.showMessageDialog(null, "Adyacencia Generada");
//                limpiar();
//            }
//        } catch (Exception e) {
//            System.out.println(":c");
//        }
try {
    Integer o = cbxOrigen.getSelectedIndex();
    Integer d = cbxDestino.getSelectedIndex();
    
    if (o.intValue() == d.intValue()) {
        JOptionPane.showMessageDialog(null, "Escoja escuelas diferentes");
    } else {
        double nro_tarea1 = ;
        double nro_taraFf = // Obtener el valor correspondiente;      
        double resultado = Math.sqrt(nro_tarea1) + nro_taraFf + 1 / (nro_tarea1 + nro_taraFf);   
        limpiar();
    }
} catch (Exception e) {
    System.out.println(":c");
}

    }

    private void mostrarGrafo() throws Exception {
        PaintGraph p = new PaintGraph();
        p.updateFile(controlActividad.getGrafo());
        Utilidades.abrirNavegadorPredeterminadorWindows("d3/grafo.html");
    }
    private void buscar() throws Exception {
        if ("Busqueda_Profundidad".equals(cbxBuscar.getSelectedItem().toString())) {
            System.out.println("Busqueda_Profundidad");
            JOptionPane.showMessageDialog(null, controlActividad.getGrafo().DFS(1));
        } else if ("Busqueda_Anchura".equals(cbxBuscar.getSelectedItem().toString())) {
            System.out.println("Busqueda_Anchura");
            JOptionPane.showMessageDialog(null,controlActividad.getGrafo().BFS());
        }
    }
    private void mostrarGrafoFloyd() throws EmptyException, Exception {
        if ("Bellman_Ford".equals(cbxGrafo.getSelectedItem().toString())) {
            System.out.println("Bellman_Ford");
            JOptionPane.showMessageDialog(null, controlActividad.getGrafo().encontrarRutaMasCortaBellman(cbxOrigen.getSelectedIndex() + 1, cbxDestino.getSelectedIndex() + 1));
        } else if ("Floyd".equals(cbxGrafo.getSelectedItem().toString())) {
            System.out.println("Floyd");
            JOptionPane.showMessageDialog(null, controlActividad.getGrafo().encontrarRutaMasCorta(cbxOrigen.getSelectedIndex() + 1, cbxDestino.getSelectedIndex() + 1));
        }

    }
    

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelHeader1 = new org.edisoncor.gui.label.LabelHeader();
        labelRound1 = new org.edisoncor.gui.label.LabelRound();
        labelRound2 = new org.edisoncor.gui.label.LabelRound();
        labelRound3 = new org.edisoncor.gui.label.LabelRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMostrar = new javax.swing.JTable();
        cbxOrigen = new javax.swing.JComboBox<>();
        cbxDestino = new javax.swing.JComboBox<>();
        btnAdyacencia = new org.edisoncor.gui.button.ButtonAero();
        cbxGrafo = new javax.swing.JComboBox<>();
        labelRound4 = new org.edisoncor.gui.label.LabelRound();
        btnRuta = new javax.swing.JButton();
        btGuardar = new org.edisoncor.gui.button.ButtonAero();
        btCancelar1 = new org.edisoncor.gui.button.ButtonAero();
        labelRound5 = new org.edisoncor.gui.label.LabelRound();
        cbxBuscar = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        btnAdyacencia1 = new org.edisoncor.gui.button.ButtonAero();
        btGuardar1 = new org.edisoncor.gui.button.ButtonAero();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelHeader1.setText("Guardar actividades");
        jPanel1.add(labelHeader1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 720, -1));

        labelRound1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelRound1.setText("Origen");
        labelRound1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel1.add(labelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 46, -1, -1));

        labelRound2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelRound2.setText("Destino:");
        labelRound2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel1.add(labelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, -1, -1));

        labelRound3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelRound3.setText("MÉTODOS PARA BUSCAR GRAFOS SIN CONEXIONES");
        labelRound3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel1.add(labelRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 130, -1, -1));

        tblMostrar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblMostrar);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 667, 190));

        cbxOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxOrigenActionPerformed(evt);
            }
        });
        jPanel1.add(cbxOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 150, -1));

        jPanel1.add(cbxDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 150, -1));

        btnAdyacencia.setBackground(new java.awt.Color(255, 0, 0));
        btnAdyacencia.setText("Generar Adyacencias");
        jPanel1.add(btnAdyacencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, -1, -1));

        cbxGrafo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Floyd", "Bellman_Ford" }));
        jPanel1.add(cbxGrafo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 90, 160, -1));

        labelRound4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelRound4.setText("Tipo de grafo a trabajar:");
        labelRound4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel1.add(labelRound4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        btnRuta.setText("MOSTRAR RUTA");
        btnRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRutaActionPerformed(evt);
            }
        });
        jPanel1.add(btnRuta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 230, -1));

        btGuardar.setBackground(new java.awt.Color(51, 255, 0));
        btGuardar.setText("GUARDAR");
        jPanel1.add(btGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 121, -1));

        btCancelar1.setBackground(new java.awt.Color(255, 0, 0));
        btCancelar1.setText("CANCELAR");
        jPanel1.add(btCancelar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 470, 121, -1));

        labelRound5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelRound5.setText("CLIC PARA VER LA RUTA MAS CORTA:");
        labelRound5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel1.add(labelRound5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        cbxBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Busqueda_Anchura", "Busqueda_Profundidad" }));
        jPanel1.add(cbxBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, -1, -1));

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, 110, -1));

        btnAdyacencia1.setBackground(new java.awt.Color(255, 0, 0));
        btnAdyacencia1.setText("VER GRAFO");
        jPanel1.add(btnAdyacencia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, -1, -1));

        btGuardar1.setBackground(new java.awt.Color(0, 255, 255));
        btGuardar1.setText("Cargar Grafo");
        btGuardar1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(btGuardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 220, 121, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxOrigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxOrigenActionPerformed

    private void btnRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRutaActionPerformed

        try {
            mostrarGrafoFloyd();
        } catch (Exception ex) {
            System.out.println("Hubo un error" + ex.getMessage());
        }

    }//GEN-LAST:event_btnRutaActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            buscar();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmGrafoTarea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGrafoTarea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGrafoTarea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGrafoTarea.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FrmGrafoTarea(new Actividad(), new DynamicList<>()).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(FrmGrafoTarea.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.edisoncor.gui.button.ButtonAero btCancelar1;
    private org.edisoncor.gui.button.ButtonAero btGuardar;
    private org.edisoncor.gui.button.ButtonAero btGuardar1;
    private org.edisoncor.gui.button.ButtonAero btnAdyacencia;
    private org.edisoncor.gui.button.ButtonAero btnAdyacencia1;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnRuta;
    private javax.swing.JComboBox<String> cbxBuscar;
    private javax.swing.JComboBox<String> cbxDestino;
    private javax.swing.JComboBox<String> cbxGrafo;
    private javax.swing.JComboBox<String> cbxOrigen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private org.edisoncor.gui.label.LabelHeader labelHeader1;
    private org.edisoncor.gui.label.LabelRound labelRound1;
    private org.edisoncor.gui.label.LabelRound labelRound2;
    private org.edisoncor.gui.label.LabelRound labelRound3;
    private org.edisoncor.gui.label.LabelRound labelRound4;
    private org.edisoncor.gui.label.LabelRound labelRound5;
    private javax.swing.JTable tblMostrar;
    // End of variables declaration//GEN-END:variables
}
