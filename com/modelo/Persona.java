package com.modelo;

import java.util.ArrayList;

public class Persona {
    private String nombre;
    private int edad;
    private int cedula;
    private int codigo;
    private String tipo;

    public Persona() {
    }

    public Persona(String nombre, int edad, int cedula, int codigo, String tipo) {
        this.nombre = nombre;
        this.edad = edad;
        this.cedula = cedula;
        this.codigo = codigo;
        this.tipo = tipo;
    }
    

    @Override
    public String toString() {
        // Se usan los nuevos nombres de los getters
        return "nombre: " + obtenerNombre() 
                + ", edad: " + obtenerEdad() 
                + ", cedula: " + obtenerCedula() 
                + ", codigo: " + obtenerCodigo() 
                + ", tipo: " + obtenerTipo(); // Corregido "codigo: " a "tipo: "
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
     * @return la edad
     */
    public int obtenerEdad() { // Renombrado de getEdad
        return edad;
    }

    /**
     * @param edad la edad a establecer
     */
    public void establecerEdad(int edad) { // Renombrado de setEdad
        this.edad = edad;
    }

    /**
     * @return la cedula
     */
    public int obtenerCedula() { // Renombrado de getCedula
        return cedula;
    }

    /**
     * @param cedula la cedula a establecer
     */
    public void establecerCedula(int cedula) { // Renombrado de setCedula
        this.cedula = cedula;
    }

    /**
     * @return el codigo
     */
    public int obtenerCodigo() { // Renombrado de getCodigo
        return codigo;
    }

    /**
     * @param codigo el codigo a establecer
     */
    public void establecerCodigo(int codigo) { // Renombrado de setCodigo
        this.codigo = codigo;
    }

    /**
     * @return el tipo
     */
    public String obtenerTipo() { // Renombrado de getTipo
        return tipo;
    }

    /**
     * @param tipo el tipo a establecer
     */
    public void establecerTipo(String tipo) { // Renombrado de setTipo
        this.tipo = tipo;
    }
    
    
}

