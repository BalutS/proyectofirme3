/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vista;

// Imports necesarios
import com.modelo.Colegio;
import com.modelo.Estudiante;
import com.modelo.Persona; 
import com.modelo.Profesor; 
import java.util.ArrayList; 
import java.util.List; 
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension; 

/**
 * Interfaz gráfica para el menú del administrador.
 * @author river // Mantener autor original si se desea
 */
public class MenuAdministrador extends javax.swing.JFrame { 

    private Colegio colegio;

    /**
     * Crea un nuevo formulario MenuAdministrador.
     */
    public MenuAdministrador() { 
        initComponents();
        this.colegio = new Colegio("Mi Colegio"); // Nombre del colegio, puede ser configurable
        setTitle("Menú del Administrador"); // Establecer título de la ventana

        // Implementar ActionListener para botonAgregarDocente (antes jButton1)
        botonAgregarDocente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    String codigoCadena = JOptionPane.showInputDialog(MenuAdministrador.this, "Ingrese código del docente:");
                    if (codigoCadena == null || codigoCadena.trim().isEmpty()) return;

                    String nombre = JOptionPane.showInputDialog(MenuAdministrador.this, "Ingrese nombre del docente:");
                    if (nombre == null || nombre.trim().isEmpty()) return;

                    String edadCadena = JOptionPane.showInputDialog(MenuAdministrador.this, "Ingrese edad del docente:");
                    if (edadCadena == null || edadCadena.trim().isEmpty()) return;

                    String cedulaCadena = JOptionPane.showInputDialog(MenuAdministrador.this, "Ingrese cédula del docente:");
                    if (cedulaCadena == null || cedulaCadena.trim().isEmpty()) return;

                    int codigo = Integer.parseInt(codigoCadena);
                    int edad = Integer.parseInt(edadCadena);
                    int cedula = Integer.parseInt(cedulaCadena);
                    String tipo = "DOCENTE"; 

                    Profesor nuevoProfesor = new Profesor(null, nombre, edad, cedula, codigo, tipo); 
                    
                    colegio.agregarProfesor(nuevoProfesor); 
                    JOptionPane.showMessageDialog(MenuAdministrador.this, "Docente agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(MenuAdministrador.this, "Error: Código, edad y cédula deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MenuAdministrador.this, "Error al agregar docente: " + e.getMessage(), "Error Inesperado", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace(); 
                }
            }
        });

        // Implementar ActionListener para botonAgregarEstudiante (antes jButton2)
        botonAgregarEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    String codigoCadena = JOptionPane.showInputDialog(MenuAdministrador.this, "Ingrese código del estudiante:");
                    if (codigoCadena == null || codigoCadena.trim().isEmpty()) return; 

                    String nombre = JOptionPane.showInputDialog(MenuAdministrador.this, "Ingrese nombre del estudiante:");
                    if (nombre == null || nombre.trim().isEmpty()) return;

                    String edadCadena = JOptionPane.showInputDialog(MenuAdministrador.this, "Ingrese edad del estudiante:");
                    if (edadCadena == null || edadCadena.trim().isEmpty()) return;

                    String cedulaCadena = JOptionPane.showInputDialog(MenuAdministrador.this, "Ingrese cédula del estudiante:");
                    if (cedulaCadena == null || cedulaCadena.trim().isEmpty()) return;

                    int codigo = Integer.parseInt(codigoCadena);
                    int edad = Integer.parseInt(edadCadena);
                    int cedula = Integer.parseInt(cedulaCadena);
                    String tipo = "ESTUDIANTE"; 

                    Estudiante nuevoEstudiante = new Estudiante(new ArrayList<>(), nombre, edad, cedula, codigo, tipo);
                    
                    colegio.agregarEstudiante(nuevoEstudiante); 
                    JOptionPane.showMessageDialog(MenuAdministrador.this, "Estudiante agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(MenuAdministrador.this, "Error: Código, edad y cédula deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MenuAdministrador.this, "Error al agregar estudiante: " + e.getMessage(), "Error Inesperado", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace(); 
                }
            }
        });

        // Implementar ActionListener para botonListarDocente (antes jButton3)
        botonListarDocente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                List<Persona> personas = colegio.obtenerPersonas(); 
                StringBuilder cadenaProfesores = new StringBuilder("Lista de Docentes:\n\n"); // 'profesoresStr' a 'cadenaProfesores'
                boolean encontrado = false; // 'found' a 'encontrado'
                for (Persona persona : personas) { // 'p' a 'persona'
                    if (persona instanceof Profesor) {
                        Profesor profesor = (Profesor) persona; // 'prof' a 'profesor'
                        cadenaProfesores.append(profesor.toString()).append("\n--------------------\n");
                        encontrado = true;
                    }
                }
                if (!encontrado) {
                    cadenaProfesores.append("No hay docentes registrados.");
                }

                JTextArea areaTexto = new JTextArea(cadenaProfesores.toString()); // 'textArea' a 'areaTexto'
                areaTexto.setEditable(false);
                JScrollPane panelDesplazamiento = new JScrollPane(areaTexto); // 'scrollPane' a 'panelDesplazamiento'
                areaTexto.setLineWrap(true);
                areaTexto.setWrapStyleWord(true);
                panelDesplazamiento.setPreferredSize(new java.awt.Dimension(500, 300)); 
                JOptionPane.showMessageDialog(MenuAdministrador.this, panelDesplazamiento, "Listado de Docentes", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Implementar ActionListener para botonListarEstudiante (antes jButton4)
        botonListarEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                List<Persona> personas = colegio.obtenerPersonas(); 
                StringBuilder cadenaEstudiantes = new StringBuilder("Lista de Estudiantes:\n\n"); // 'estudiantesStr' a 'cadenaEstudiantes'
                boolean encontrado = false; // 'found' a 'encontrado'
                for (Persona persona : personas) { // 'p' a 'persona'
                    if (persona instanceof Estudiante) {
                        Estudiante estudiante = (Estudiante) persona; // 'est' a 'estudiante'
                        cadenaEstudiantes.append(estudiante.toString()).append("\n--------------------\n");
                        encontrado = true;
                    }
                }
                if (!encontrado) {
                    cadenaEstudiantes.append("No hay estudiantes registrados.");
                }

                JTextArea areaTexto = new JTextArea(cadenaEstudiantes.toString()); // 'textArea' a 'areaTexto'
                areaTexto.setEditable(false);
                JScrollPane panelDesplazamiento = new JScrollPane(areaTexto); // 'scrollPane' a 'panelDesplazamiento'
                areaTexto.setLineWrap(true);
                areaTexto.setWrapStyleWord(true);
                panelDesplazamiento.setPreferredSize(new Dimension(500, 300)); 
                JOptionPane.showMessageDialog(MenuAdministrador.this, panelDesplazamiento, "Listado de Estudiantes", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Este método es llamado desde el constructor para inicializar el formulario.
     * ADVERTENCIA: No modificar este código. El contenido de este método siempre
     * es regenerado por el Editor de Formularios.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        etiquetaMenuAdmin = new javax.swing.JLabel(); // Renombrado de jLabel1
        etiquetaTitulo = new javax.swing.JLabel();    // Renombrado de titulo
        botonAgregarDocente = new javax.swing.JButton(); // Renombrado de jButton1
        botonAgregarEstudiante = new javax.swing.JButton(); // Renombrado de jButton2
        botonListarDocente = new javax.swing.JButton();   // Renombrado de jButton3
        botonListarEstudiante = new javax.swing.JButton();  // Renombrado de jButton4

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Para no cerrar toda la app

        etiquetaMenuAdmin.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        etiquetaMenuAdmin.setText("MENÚ ADMINISTRADOR"); // Texto traducido

        etiquetaTitulo.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        etiquetaTitulo.setText("GESTIÓN COLEGIO"); // Ya en español

        botonAgregarDocente.setText("Agregar Docente"); // Ya en español

        botonAgregarEstudiante.setText("Agregar Estudiante"); // Ya en español

        botonListarDocente.setText("Listar Docente"); // Ya en español

        botonListarEstudiante.setText("Listar Estudiante"); // Ya en español

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(etiquetaMenuAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE) // Ajustado tamaño si es necesario
                .addGap(220, 220, 220)) // Ajustado espaciado si es necesario
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(209, 209, 209)
                        .addComponent(etiquetaTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(botonAgregarEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonAgregarDocente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(botonListarEstudiante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botonListarDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(209, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(etiquetaTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiquetaMenuAdmin)
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonAgregarDocente)
                        .addGap(18, 18, 18)
                        .addComponent(botonAgregarEstudiante))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonListarDocente)
                        .addGap(18, 18, 18)
                        .addComponent(botonListarEstudiante)))
                .addContainerGap(178, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null); // Centrar ventana
    }// </editor-fold>                        


    // Declaración de Variables - ¡NO modificar el bloque generado por el editor!                     
    private javax.swing.JButton botonAgregarDocente;   // Renombrado de jButton1
    private javax.swing.JButton botonAgregarEstudiante; // Renombrado de jButton2
    private javax.swing.JButton botonListarDocente;    // Renombrado de jButton3
    private javax.swing.JButton botonListarEstudiante;   // Renombrado de jButton4
    private javax.swing.JLabel etiquetaMenuAdmin;     // Renombrado de jLabel1
    private javax.swing.JLabel etiquetaTitulo;        // Renombrado de titulo
    // Fin de la declaración de Variables                   
}
