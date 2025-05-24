/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vista;

import com.modelo.Colegio;
import com.modelo.Profesor;
import com.modelo.Curso;
import com.modelo.Estudiante; 
import com.modelo.Asignatura; 
import com.modelo.Calificacion; 

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate; 
import java.util.List; 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Interfaz gráfica para el menú del docente.
 * @author river // Mantener autor original si se desea
 */
public class MenuDocente extends javax.swing.JFrame {

    private Colegio colegio;
    private Profesor profesorActual;

    // Componentes UI traducidos
    private javax.swing.JLabel etiquetaInfo;
    private javax.swing.JButton botonVerCurso;
    private javax.swing.JButton botonListarEstudiantes;
    private javax.swing.JButton botonCalificarEstudiante;
    private javax.swing.JTextArea areaDeTexto;
    private javax.swing.JScrollPane panelDesplazamiento;

    /**
     * Crea el nuevo formulario MenuDocente.
     */
    public MenuDocente() {
        this.colegio = new Colegio("Mi Colegio"); // Nombre del colegio, puede ser configurable

        // Inicialización Manual de UI
        setTitle("Portal del Docente");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
        
        etiquetaInfo = new JLabel("Identifíquese por favor...");
        botonVerCurso = new JButton("Ver Mi Curso");
        botonListarEstudiantes = new JButton("Listar Estudiantes de Mi Curso");
        botonCalificarEstudiante = new JButton("Calificar Estudiante");

        areaDeTexto = new JTextArea(15, 50); 
        areaDeTexto.setEditable(false);
        areaDeTexto.setLineWrap(true);
        areaDeTexto.setWrapStyleWord(true);
        panelDesplazamiento = new JScrollPane(areaDeTexto);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 'topPanel' a 'panelSuperior'
        panelSuperior.add(etiquetaInfo);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 'buttonPanel' a 'panelBotones'
        panelBotones.add(botonVerCurso);
        panelBotones.add(botonListarEstudiantes);
        panelBotones.add(botonCalificarEstudiante);

        getContentPane().setLayout(new BorderLayout(5, 5)); 
        getContentPane().add(panelSuperior, BorderLayout.NORTH);
        getContentPane().add(panelBotones, BorderLayout.CENTER);
        getContentPane().add(panelDesplazamiento, BorderLayout.SOUTH);
        
        pack(); 
        setLocationRelativeTo(null); 
        setMinimumSize(new Dimension(600, 400)); 

        // Estado inicial de los botones
        botonVerCurso.setEnabled(false);
        botonListarEstudiantes.setEnabled(false);
        botonCalificarEstudiante.setEnabled(false);

        // ActionListeners
        botonVerCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (profesorActual != null && profesorActual.obtenerCurso() != null && profesorActual.obtenerCurso().obtenerCodigo() != 0) {
                    String infoCurso = colegio.infoCurso(profesorActual.obtenerCurso().obtenerCodigo()); // Uso de métodos traducidos
                    areaDeTexto.setText(infoCurso);
                } else {
                    areaDeTexto.setText("No hay información del curso para mostrar o no tiene un curso asignado.");
                }
            }
        });

        botonListarEstudiantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (profesorActual != null && profesorActual.obtenerCurso() != null && profesorActual.obtenerCurso().obtenerCodigo() != 0) {
                    Curso cursoDelProfesor = profesorActual.obtenerCurso(); 
                    
                    StringBuilder constructorCadena = new StringBuilder("Estudiantes en su curso (" + cursoDelProfesor.obtenerGrado() + "-" + cursoDelProfesor.obtenerGrupo() + "):\n\n"); // 'sb' a 'constructorCadena'
                    List<Estudiante> estudiantes = colegio.obtenerEstudiantesPorCurso(cursoDelProfesor.obtenerCodigo()); // Uso de método traducido
                    
                    if (estudiantes.isEmpty()) {
                        constructorCadena.append("No hay estudiantes asignados a este curso.");
                    } else {
                        for (Estudiante estudiante : estudiantes) { // 'est' a 'estudiante'
                            constructorCadena.append(estudiante.toString()).append("\n--------------------\n");
                        }
                    }
                    areaDeTexto.setText(constructorCadena.toString());
                } else {
                    areaDeTexto.setText("No tiene un curso asignado para listar estudiantes.");
                }
            }
        });

        botonCalificarEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (profesorActual == null || profesorActual.obtenerCurso() == null || profesorActual.obtenerCurso().obtenerCodigo() == 0) {
                    JOptionPane.showMessageDialog(MenuDocente.this, "No tiene un curso asignado para calificar estudiantes.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Paso 1: Seleccionar Estudiante
                List<Estudiante> estudiantes = colegio.obtenerEstudiantesPorCurso(profesorActual.obtenerCurso().obtenerCodigo());
                if (estudiantes.isEmpty()) {
                    JOptionPane.showMessageDialog(MenuDocente.this, "No hay estudiantes en su curso para calificar.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Estudiante[] estudiantesArray = estudiantes.toArray(new Estudiante[0]);
                Estudiante estudianteSeleccionado = (Estudiante) JOptionPane.showInputDialog(MenuDocente.this, "Seleccione Estudiante:", "Calificar Estudiante - Paso 1", JOptionPane.QUESTION_MESSAGE, null, estudiantesArray, estudiantesArray[0]);
                if (estudianteSeleccionado == null) return; // Usuario canceló

                // Paso 2: Seleccionar Asignatura
                List<Asignatura> asignaturasDelEstudiante = colegio.obtenerAsignaturasPorEstudiante(estudianteSeleccionado.obtenerCodigo()); // Uso de método traducido
                if (asignaturasDelEstudiante.isEmpty()) {
                    JOptionPane.showMessageDialog(MenuDocente.this, "El estudiante seleccionado no tiene asignaturas.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                Asignatura[] asignaturasArray = asignaturasDelEstudiante.toArray(new Asignatura[0]);
                Asignatura asignaturaSeleccionada = (Asignatura) JOptionPane.showInputDialog(MenuDocente.this, "Seleccione Asignatura:", "Calificar Estudiante - Paso 2", JOptionPane.QUESTION_MESSAGE, null, asignaturasArray, asignaturasArray[0]);
                if (asignaturaSeleccionada == null) return; // Usuario canceló

                // Paso 3: Ingresar Detalles de Calificación
                String nombreCalificacion = JOptionPane.showInputDialog(MenuDocente.this, "Ingrese nombre/descripción de la calificación (ej: Examen Parcial 1):");
                if (nombreCalificacion == null || nombreCalificacion.trim().isEmpty()) return;

                String valorCadena = JOptionPane.showInputDialog(MenuDocente.this, "Ingrese la nota (valor numérico):"); // 'valorStr' a 'valorCadena'
                if (valorCadena == null || valorCadena.trim().isEmpty()) return;
                
                float valorNota;
                try {
                    valorNota = Float.parseFloat(valorCadena);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(MenuDocente.this, "La nota debe ser un valor numérico.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Calificacion nuevaCalificacion = new Calificacion(nombreCalificacion, valorNota);
                
                // Paso 4: Guardar Calificación
                boolean exito = colegio.agregarCalificacionAEstudianteAsignatura(estudianteSeleccionado.obtenerCodigo(), asignaturaSeleccionada.obtenerCodigo(), nuevaCalificacion);
                
                if (exito) {
                    JOptionPane.showMessageDialog(MenuDocente.this, "Calificación agregada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    areaDeTexto.setText("Calificación para " + estudianteSeleccionado.obtenerNombre() + 
                                        " en " + asignaturaSeleccionada.obtenerNombre() + " ha sido registrada.");
                } else {
                    JOptionPane.showMessageDialog(MenuDocente.this, "Error al agregar la calificación.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Diferir identificación para asegurar que la UI sea visible para JOptionPane
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                identificarProfesor();
            }
        });
    }

    private void identificarProfesor() {
        String codigoCadena = JOptionPane.showInputDialog(this, "Ingrese su código de docente:", "Identificación del Docente", JOptionPane.QUESTION_MESSAGE); // 'codigoStr' a 'codigoCadena', título traducido
        if (codigoCadena == null || codigoCadena.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Identificación cancelada. La ventana se cerrará.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        try {
            int codigoProfesor = Integer.parseInt(codigoCadena);
            this.profesorActual = colegio.buscarProfesor(codigoProfesor);

            if (this.profesorActual == null) {
                JOptionPane.showMessageDialog(this, "Docente con código " + codigoProfesor + " no encontrado.", "Error de Identificación", JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
            }

            etiquetaInfo.setText("Bienvenido/a Docente: " + profesorActual.obtenerNombre()); // Uso de método traducido
            if (profesorActual.obtenerCurso() != null && profesorActual.obtenerCurso().obtenerCodigo() != 0) { // Uso de métodos traducidos
                Curso cursoAsignado = colegio.buscarCursoPorCodigo(profesorActual.obtenerCurso().obtenerCodigo());
                if (cursoAsignado != null) {
                    profesorActual.establecerCurso(cursoAsignado); // Uso de método traducido
                    etiquetaInfo.setText(etiquetaInfo.getText() + " - Curso Asignado: " + cursoAsignado.obtenerGrado() + "-" + cursoAsignado.obtenerGrupo() + " (ID: " + cursoAsignado.obtenerCodigo() +")"); // Uso de métodos traducidos
                    botonVerCurso.setEnabled(true);
                    botonListarEstudiantes.setEnabled(true);
                    botonCalificarEstudiante.setEnabled(true); 
                } else {
                    etiquetaInfo.setText(etiquetaInfo.getText() + " - Curso ID " + profesorActual.obtenerCurso().obtenerCodigo() + " no encontrado en el sistema.");
                    JOptionPane.showMessageDialog(this, "Advertencia: El curso asignado (ID: " + profesorActual.obtenerCurso().obtenerCodigo() + ") no fue encontrado. Algunas funciones pueden estar limitadas.", "Datos Inconsistentes", JOptionPane.WARNING_MESSAGE);
                    botonVerCurso.setEnabled(false);
                    botonListarEstudiantes.setEnabled(false);
                    botonCalificarEstudiante.setEnabled(false);
                }
            } else {
                etiquetaInfo.setText(etiquetaInfo.getText() + " - No tiene un curso principal asignado.");
                botonVerCurso.setEnabled(false); 
                botonListarEstudiantes.setEnabled(false);
                botonCalificarEstudiante.setEnabled(false);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El código del docente debe ser un número.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            identificarProfesor(); 
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
        // Este método podría ser generado por NetBeans si se abre posteriormente en el diseñador de GUI.
        // Para la configuración manual, puede dejarse vacío o eliminarse si no se llama.
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE); // Cambiado a EXIT_ON_CLOSE si es la ventana principal, sino DISPOSE_ON_CLOSE
        // NOTA: Mantendré DISPOSE_ON_CLOSE como estaba antes, ya que es un menú secundario.
        // setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 


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
