package com.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Calificacion {
    private String nombre; // Servirá como 'descripcion' para propósitos del DAO
    private float nota;    // Servirá como 'valor' para propósitos del DAO
    private int periodo;
    private LocalDate fecha;

    private static final DateTimeFormatter DAO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE; // ej., "2023-10-26"

    // Constructor por defecto
    public Calificacion() {
        this.nombre = "";
        this.nota = 0.0f;
        this.periodo = 1; // Periodo por defecto
        this.fecha = LocalDate.now(); // Fecha por defecto
    }

    // Constructor para campos relevantes al DAO (nombre/descripcion, nota/valor)
    public Calificacion(String nombre, float nota) {
        this.nombre = nombre;
        this.nota = nota;
        this.periodo = 1; // Valor por defecto
        this.fecha = LocalDate.now(); // Valor por defecto
    }
    
    public Calificacion(String nombre, float nota, int periodo, LocalDate fecha) {
        this.nombre = nombre;
        this.nota = nota;
        this.periodo = periodo;
        this.fecha = fecha;
    }

    // toString original - mantenido para uso general
    @Override
    public String toString() {
        // Se usan los nuevos nombres de los getters
        return obtenerNombre() + "{" + "nota:" + obtenerNota() + ", periodo:" + obtenerPeriodo() + ", fecha:" + (obtenerFecha() != null ? obtenerFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A") + '}';
    }

    // Método para generar cadena para el DAO: "nombre:nota:periodo:fecha_iso"
    public String toDaoString() {
        String fechaStr = (this.fecha != null) ? this.fecha.format(DAO_DATE_FORMATTER) : "";
        return String.join(":", this.nombre, Float.toString(this.nota), Integer.toString(this.periodo), fechaStr);
    }

    // Método estático para parsear desde cadena del DAO: "nombre:nota:periodo:fecha_iso"
    public static Calificacion fromDaoString(String cadena) { // Parámetro 's' a 'cadena'
        if (cadena == null || cadena.isEmpty()) {
            return null;
        }
        String[] partes = cadena.split(":", 4); // 'parts' a 'partes', limita a 4 partes
        if (partes.length < 2) { // Debe tener al menos nombre y nota
            System.err.println("Calificacion.fromDaoString: Formato inválido - " + cadena);
            return null; 
        }
        try {
            String nombre = partes[0];
            float nota = Float.parseFloat(partes[1]);
            int periodo = (partes.length > 2 && !partes[2].isEmpty()) ? Integer.parseInt(partes[2]) : 1; // Valor por defecto si falta
            LocalDate fecha = null;
            if (partes.length > 3 && !partes[3].isEmpty()) {
                try {
                    fecha = LocalDate.parse(partes[3], DAO_DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    System.err.println("Calificacion.fromDaoString: Formato de fecha inválido para " + partes[3] + " en " + cadena + ". Usando fecha actual.");
                    fecha = LocalDate.now(); // Valor por defecto en error de parseo
                }
            } else {
                fecha = LocalDate.now(); // Valor por defecto si falta
            }
            return new Calificacion(nombre, nota, periodo, fecha);
        } catch (NumberFormatException e) {
            System.err.println("Calificacion.fromDaoString: Error parseando números en " + cadena + " - " + e.getMessage());
            return null;
        }
    }


    /**
     * @return el nombre
     */
    public String obtenerNombre() { // Renombrado de getNombre
        return nombre;
    }

    /**
     * @param nombre el nombre a establecer
     */
    public void establecerNombre(String nombre) { // Renombrado de setNombre
        this.nombre = nombre;
    }

    /**
     * @return la nota
     */
    public float obtenerNota() { // Renombrado de getNota
        return nota;
    }

    /**
     * @param nota la nota a establecer
     */
    public void establecerNota(float nota) { // Renombrado de setNota
        this.nota = nota;
    }

    /**
     * @return el periodo
     */
    public int obtenerPeriodo() { // Renombrado de getPeriodo
        return periodo;
    }

    /**
     * @param periodo el periodo a establecer
     */
    public void establecerPeriodo(int periodo) { // Renombrado de setPeriodo
        this.periodo = periodo;
    }

    /**
     * @return la fecha
     */
    public LocalDate obtenerFecha() { // Renombrado de getFecha
        return fecha;
    }

    /**
     * @param fecha la fecha a establecer
     */
    public void establecerFecha(LocalDate fecha) { // Renombrado de setFecha
        this.fecha = fecha;
    }

    
    
    
    
}
