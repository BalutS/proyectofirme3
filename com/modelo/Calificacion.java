package com.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Calificacion {
    private String nombre; // Will serve as 'descripcion' for DAO purposes
    private float nota;    // Will serve as 'valor' for DAO purposes
    private int periodo;
    private LocalDate fecha;

    private static final DateTimeFormatter DAO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE; // e.g., "2023-10-26"

    // Default constructor
    public Calificacion() {
        this.nombre = "";
        this.nota = 0.0f;
        this.periodo = 1; // Default periodo
        this.fecha = LocalDate.now(); // Default fecha
    }

    // Constructor for DAO relevant fields (nombre/descripcion, nota/valor)
    public Calificacion(String nombre, float nota) {
        this.nombre = nombre;
        this.nota = nota;
        this.periodo = 1; // Default
        this.fecha = LocalDate.now(); // Default
    }
    
    public Calificacion(String nombre, float nota, int periodo, LocalDate fecha) {
        this.nombre = nombre;
        this.nota = nota;
        this.periodo = periodo;
        this.fecha = fecha;
    }

    // Original toString - kept for general use
    @Override
    public String toString() {
        return getNombre() + "{" + "nota:" + getNota() + ", periodo:" + getPeriodo() + ", fecha:" + (getFecha() != null ? getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A") + '}';
    }

    // Method to generate string for DAO: "nombre:nota:periodo:fecha_iso"
    public String toDaoString() {
        String fechaStr = (this.fecha != null) ? this.fecha.format(DAO_DATE_FORMATTER) : "";
        return String.join(":", this.nombre, Float.toString(this.nota), Integer.toString(this.periodo), fechaStr);
    }

    // Static method to parse from DAO string: "nombre:nota:periodo:fecha_iso"
    public static Calificacion fromDaoString(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        String[] parts = s.split(":", 4); // Limit split to 4 parts
        if (parts.length < 2) { // Must have at least nombre and nota
            System.err.println("Calificacion.fromDaoString: Invalid format - " + s);
            return null; 
        }
        try {
            String nombre = parts[0];
            float nota = Float.parseFloat(parts[1]);
            int periodo = (parts.length > 2 && !parts[2].isEmpty()) ? Integer.parseInt(parts[2]) : 1; // Default if missing
            LocalDate fecha = null;
            if (parts.length > 3 && !parts[3].isEmpty()) {
                try {
                    fecha = LocalDate.parse(parts[3], DAO_DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    System.err.println("Calificacion.fromDaoString: Invalid date format for " + parts[3] + " in " + s + ". Using current date.");
                    fecha = LocalDate.now(); // Default on parse error
                }
            } else {
                fecha = LocalDate.now(); // Default if missing
            }
            return new Calificacion(nombre, nota, periodo, fecha);
        } catch (NumberFormatException e) {
            System.err.println("Calificacion.fromDaoString: Error parsing numbers in " + s + " - " + e.getMessage());
            return null;
        }
    }


    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the nota
     */
    public float getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(float nota) {
        this.nota = nota;
    }

    /**
     * @return the periodo
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    
    
    
    
}
