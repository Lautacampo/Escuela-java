package com.mycompany.colegiotomasbaras.dao;

import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import com.mycompany.colegiotomasbaras.modelo.academico.Materia;
import com.mycompany.colegiotomasbaras.modelo.academico.Modulo;
import com.mycompany.colegiotomasbaras.modelo.evaluacion.Calificacion;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CalificacionesDAO {
    Optional<Modulo> buscarModulo(Long id);
    Optional<CursadoModulo> buscarCursada(Long id);
    Optional<Materia> buscarMateria(Long id);
    List<CursadoModulo> buscarCursadasConAlumno(Long moduloId, String division);
    List<Calificacion> buscarPorCursadas(Collection<Long> cursadaIds);
    Optional<Calificacion> buscar(Long cursadaId, Long materiaId);
    Calificacion guardar(Calificacion calificacion);
}