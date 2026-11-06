package com.mycompany.colegiotomasbaras.dto;

import java.util.List;
import java.util.Map;

public final class CalificacionesDTO {

    private CalificacionesDTO() {}

    public record AnioModulos(int anio, List<ModuloResumen> modulos) {}

    public record ModuloResumen(Long moduloId, String nombre,
                                List<String> materias, List<String> divisiones) {}

    public record MateriaGrilla(Long id, String nombre) {}

    public record AlumnoGrilla(Long cursadaId, String apellido, String nombre,
                               Map<Long, String> calificaciones) {}

    public record Grilla(List<MateriaGrilla> materias, List<AlumnoGrilla> alumnos) {}

    public record SetEstado(Long cursadaModuloId, Long materiaId, String estado) {}
}