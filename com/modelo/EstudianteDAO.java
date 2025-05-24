package com.modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EstudianteDAO implements DAO<Estudiante> { 

    private static final String NOMBRE_ARCHIVO = "estudiantes.txt"; // Renombrado
    private static final String DELIMITADOR_ASIGNATURA = ";"; // Renombrado
    private static final String DELIMITADOR_CSV = ","; // Renombrado

    // Método auxiliar para convertir un Estudiante a una cadena CSV
    private String estudianteACadenaCsv(Estudiante estudiante) { // Renombrado
        if (estudiante == null) {
            return "";
        }
        // codigo,nombre,edad,cedula,tipo
        String datosPersona = String.join(DELIMITADOR_CSV, // Uso de constante renombrada
                String.valueOf(estudiante.obtenerCodigo()), // Uso de método traducido
                estudiante.obtenerNombre(), // Uso de método traducido
                String.valueOf(estudiante.obtenerEdad()), // Uso de método traducido
                String.valueOf(estudiante.obtenerCedula()), // Uso de método traducido
                estudiante.obtenerTipo()); // Uso de método traducido

        // curso (grado-grupo)
        String datosCurso = "";
        if (estudiante.obtenerCurso() != null) { // Uso de método traducido
            datosCurso = estudiante.obtenerCurso().obtenerGrado() + "-" + estudiante.obtenerCurso().obtenerGrupo(); // Uso de métodos traducidos
        }

        // asignaturas (nombre1;nombre2;nombre3)
        String datosAsignaturas = "";
        if (estudiante.obtenerAsignaturas() != null && !estudiante.obtenerAsignaturas().isEmpty()) { // Uso de método traducido
            datosAsignaturas = estudiante.obtenerAsignaturas().stream()
                    .map(Asignatura::obtenerNombre) // Se asume Asignatura.obtenerNombre()
                    .collect(Collectors.joining(DELIMITADOR_ASIGNATURA)); // Uso de constante renombrada
        }

        return String.join(DELIMITADOR_CSV, datosPersona, datosCurso, datosAsignaturas); // Uso de constante renombrada
    }

    // Método auxiliar para convertir una cadena CSV a un Estudiante
    private Estudiante cadenaCsvAEstudiante(String lineaCsv) { // Renombrado y parámetro traducido
        if (lineaCsv == null || lineaCsv.trim().isEmpty()) {
            return null;
        }
        String[] campos = lineaCsv.split(DELIMITADOR_CSV, -1); // 'fields' a 'campos', uso de constante renombrada

        // Esperado: codigo,nombre,edad,cedula,tipo,cursoCadena,asignaturasCadena
        if (campos.length < 7) { // Validación básica
            System.err.println("EstudianteDAO: Formato de línea CSV inválido: " + lineaCsv); // Mensaje traducido
            return null;
        }

        try {
            int codigo = Integer.parseInt(campos[0]);
            String nombre = campos[1];
            int edad = Integer.parseInt(campos[2]);
            int cedula = Integer.parseInt(campos[3]);
            String tipo = campos[4];
            String cursoCadena = campos[5]; // 'cursoStr' a 'cursoCadena'
            String asignaturasCadena = campos[6]; // 'asignaturasStr' a 'asignaturasCadena'

            // Crear Estudiante con campos de Persona
            // El constructor Estudiante(ArrayList<Asignatura> asignaturas, String nombre, int edad, int cedula, int codigo, String tipo)
            // inicializa asignaturas y curso internamente. Se establecerán después.
            Estudiante estudiante = new Estudiante(new ArrayList<>(), nombre, edad, cedula, codigo, tipo);
            
            // Establecer Curso (marcador de posición)
            Curso curso = new Curso(); // Usa constructor por defecto
            if (!cursoCadena.isEmpty() && cursoCadena.contains("-")) {
                String[] partesCurso = cursoCadena.split("-"); // 'cursoParts' a 'partesCurso'
                if (partesCurso.length == 2) {
                    try {
                        curso.establecerGrado(Integer.parseInt(partesCurso[0])); // Uso de método traducido
                        curso.establecerGrupo(Integer.parseInt(partesCurso[1])); // Uso de método traducido
                    } catch (NumberFormatException e) {
                        System.err.println("EstudianteDAO: Formato de curso inválido en CSV: " + cursoCadena); // Mensaje traducido
                        // Mantener objeto Curso por defecto
                    }
                }
            }
            estudiante.establecerCurso(curso); // Uso de método traducido

            // Establecer Asignaturas (marcadores de posición)
            ArrayList<Asignatura> listaAsignaturas = new ArrayList<>(); // 'asignaturasList' a 'listaAsignaturas'
            if (!asignaturasCadena.isEmpty()) {
                String[] nombresAsignatura = asignaturasCadena.split(DELIMITADOR_ASIGNATURA); // 'asignaturaNombres' a 'nombresAsignatura', uso de constante
                for (String nombreAsignatura : nombresAsignatura) {
                    Asignatura asignatura = new Asignatura(); // Usa constructor por defecto
                    asignatura.establecerNombre(nombreAsignatura); // Uso de método traducido
                    listaAsignaturas.add(asignatura);
                }
            }
            estudiante.establecerAsignaturas(listaAsignaturas); // Uso de método traducido

            return estudiante;

        } catch (NumberFormatException e) {
            System.err.println("EstudianteDAO: Error parseando número en línea CSV: " + lineaCsv + " - " + e.getMessage()); // Mensaje traducido
            return null;
        } catch (Exception e) { // Capturar cualquier otro error inesperado durante el parseo
            System.err.println("EstudianteDAO: Error inesperado parseando línea CSV: " + lineaCsv + " - " + e.getMessage()); // Mensaje traducido
            return null;
        }
    }

    @Override
    public void crear(Estudiante estudiante) {
        if (estudiante == null) {
            System.err.println("EstudianteDAO: No se puede crear un estudiante nulo."); // Mensaje traducido
            return;
        }
        String lineaCsv = estudianteACadenaCsv(estudiante); // 'csvLine' a 'lineaCsv'
        try {
            Files.write(Paths.get(NOMBRE_ARCHIVO), (lineaCsv + System.lineSeparator()).getBytes(), // Uso de constante renombrada
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("EstudianteDAO: Error al escribir en el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
        }
    }

    @Override
    public Estudiante leerPorId(int id) { // Renombrado de leer
        List<Estudiante> estudiantes = listarTodos();
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.obtenerCodigo() == id) { // Uso de método traducido
                return estudiante;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Estudiante estudianteActualizado) {
        if (estudianteActualizado == null) {
            System.err.println("EstudianteDAO: No se puede actualizar un estudiante nulo."); // Mensaje traducido
            return;
        }
        List<Estudiante> estudiantes = listarTodos();
        boolean encontrado = false; // 'found' a 'encontrado'
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).obtenerCodigo() == estudianteActualizado.obtenerCodigo()) { // Uso de método traducido
                estudiantes.set(i, estudianteActualizado);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            reescribirArchivo(estudiantes);
        } else {
            System.err.println("EstudianteDAO: Estudiante con código " + estudianteActualizado.obtenerCodigo() + " no encontrado para actualizar."); // Mensaje traducido
        }
    }

    @Override
    public void eliminarPorId(int id) { // Renombrado de eliminar
        List<Estudiante> estudiantes = listarTodos();
        boolean eliminado = estudiantes.removeIf(estudiante -> estudiante.obtenerCodigo() == id); // 'removed' a 'eliminado', uso de método traducido

        if (eliminado) {
            reescribirArchivo(estudiantes);
        } else {
            System.err.println("EstudianteDAO: Estudiante con código " + id + " no encontrado para eliminar."); // Mensaje traducido
        }
    }

    @Override
    public List<Estudiante> listarTodos() {
        List<Estudiante> estudiantes = new ArrayList<>();
        if (!Files.exists(Paths.get(NOMBRE_ARCHIVO))) { // Uso de constante renombrada
            return estudiantes; // Devuelve lista vacía si el archivo no existe
        }
        try {
            List<String> lineas = Files.readAllLines(Paths.get(NOMBRE_ARCHIVO)); // 'lines' a 'lineas', uso de constante
            for (String linea : lineas) { // 'line' a 'linea'
                if (linea.trim().isEmpty()) continue; // Saltar líneas vacías
                Estudiante estudiante = cadenaCsvAEstudiante(linea); // Uso de método traducido
                if (estudiante != null) {
                    estudiantes.add(estudiante);
                }
            }
        } catch (IOException e) {
            System.err.println("EstudianteDAO: Error al leer el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
            // Devolver lista potencialmente parcialmente llena o vacía en caso de error
        }
        return estudiantes;
    }

    // Método auxiliar para reescribir todo el archivo
    private void reescribirArchivo(List<Estudiante> estudiantes) {
        List<String> lineas = new ArrayList<>(); // 'lines' a 'lineas'
        for (Estudiante est : estudiantes) {
            lineas.add(estudianteACadenaCsv(est)); // Uso de método traducido
        }
        try {
            Files.write(Paths.get(NOMBRE_ARCHIVO), lineas, // Uso de constante renombrada
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("EstudianteDAO: Error al reescribir el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
        }
    }
}
