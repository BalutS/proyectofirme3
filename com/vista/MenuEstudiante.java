/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vista;

import com.modelo.Colegio;
import com.modelo.Estudiante;
import com.modelo.Curso; // For displaying course info

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author river
 */
public class MenuEstudiante extends javax.swing.JFrame {

    private Colegio colegio;
    private Estudiante estudianteActual;

    private javax.swing.JLabel infoLabel;
    private javax.swing.JButton verCursoButton;
    private javax.swing.JButton verCalificacionesButton;
    private javax.swing.JTextArea displayArea;
    private javax.swing.JScrollPane scrollPane;
    
    /**
     * Creates new form MenuEstudiante
     */
    public MenuEstudiante() {
        // initComponents(); // Original call
        this.colegio = new Colegio("Mi Colegio");

        // Manual UI Initialization
        setTitle("Portal del Estudiante");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
        
        infoLabel = new JLabel("Identifíquese por favor...");
        verCursoButton = new JButton("Ver Mi Curso");
        verCalificacionesButton = new JButton("Ver Mis Calificaciones");
        
        displayArea = new JTextArea(15, 50);
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(displayArea);

        // Layout components
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(infoLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(verCursoButton);
        buttonPanel.add(verCalificacionesButton);

        getContentPane().setLayout(new BorderLayout(5, 5));
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));

        // Initial state of buttons
        verCursoButton.setEnabled(false);
        verCalificacionesButton.setEnabled(false);

        // Action Listeners
        verCursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (estudianteActual != null && estudianteActual.getCurso() != null && estudianteActual.getCurso().getCodigo() != 0) {
                    // estudianteActual.getCurso() might be a placeholder with only ID.
                    // colegio.infoCurso() is designed to take a curso ID and return fully hydrated info.
                    String info = colegio.infoCurso(estudianteActual.getCurso().getCodigo());
                    displayArea.setText(info);
                } else {
                    displayArea.setText("No estás inscrito en ningún curso actualmente.");
                }
            }
        });

        verCalificacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (estudianteActual != null) {
                    // colegio.reporteEstudiante() should handle full hydration
                    String reporte = colegio.reporteEstudiante(estudianteActual.getCodigo());
                    displayArea.setText(reporte);
                } else {
                    // This case should ideally not be reached if identification was successful
                    displayArea.setText("No se pudo obtener el reporte académico. Estudiante no identificado.");
                }
            }
        });
        
        // Defer identification
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                identificarEstudiante();
            }
        });
    }

    private void identificarEstudiante() {
        String codigoStr = JOptionPane.showInputDialog(this, "Ingrese su código de estudiante:", "Identificación Estudiante", JOptionPane.QUESTION_MESSAGE);
        if (codigoStr == null || codigoStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Identificación cancelada. La ventana se cerrará.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            dispose(); // Close the window if identification is cancelled
            return;
        }

        try {
            int codigoEstudiante = Integer.parseInt(codigoStr);
            this.estudianteActual = colegio.buscarEstudiante(codigoEstudiante);

            if (this.estudianteActual == null) {
                JOptionPane.showMessageDialog(this, "Estudiante con código " + codigoEstudiante + " no encontrado.", "Error de Identificación", JOptionPane.ERROR_MESSAGE);
                // Keep buttons disabled, or dispose and re-prompt
                dispose(); // Close on failed identification
                return;
            }

            // Student found
            infoLabel.setText("Bienvenido/a Estudiante: " + estudianteActual.getNombre());
            verCursoButton.setEnabled(true);
            verCalificacionesButton.setEnabled(true);

            // Optionally, display initial course info in label
            if (estudianteActual.getCurso() != null && estudianteActual.getCurso().getCodigo() != 0) {
                // Fetch the full course for display in label, estudianteActual.getCurso() is placeholder
                Curso cursoAsignado = colegio.buscarCursoPorCodigo(estudianteActual.getCurso().getCodigo());
                if (cursoAsignado != null) {
                    infoLabel.setText(infoLabel.getText() + " - Curso: " + cursoAsignado.getGrado() + "-" + cursoAsignado.getGrupo());
                    // Update estudianteActual with the more complete Curso object if needed elsewhere immediately
                    // estudianteActual.setCurso(cursoAsignado); // Not strictly necessary if verCursoButton always re-fetches
                } else {
                     infoLabel.setText(infoLabel.getText() + " - Curso ID " + estudianteActual.getCurso().getCodigo() + " no encontrado.");
                }
            } else {
                infoLabel.setText(infoLabel.getText() + " - No estás asignado a un curso.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El código del estudiante debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            identificarEstudiante(); // Re-prompt on format error
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        // Manual setup is done in constructor. This can be left empty or removed if not called.
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    // End of variables declaration                   
}
