/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vista;

import com.modelo.Colegio;
import com.modelo.Profesor;
import com.modelo.Curso;
import com.modelo.Estudiante; 
import com.modelo.Asignatura; // Added for Calificar Estudiante
import com.modelo.Calificacion; // Added for Calificar Estudiante

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate; // For Calificacion
import java.util.List; 
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
public class MenuDocente extends javax.swing.JFrame {

    private Colegio colegio;
    private Profesor profesorActual;

    private javax.swing.JLabel infoLabel;
    private javax.swing.JButton verCursoButton;
    private javax.swing.JButton listarEstudiantesButton;
    private javax.swing.JButton calificarEstudianteButton;
    private javax.swing.JTextArea displayArea;
    private javax.swing.JScrollPane scrollPane;

    /**
     * Creates new form MenuDocente
     */
    public MenuDocente() {
        this.colegio = new Colegio("Mi Colegio");

        setTitle("Portal del Docente");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
        
        infoLabel = new JLabel("Identifíquese por favor...");
        verCursoButton = new JButton("Ver Mi Curso");
        listarEstudiantesButton = new JButton("Listar Estudiantes de Mi Curso");
        calificarEstudianteButton = new JButton("Calificar Estudiante");

        displayArea = new JTextArea(15, 50); 
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(displayArea);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(infoLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(verCursoButton);
        buttonPanel.add(listarEstudiantesButton);
        buttonPanel.add(calificarEstudianteButton);

        getContentPane().setLayout(new BorderLayout(5, 5)); 
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);
        
        pack(); 
        setLocationRelativeTo(null); 
        setMinimumSize(new Dimension(600, 400)); 

        verCursoButton.setEnabled(false);
        listarEstudiantesButton.setEnabled(false);
        calificarEstudianteButton.setEnabled(false);

        verCursoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (profesorActual != null && profesorActual.getCurso() != null && profesorActual.getCurso().getCodigo() != 0) {
                    String cursoInfo = colegio.infoCurso(profesorActual.getCurso().getCodigo());
                    displayArea.setText(cursoInfo);
                } else {
                    displayArea.setText("No hay información del curso para mostrar o no tiene un curso asignado.");
                }
            }
        });

        listarEstudiantesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (profesorActual != null && profesorActual.getCurso() != null && profesorActual.getCurso().getCodigo() != 0) {
                    Curso cursoDelProfesor = profesorActual.getCurso(); 
                    
                    StringBuilder sb = new StringBuilder("Estudiantes en su curso (" + cursoDelProfesor.getGrado() + "-" + cursoDelProfesor.getGrupo() + "):\n\n");
                    List<Estudiante> estudiantes = colegio.getEstudiantesPorCurso(cursoDelProfesor.getCodigo());
                    
                    if (estudiantes.isEmpty()) {
                        sb.append("No hay estudiantes asignados a este curso.");
                    } else {
                        for (Estudiante est : estudiantes) {
                            sb.append(est.toString()).append("\n--------------------\n");
                        }
                    }
                    displayArea.setText(sb.toString());
                } else {
                    displayArea.setText("No tiene un curso asignado para listar estudiantes.");
                }
            }
        });

        calificarEstudianteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (profesorActual == null || profesorActual.getCurso() == null || profesorActual.getCurso().getCodigo() == 0) {
                    JOptionPane.showMessageDialog(MenuDocente.this, "No tiene un curso asignado para calificar estudiantes.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Step 1: Select Student
                List<Estudiante> estudiantes = colegio.getEstudiantesPorCurso(profesorActual.getCurso().getCodigo());
                if (estudiantes.isEmpty()) {
                    JOptionPane.showMessageDialog(MenuDocente.this, "No hay estudiantes en su curso para calificar.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Estudiante[] estudiantesArray = estudiantes.toArray(new Estudiante[0]);
                Estudiante estudianteSeleccionado = (Estudiante) JOptionPane.showInputDialog(MenuDocente.this, "Seleccione Estudiante:", "Calificar Estudiante - Paso 1", JOptionPane.QUESTION_MESSAGE, null, estudiantesArray, estudiantesArray[0]);
                if (estudianteSeleccionado == null) return; // User cancelled

                // Step 2: Select Asignatura
                List<Asignatura> asignaturasDelEstudiante = colegio.getAsignaturasPorEstudiante(estudianteSeleccionado.getCodigo());
                if (asignaturasDelEstudiante.isEmpty()) {
                    JOptionPane.showMessageDialog(MenuDocente.this, "El estudiante seleccionado no tiene asignaturas.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Asignatura[] asignaturasArray = asignaturasDelEstudiante.toArray(new Asignatura[0]);
                Asignatura asignaturaSeleccionada = (Asignatura) JOptionPane.showInputDialog(MenuDocente.this, "Seleccione Asignatura:", "Calificar Estudiante - Paso 2", JOptionPane.QUESTION_MESSAGE, null, asignaturasArray, asignaturasArray[0]);
                if (asignaturaSeleccionada == null) return; // User cancelled

                // Step 3: Enter Calificacion Details
                String nombreCalificacion = JOptionPane.showInputDialog(MenuDocente.this, "Ingrese nombre/descripción de la calificación (e.g., Examen Parcial 1):");
                if (nombreCalificacion == null || nombreCalificacion.trim().isEmpty()) return;

                String valorStr = JOptionPane.showInputDialog(MenuDocente.this, "Ingrese la nota (valor numérico):");
                if (valorStr == null || valorStr.trim().isEmpty()) return;
                
                float valorNota;
                try {
                    valorNota = Float.parseFloat(valorStr);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(MenuDocente.this, "La nota debe ser un valor numérico.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // For simplicity, use the constructor Calificacion(nombre, nota). 
                // Other fields like periodo and fecha will be defaulted by Calificacion's constructor or toDaoString/fromDaoString.
                Calificacion nuevaCalificacion = new Calificacion(nombreCalificacion, valorNota);
                // If specific periodo/fecha are needed:
                // String periodoStr = JOptionPane.showInputDialog(MenuDocente.this, "Ingrese el periodo (e.g., 1, 2, 3):");
                // int periodo = Integer.parseInt(periodoStr); // Add try-catch
                // LocalDate fecha = LocalDate.now(); // Or prompt for date
                // nuevaCalificacion.setPeriodo(periodo);
                // nuevaCalificacion.setFecha(fecha);


                // Step 4: Save Calificacion
                boolean exito = colegio.agregarCalificacionAEstudianteAsignatura(estudianteSeleccionado.getCodigo(), asignaturaSeleccionada.getCodigo(), nuevaCalificacion);
                
                if (exito) {
                    JOptionPane.showMessageDialog(MenuDocente.this, "Calificación agregada exitosamente.");
                    // Optionally, refresh some view or clear displayArea
                    displayArea.setText("Calificación para " + estudianteSeleccionado.getNombre() + 
                                        " en " + asignaturaSeleccionada.getNombre() + " ha sido registrada.");
                } else {
                    JOptionPane.showMessageDialog(MenuDocente.this, "Error al agregar la calificación.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                identificarProfesor();
            }
        });
    }

    private void identificarProfesor() {
        String codigoStr = JOptionPane.showInputDialog(this, "Ingrese su código de docente:", "Identificación Docente", JOptionPane.QUESTION_MESSAGE);
        if (codigoStr == null || codigoStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Identificación cancelada. La ventana se cerrará.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        try {
            int codigoProfesor = Integer.parseInt(codigoStr);
            this.profesorActual = colegio.buscarProfesor(codigoProfesor);

            if (this.profesorActual == null) {
                JOptionPane.showMessageDialog(this, "Docente con código " + codigoProfesor + " no encontrado.", "Error de Identificación", JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
            }

            infoLabel.setText("Bienvenido/a Docente: " + profesorActual.getNombre());
            if (profesorActual.getCurso() != null && profesorActual.getCurso().getCodigo() != 0) {
                Curso cursoAsignado = colegio.buscarCursoPorCodigo(profesorActual.getCurso().getCodigo());
                if (cursoAsignado != null) {
                    profesorActual.setCurso(cursoAsignado); 
                    infoLabel.setText(infoLabel.getText() + " - Curso Asignado: " + cursoAsignado.getGrado() + "-" + cursoAsignado.getGrupo() + " (ID: " + cursoAsignado.getCodigo() +")");
                    verCursoButton.setEnabled(true);
                    listarEstudiantesButton.setEnabled(true);
                    calificarEstudianteButton.setEnabled(true); 
                } else {
                    infoLabel.setText(infoLabel.getText() + " - Curso ID " + profesorActual.getCurso().getCodigo() + " no encontrado en el sistema.");
                    JOptionPane.showMessageDialog(this, "Advertencia: El curso asignado (ID: " + profesorActual.getCurso().getCodigo() + ") no fue encontrado. Algunas funciones pueden estar limitadas.", "Datos Inconsistentes", JOptionPane.WARNING_MESSAGE);
                    verCursoButton.setEnabled(false);
                    listarEstudiantesButton.setEnabled(false);
                    calificarEstudianteButton.setEnabled(false);
                }
            } else {
                infoLabel.setText(infoLabel.getText() + " - No tiene un curso principal asignado.");
                // If no course, "Ver Mi Curso" should also be disabled or show a message.
                // Disabling is consistent with other buttons.
                verCursoButton.setEnabled(false); 
                listarEstudiantesButton.setEnabled(false);
                calificarEstudianteButton.setEnabled(false);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El código del docente debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            // Consider not re-prompting immediately or providing a cancel option, but for now, re-prompt is the existing behavior.
            identificarProfesor(); 
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
