/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vsta;

import Controlador.PozoDao.PozoControl1;
import Controlador.listas.Exception.EmptyException;
import Modelo.PozosLuz;
import Vista.listas.tablas.ModeloAdyacenciaBellman;
import controlador.TDA.grafos.PaintGraph;
import controlador.TDA.listas.DynamicList;

import controlador.Utiles.UtilesFoto;
import controlador.utiles.Utilidades;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import vista.listas.tablas.ModeloAdyacenciaFloyd;

import vsta.utilidades.UtilVistaPozo;

/**
 *
 * @author Alejandro
 */
public class FrmGrafoPozo extends javax.swing.JDialog {

    PozoControl1 pozoControl = new PozoControl1();
    private ModeloAdyacenciaFloyd maf = new ModeloAdyacenciaFloyd();

    /**
     * Creates new form FrmGrafoEscuela
     */
    public FrmGrafoPozo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(this);
        limpiar();
    }

    private void cargarTabla() {

        try {
            maf.setGrafo1(pozoControl.getGrafo());
        } catch (EmptyException ex) {
            System.out.println(ex.getMessage());
        }

        maf.fireTableDataChanged();
        tblMostrar.setModel(maf);
        tblMostrar.updateUI();

    }

    private void mostrarMapa() throws Exception {
        UtilVistaPozo.crearMapaPoste(pozoControl.getGrafo());
        Runtime rt = Runtime.getRuntime();
        Utilidades.abrirNavegadorPredeterminadorWindows("mapas/index.html");
    }

    private void limpiar() {
        try {
            UtilVistaPozo.cargarComboPoste(cbxOrigen);
            UtilVistaPozo.cargarComboPoste(cbxDestino);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        cargarTabla();

        pozoControl.setPozo(null);
        cbxOrigen.setSelectedIndex(0);
        cbxDestino.setSelectedIndex(0);
    }

    private void load() throws Exception {
        try {
            int i = JOptionPane.showConfirmDialog(null, "Esta seguro de cargar el grafo?");
            if (i == JOptionPane.OK_OPTION) {
                System.out.println("HoLA");
                pozoControl.loadGrapgFloydBellman();
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
                if (pozoControl.getGrafo() != null) {
                    pozoControl.guardarGrafoFloydBellman();
                    JOptionPane.showMessageDialog(null, "Grafo guardado");
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede guardar un grafo vacio");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void mostrarGrafo() throws Exception {
        PaintGraph p = new PaintGraph();
        p.updateFile(pozoControl.getGrafo());
        Utilidades.abrirNavegadorPredeterminadorWindows("d3/grafo.html");
    }

    private void Adyacencia() throws EmptyException, Exception {
        try {
            int totalEscuelas = pozoControl.getList().getLength();
            Random random = new Random();
            int[] contadorAdyacencias = new int[totalEscuelas];
            int nodosSinAdyacencias = totalEscuelas / 2;

            for (int i = 0; i < totalEscuelas; i++) {
                int numAdyacencias = Math.min(3, 2);
                if (nodosSinAdyacencias > 0) {
                    numAdyacencias = 0;
                    nodosSinAdyacencias--;
                }

                int indiceOrigen = i;
                int indiceDestino;

                int intentos = 0;
                do {
                    indiceDestino = random.nextInt(totalEscuelas);
                    intentos++;
                } while (intentos < 10 && (indiceOrigen == indiceDestino || contadorAdyacencias[indiceDestino] >= 3));

                if (intentos == 10) {
                    System.out.println("No se pudo encontrar un nodo destino adecuado.");
                    continue;
                }

                contadorAdyacencias[indiceOrigen]++;
                contadorAdyacencias[indiceDestino]++;
                Double distancia = UtilVistaPozo.CalcularDistanciaPoste(
                        pozoControl.getList().getInfo(indiceOrigen),
                        pozoControl.getList().getInfo(indiceDestino)
                );

                distancia = UtilesFoto.redondear(distancia);
                pozoControl.getGrafo().insertEdge(
                        pozoControl.getList().getInfo(indiceOrigen),
                        pozoControl.getList().getInfo(indiceDestino),
                        distancia
                );
            }

            limpiar();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar adyacencias");
        }

    }

    private void buscar() throws Exception {
        if ("Busqueda_Profundidad".equals(cbxBuscar.getSelectedItem().toString())) {
            System.out.println("Busqueda_Profundidad");
            JOptionPane.showMessageDialog(null, pozoControl.getGrafo().DFS(1));
        } else if ("Busqueda_Anchura".equals(cbxBuscar.getSelectedItem().toString())) {
            System.out.println("Busqueda_Anchura");
            JOptionPane.showMessageDialog(null,pozoControl.getGrafo().BFS());
        }
    }
    private void mostrarGrafoFloyd() throws EmptyException, Exception {
        if ("Bellman_Ford".equals(cbxGrafo.getSelectedItem().toString())) {
            System.out.println("Bellman_Ford");
            JOptionPane.showMessageDialog(null, pozoControl.getGrafo().encontrarRutaMasCortaBellman(cbxOrigen.getSelectedIndex() + 1, cbxDestino.getSelectedIndex() + 1));
        } else if ("Floyd".equals(cbxGrafo.getSelectedItem().toString())) {
            System.out.println("Floyd");
            JOptionPane.showMessageDialog(null, pozoControl.getGrafo().encontrarRutaMasCorta(cbxOrigen.getSelectedIndex() + 1, cbxDestino.getSelectedIndex() + 1));
        }

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panel1 = new org.edisoncor.gui.panel.Panel();
        panelRound1 = new org.edisoncor.gui.panel.PanelRound();
        btnMapa = new javax.swing.JButton();
        btnCualquiera = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMostrar = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnRuta = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        cbxBuscar = new javax.swing.JComboBox<>();
        cbxGrafo = new javax.swing.JComboBox<>();
        cbxDestino = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cbxOrigen = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnCargar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnAdyacencia = new javax.swing.JButton();
        btnPozos = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel1.setColorPrimario(new java.awt.Color(204, 204, 255));
        panel1.setColorSecundario(new java.awt.Color(255, 255, 204));
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound1.setLayout(new java.awt.BorderLayout());
        panel1.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, 270, -1));

        btnMapa.setBackground(new java.awt.Color(0, 0, 0));
        btnMapa.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        btnMapa.setForeground(new java.awt.Color(255, 102, 102));
        btnMapa.setText("VER MAPA");
        btnMapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapaActionPerformed(evt);
            }
        });
        panel1.add(btnMapa, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 270, -1));

        btnCualquiera.setBackground(new java.awt.Color(0, 0, 0));
        btnCualquiera.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        btnCualquiera.setForeground(new java.awt.Color(255, 102, 102));
        btnCualquiera.setText("VER GRAFO");
        btnCualquiera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCualquieraActionPerformed(evt);
            }
        });
        panel1.add(btnCualquiera, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 270, -1));

        btnGuardar.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardar.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 102, 102));
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 270, -1));

        tblMostrar.setBackground(new java.awt.Color(255, 204, 204));
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
        jScrollPane2.setViewportView(tblMostrar);

        panel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 810, 160));

        jLabel3.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 0));
        jLabel3.setText("ADYACENCIAS ENTRE POZOS DE LUZ");
        panel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        btnRuta.setText("MOSTRAR RUTA");
        btnRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRutaActionPerformed(evt);
            }
        });
        panel1.add(btnRuta, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 160, -1));

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        panel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 230, 110, -1));

        cbxBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Busqueda_Anchura", "Busqueda_Profundidad" }));
        panel1.add(cbxBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 230, -1, -1));

        cbxGrafo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Floyd", "Bellman_Ford" }));
        panel1.add(cbxGrafo, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, 160, -1));

        panel1.add(cbxDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 390, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 0));
        jLabel1.setText("DESTINO:");
        panel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        cbxOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxOrigenActionPerformed(evt);
            }
        });
        panel1.add(cbxOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 390, -1));

        jLabel4.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 0, 153));
        jLabel4.setText("MÉTODOS PARA BUSCAR GRAFOS SIN CONEXIONES");
        panel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 200, -1, -1));

        jLabel5.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 0));
        jLabel5.setText("ORIGEN:");
        panel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 153));
        jLabel6.setText("¿CON QUE GRAFO DESEAS TRABAJAR?");
        panel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        btnCargar.setBackground(new java.awt.Color(0, 0, 0));
        btnCargar.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        btnCargar.setForeground(new java.awt.Color(255, 102, 102));
        btnCargar.setText("CARGAR INFORMACIÓN ");
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });
        panel1.add(btnCargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, 270, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 0, 153));
        jLabel7.setText("CLIC PARA VER LA RUTA MAS CORTA:");
        panel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        btnAdyacencia.setBackground(new java.awt.Color(0, 0, 0));
        btnAdyacencia.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        btnAdyacencia.setForeground(new java.awt.Color(255, 102, 102));
        btnAdyacencia.setText("GENERAR ADYACENCIAS");
        btnAdyacencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdyacenciaActionPerformed(evt);
            }
        });
        panel1.add(btnAdyacencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 480, -1));

        btnPozos.setBackground(new java.awt.Color(0, 0, 0));
        btnPozos.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        btnPozos.setForeground(new java.awt.Color(255, 102, 102));
        btnPozos.setText("INFORMACIÓN POZOS");
        btnPozos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPozosActionPerformed(evt);
            }
        });
        panel1.add(btnPozos, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 270, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapaActionPerformed
        try {
            mostrarMapa();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnMapaActionPerformed

    private void btnCualquieraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCualquieraActionPerformed
        try {
            mostrarGrafo();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnCualquieraActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarGrafo();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRutaActionPerformed

        try {
            mostrarGrafoFloyd();
        } catch (Exception ex) {
            System.out.println("Hubo un error" + ex.getMessage());
        }


    }//GEN-LAST:event_btnRutaActionPerformed

    private void cbxOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxOrigenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxOrigenActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            buscar();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
        try {
            load();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnCargarActionPerformed

    private void btnAdyacenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdyacenciaActionPerformed
        try {
            Adyacencia();
        } catch (Exception ex) {
            Logger.getLogger(FrmGrafoPozo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAdyacenciaActionPerformed

    private void btnPozosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPozosActionPerformed
        FrmPozos fp = new FrmPozos();
        fp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPozosActionPerformed

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
            java.util.logging.Logger.getLogger(FrmGrafoPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGrafoPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGrafoPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGrafoPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmGrafoPozo dialog = null;
                try {
                    dialog = new FrmGrafoPozo(new javax.swing.JFrame(), true);
                } catch (Exception ex) {
                    Logger.getLogger(FrmGrafoPozo.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdyacencia;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnCualquiera;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMapa;
    private javax.swing.JButton btnPozos;
    private javax.swing.JButton btnRuta;
    private javax.swing.JComboBox<String> cbxBuscar;
    private javax.swing.JComboBox<String> cbxDestino;
    private javax.swing.JComboBox<String> cbxGrafo;
    private javax.swing.JComboBox<String> cbxOrigen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.PanelRound panelRound1;
    private javax.swing.JTable tblMostrar;
    // End of variables declaration//GEN-END:variables

}
