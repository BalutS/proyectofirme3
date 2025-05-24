/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.vista;

/**
 *
 * @author river // El autor original se puede mantener o actualizar.
 */
public class VentanaPrincipal extends javax.swing.JFrame { 

    /**
     * Crea el nuevo formulario VentanaPrincipal.
     */
    public VentanaPrincipal() { 
        initComponents();
        // Establecer el título de la ventana principal en español
        setTitle("Gestión Colegio - Selección de Rol");
    }

    /**
     * Este método es llamado desde el constructor para inicializar el formulario.
     * ADVERTENCIA: No modificar este código. El contenido de este método siempre
     * es regenerado por el Editor de Formularios.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel(); // Renombrado de jPanel1
        etiquetaTitulo = new javax.swing.JLabel(); // Renombrado de titulo
        etiquetaRol = new javax.swing.JLabel();    // Renombrado de rolLabel
        selectorRol = new javax.swing.JComboBox<>(); // Renombrado de rolComboBox
        botonContinuar = new javax.swing.JButton(); // Renombrado de btnContinue

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        etiquetaTitulo.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        etiquetaTitulo.setText("GESTIÓN COLEGIO"); // Ya en español

        etiquetaRol.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        etiquetaRol.setText("SELECCIONA TU ROL:"); // Ya en español

        selectorRol.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        // Los valores "ADMIN", "DOCENTE", "ESTUDIANTE" son identificadores para el switch, se mantienen.
        // Si se quisieran mostrar en español en el ComboBox, se necesitaría un mapeo o cambiar el switch.
        selectorRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADMIN", "DOCENTE", "ESTUDIANTE" }));
        selectorRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionSelectorRol(evt); // Renombrado de rolComboBoxActionPerformed
            }
        });

        botonContinuar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        botonContinuar.setText("CONTINUAR"); // Ya en español
        botonContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBotonContinuar(evt); // Renombrado de btnContinueActionPerformed
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal); // Renombrado de jPanel1Layout
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(botonContinuar) // Uso de variable renombrada
                .addGap(292, 292, 292))
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(etiquetaRol) // Uso de variable renombrada
                        .addGap(28, 28, 28)
                        .addComponent(selectorRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)) // Uso de variable renombrada
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(etiquetaTitulo))) // Uso de variable renombrada
                .addContainerGap(145, Short.MAX_VALUE))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(etiquetaTitulo) // Uso de variable renombrada
                .addGap(100, 100, 100)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaRol) // Uso de variable renombrada
                    .addComponent(selectorRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)) // Uso de variable renombrada
                .addGap(32, 32, 32)
                .addComponent(botonContinuar) // Uso de variable renombrada
                .addContainerGap(115, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Renombrado de jPanel1
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Renombrado de jPanel1
        );

        pack();
    }// </editor-fold>                        

    private void accionBotonContinuar(java.awt.event.ActionEvent evt) { // Renombrado de btnContinueActionPerformed                                            
        String rol = (String) selectorRol.getSelectedItem(); // Uso de variable renombrada
        MenuAdministrador menuAdmin = new MenuAdministrador(); 
        MenuEstudiante menuEstudiante = new MenuEstudiante();
        MenuDocente menuDocente = new MenuDocente();
        switch (rol) {
            case "ADMIN": // Estos son identificadores, se mantienen
                menuAdmin.setVisible(true);
                menuAdmin.setLocationRelativeTo(null);
                break;
            case "DOCENTE":
                menuDocente.setVisible(true);
                menuDocente.setLocationRelativeTo(null);
                break;
            case "ESTUDIANTE":
                menuEstudiante.setVisible(true);
                menuEstudiante.setLocationRelativeTo(null);
                break;
            default:
                break;
        }
    }                                           

    private void accionSelectorRol(java.awt.event.ActionEvent evt) { // Renombrado de rolComboBoxActionPerformed                                            
         // Lógica si es necesaria cuando cambia la selección del ComboBox
    }                                           

    /**
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String args[]) {
        /* Establecer el look and feel Nimbus */
        //<editor-fold defaultstate="collapsed" desc=" Código para configurar el look and feel (opcional) ">
        /* Si Nimbus (introducido en Java SE 6) no está disponible, quedarse con el look and feel por defecto.
         * Para detalles vea http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex); 
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex); 
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex); 
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex); 
        }
        //</editor-fold>
        //</editor-fold> 

        /* Crear y mostrar el formulario */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                VentanaPrincipal ventana = new VentanaPrincipal(); // 'princ' a 'ventana'
                ventana.setVisible(true);
                ventana.setLocationRelativeTo(null);
            }
        });
    }

    // Declaración de Variables - ¡NO modificar el bloque generado por el editor!                     
    private javax.swing.JButton botonContinuar; // Renombrado de btnContinue
    private javax.swing.JPanel panelPrincipal;  // Renombrado de jPanel1
    private javax.swing.JComboBox<String> selectorRol; // Renombrado de rolComboBox
    private javax.swing.JLabel etiquetaRol;    // Renombrado de rolLabel
    private javax.swing.JLabel etiquetaTitulo; // Renombrado de titulo
    // Fin de la declaración de Variables                   
}

