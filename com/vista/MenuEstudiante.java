/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vista;

import com.modelo.Colegio;
import com.modelo.Estudiante;
import com.modelo.Curso; 

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
 * Interfaz gráfica para el menú del estudiante.
 * @author river // Mantener autor original si se desea
 */
public class MenuEstudiante extends javax.swing.JFrame {

    private Colegio colegio;
    private Estudiante estudianteActual;

    // Componentes UI traducidos
    private javax.swing.JLabel etiquetaInfo;
    private javax.swing.JButton botonVerCurso;
    private javax.swing.JButton botonVerCalificaciones;
    private javax.swing.JTextArea areaDeTexto;
    private javax.swing.JScrollPane panelDesplazamiento;
    
    /**
     * Crea el nuevo formulario MenuEstudiante.
     */
    public MenuEstudiante() {
        this.colegio = new Colegio("Mi Colegio"); // Nombre del colegio, puede ser configurable

        // Inicialización Manual de UI
        setTitle("Portal del Estudiante");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
        
        etiquetaInfo = new JLabel("Identifíquese por favor...");
        botonVerCurso = new JButton("Ver Mi Curso");
        botonVerCalificaciones = new JButton("Ver Mis Calificaciones");
        
        areaDeTexto = new JTextArea(15, 50);
        areaDeTexto.setEditable(false);
        areaDeTexto.setLineWrap(true);
        areaDeTexto.setWrapStyleWord(true);
        panelDesplazamiento = new JScrollPane(areaDeTexto);

        // Disposición de componentes
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 'topPanel' a 'panelSuperior'
        panelSuperior.add(etiquetaInfo);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 'buttonPanel' a 'panelBotones'
        panelBotones.add(botonVerCurso);
        panelBotones.add(botonVerCalificaciones);

        getContentPane().setLayout(new BorderLayout(5, 5));
        getContentPane().add(panelSuperior, BorderLayout.NORTH);
        getContentPane().add(panelBotones, BorderLayout.CENTER);
        getContentPane().add(panelDesplazamiento, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 400));

        // Estado inicial de los botones
        botonVerCurso.setEnabled(false);
        botonVerCalificaciones.setEnabled(false);

        // ActionListeners
        botonVerCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (estudianteActual != null && estudianteActual.obtenerCurso() != null && estudianteActual.obtenerCurso().obtenerCodigo() != 0) { // Uso de métodos traducidos
                    String informacionCurso = colegio.infoCurso(estudianteActual.obtenerCurso().obtenerCodigo()); // 'info' a 'informacionCurso'
                    areaDeTexto.setText(informacionCurso);
                } else {
                    areaDeTexto.setText("No estás inscrito en ningún curso actualmente.");
                }
            }
        });

        botonVerCalificaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (estudianteActual != null) {
                    String reporte = colegio.reporteEstudiante(estudianteActual.obtenerCodigo()); // Uso de método traducido
                    areaDeTexto.setText(reporte);
                } else {
                    areaDeTexto.setText("No se pudo obtener el reporte académico. Estudiante no identificado.");
                }
            }
        });
        
        // Diferir identificación
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                identificarEstudiante();
            }
        });
    }

    private void identificarEstudiante() {
        String codigoCadena = JOptionPane.showInputDialog(this, "Ingrese su código de estudiante:", "Identificación del Estudiante", JOptionPane.QUESTION_MESSAGE); // 'codigoStr' a 'codigoCadena', título traducido
        if (codigoCadena == null || codigoCadena.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Identificación cancelada. La ventana se cerrará.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            dispose(); 
            return;
        }

        try {
            int codigoEstudiante = Integer.parseInt(codigoCadena);
            this.estudianteActual = colegio.buscarEstudiante(codigoEstudiante);

            if (this.estudianteActual == null) {
                JOptionPane.showMessageDialog(this, "Estudiante con código " + codigoEstudiante + " no encontrado.", "Error de Identificación", JOptionPane.ERROR_MESSAGE);
                dispose(); 
                return;
            }

            // Estudiante encontrado
            etiquetaInfo.setText("Bienvenido/a Estudiante: " + estudianteActual.obtenerNombre()); // Uso de método traducido
            botonVerCurso.setEnabled(true);
            botonVerCalificaciones.setEnabled(true);

            // Opcionalmente, mostrar información inicial del curso en la etiqueta
            if (estudianteActual.obtenerCurso() != null && estudianteActual.obtenerCurso().obtenerCodigo() != 0) { // Uso de métodos traducidos
                Curso cursoAsignado = colegio.buscarCursoPorCodigo(estudianteActual.obtenerCurso().obtenerCodigo());
                if (cursoAsignado != null) {
                    etiquetaInfo.setText(etiquetaInfo.getText() + " - Curso: " + cursoAsignado.obtenerGrado() + "-" + cursoAsignado.obtenerGrupo()); // Uso de métodos traducidos
                } else {
                     etiquetaInfo.setText(etiquetaInfo.getText() + " - Curso ID " + estudianteActual.obtenerCurso().obtenerCodigo() + " no encontrado.");
                }
            } else {
                etiquetaInfo.setText(etiquetaInfo.getText() + " - No estás asignado a un curso.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El código del estudiante debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            identificarEstudiante(); 
        }
    }

    /**
     * Este método es llamado desde el constructor para inicializar el formulario.
     * ADVERTENCIA: No modificar este código. El contenido de este método siempre
     * es regenerado por el Editor de Formularios.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        // La configuración manual se realiza en el constructor. Esto puede dejarse vacío o eliminarse si no se llama.
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // DISPOSE_ON_CLOSE para ventanas secundarias

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


    // Declaración de Variables - ¡NO modificar el bloque generado por el editor!                     
    // End of variables declaration                   
}
