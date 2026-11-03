/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dto;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author Maximo Gallardo
 */
public record FichaAlumnoDTO(
        Long id,
        String nombre,
        String apellido,
        String dni,
        String cuil,
        LocalDate fechaNacimiento,
        Integer edad,
        String lugarNacimiento,
        String domicilio,
        String telefono,
        String email,
        String telefonoEmergencia,
        Integer cantidadHijos,
        Boolean trabaja,

        Long cursadaModuloId,
        String curso,
        String division,
        String moduloActual,
        Double faltas,
        String condicion,

        List<Cursada> trayectoria
) {

    public record Cursada(
            Long idCursada,
            Integer anioCurso,
            Integer cicloLectivo,
            String division,
            String modulo,
            String estadoAcademico,
            Double faltas,
            String condicion,
            Double nota,
            List<Evaluacion> evaluaciones,
            List<MateriaEstado> materias,
            List<AsistenciaRegistro> asistencias
    ) {}

    public record Evaluacion(
            Integer nroInstancia,
            LocalDate fecha,
            Double nota,
            String tipoInstancia
    ) {}

    public record MateriaEstado(
            Long idMateria,
            String nombre,
            String estado
    ) {}

    public record AsistenciaRegistro(
            LocalDate fecha,
            Double valorFalta
    ) {}
}
