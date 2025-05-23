/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vista;

// Added imports
import com.modelo.Colegio;
import com.modelo.Estudiante;
import com.modelo.Persona; // For instanceof check and casting
import com.modelo.Profesor; // Added import for Profesor
import java.util.ArrayList; // For new Estudiante constructor
import java.util.List; // For colegio.getPersonas()
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension; // For JScrollPane preferred size

/**
 *
 * @author river
 */
public class MenuAdmin extends javax.swing.JFrame {

    private Colegio colegio; // Added Colegio field

    /**
     * Creates new form MenuAdmin
     */
    public MenuAdmin() {
        initComponents();
        this.colegio = new Colegio("Mi Colegio"); // Initialize Colegio

        // Implement ActionListener for jButton1 (Agregar Docente)
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    String codigoStr = JOptionPane.showInputDialog(MenuAdmin.this, "Ingrese código del docente:");
                    if (codigoStr == null || codigoStr.trim().isEmpty()) return;

                    String nombre = JOptionPane.showInputDialog(MenuAdmin.this, "Ingrese nombre del docente:");
                    if (nombre == null || nombre.trim().isEmpty()) return;

                    String edadStr = JOptionPane.showInputDialog(MenuAdmin.this, "Ingrese edad del docente:");
                    if (edadStr == null || edadStr.trim().isEmpty()) return;

                    String cedulaStr = JOptionPane.showInputDialog(MenuAdmin.this, "Ingrese cédula del docente:");
                    if (cedulaStr == null || cedulaStr.trim().isEmpty()) return;

                    int codigo = Integer.parseInt(codigoStr);
                    int edad = Integer.parseInt(edadStr);
                    int cedula = Integer.parseInt(cedulaStr);
                    String tipo = "DOCENTE"; // Set automatically

                    // Pass null for Curso. It can be assigned later.
                    // Profesor constructor: public Profesor(Curso curso, String nombre, int edad, int cedula, int codigo, String tipo)
                    Profesor nuevoProfesor = new Profesor(null, nombre, edad, cedula, codigo, tipo); 
                    
                    colegio.agregarProfesor(nuevoProfesor);
                    JOptionPane.showMessageDialog(MenuAdmin.this, "Docente agregado exitosamente.");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(MenuAdmin.this, "Error: Código, edad y cédula deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MenuAdmin.this, "Error al agregar docente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace(); // For debugging
                }
            }
        });

        // Implement ActionListener for jButton2 (Agregar Estudiante)
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    String codigoStr = JOptionPane.showInputDialog(MenuAdmin.this, "Ingrese código del estudiante:");
                    if (codigoStr == null || codigoStr.trim().isEmpty()) return; 

                    String nombre = JOptionPane.showInputDialog(MenuAdmin.this, "Ingrese nombre del estudiante:");
                    if (nombre == null || nombre.trim().isEmpty()) return;

                    String edadStr = JOptionPane.showInputDialog(MenuAdmin.this, "Ingrese edad del estudiante:");
                    if (edadStr == null || edadStr.trim().isEmpty()) return;

                    String cedulaStr = JOptionPane.showInputDialog(MenuAdmin.this, "Ingrese cédula del estudiante:");
                    if (cedulaStr == null || cedulaStr.trim().isEmpty()) return;

                    int codigo = Integer.parseInt(codigoStr);
                    int edad = Integer.parseInt(edadStr);
                    int cedula = Integer.parseInt(cedulaStr);
                    String tipo = "ESTUDIANTE"; 

                    Estudiante nuevoEstudiante = new Estudiante(new ArrayList<>(), nombre, edad, cedula, codigo, tipo);
                    
                    colegio.agregarEstudiante(nuevoEstudiante);
                    JOptionPane.showMessageDialog(MenuAdmin.this, "Estudiante agregado exitosamente.");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(MenuAdmin.this, "Error: Código, edad y cédula deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MenuAdmin.this, "Error al agregar estudiante: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace(); 
                }
            }
        });

        // Implement ActionListener for jButton3 (Listar Docente)
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                List<Persona> personas = colegio.getPersonas();
                StringBuilder profesoresStr = new StringBuilder("Lista de Docentes:\n\n");
                boolean found = false;
                for (Persona p : personas) {
                    if (p instanceof Profesor) {
                        Profesor prof = (Profesor) p;
                        // Profesor.toString() includes basic info. If assigned, curso info might be placeholder from DAO.
                        profesoresStr.append(prof.toString()).append("\n--------------------\n");
                        found = true;
                    }
                }
                if (!found) {
                    profesoresStr.append("No hay docentes registrados.");
                }

                JTextArea textArea = new JTextArea(profesoresStr.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                scrollPane.setPreferredSize(new java.awt.Dimension(500, 300)); 
                JOptionPane.showMessageDialog(MenuAdmin.this, scrollPane, "Listado de Docentes", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Implement ActionListener for jButton4 (Listar Estudiante)
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                List<Persona> personas = colegio.getPersonas();
                StringBuilder estudiantesStr = new StringBuilder("Lista de Estudiantes:\n\n");
                boolean found = false;
                for (Persona p : personas) {
                    if (p instanceof Estudiante) {
                        Estudiante est = (Estudiante) p;
                        estudiantesStr.append(est.toString()).append("\n--------------------\n");
                        found = true;
                    }
                }
                if (!found) {
                    estudiantesStr.append("No hay estudiantes registrados.");
                }

                JTextArea textArea = new JTextArea(estudiantesStr.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                scrollPane.setPreferredSize(new Dimension(500, 300)); 
                JOptionPane.showMessageDialog(MenuAdmin.this, scrollPane, "Listado de Estudiantes", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        titulo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("MENU ADMIN");

        titulo.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        titulo.setText("GESTION COLEGIO");

        jButton1.setText("Agregar Docente");

        jButton2.setText("Agregar Estudiante");

        jButton3.setText("Listar Docente");

        jButton4.setText("Listar Estudiante");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(274, 274, 274))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(209, 209, 209)
                        .addComponent(titulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(209, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)))
                .addContainerGap(178, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel titulo;
    // End of variables declaration                   
}
