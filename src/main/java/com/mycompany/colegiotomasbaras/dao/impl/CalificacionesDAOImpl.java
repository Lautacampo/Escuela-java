package com.mycompany.colegiotomasbaras.dao.impl;
import com.mycompany.colegiotomasbaras.dao.CalificacionesDAO;
import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import com.mycompany.colegiotomasbaras.modelo.academico.Materia;
import com.mycompany.colegiotomasbaras.modelo.academico.Modulo;
import com.mycompany.colegiotomasbaras.modelo.evaluacion.Calificacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class CalificacionesDAOImpl implements CalificacionesDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Modulo> buscarModulo(Long id) {
        return Optional.ofNullable(em.find(Modulo.class, id));
    }

    @Override
    public Optional<CursadoModulo> buscarCursada(Long id) {
        return Optional.ofNullable(em.find(CursadoModulo.class, id));
    }

    @Override
    public Optional<Materia> buscarMateria(Long id) {
        return Optional.ofNullable(em.find(Materia.class, id));
    }

    @Override
    public List<CursadoModulo> buscarCursadasConAlumno(Long moduloId, String division) {
        return em.createQuery(
                "select c from CursadoModulo c join fetch c.alumno a "
                + "where c.modulo.id = :moduloId and c.curso.division = :division "
                + "order by a.apellido, a.nombre", CursadoModulo.class)
                .setParameter("moduloId", moduloId)
                .setParameter("division", division)
                .getResultList();
    }

    @Override
    public List<Calificacion> buscarPorCursadas(Collection<Long> cursadaIds) {
        return em.createQuery(
                "select c from Calificacion c join fetch c.materia "
                + "where c.cursadaModulo.id in :ids", Calificacion.class)
                .setParameter("ids", cursadaIds)
                .getResultList();
    }

    @Override
    public Optional<Calificacion> buscar(Long cursadaId, Long materiaId) {
        return em.createQuery(
                "select c from Calificacion c "
                + "where c.cursadaModulo.id = :cid and c.materia.id = :mid", Calificacion.class)
                .setParameter("cid", cursadaId)
                .setParameter("mid", materiaId)
                .getResultStream().findFirst();
    }

    @Override
    public Calificacion guardar(Calificacion calificacion) {
        if (calificacion.getId() == null) {
            em.persist(calificacion);
            return calificacion;
        }
        return em.merge(calificacion);
    }
}