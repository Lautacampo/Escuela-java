/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dao.impl;

import com.mycompany.colegiotomasbaras.dao.CursadoModuloDAO;
import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Maximo Gallardo
 */
@Repository
public class CursadoModuloDAOImpl
        implements CursadoModuloDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Buscar la cursada con bloqueo de escritura
    // para registrar la evaluacion.

    @Override
    public Optional<CursadoModulo> buscarParaCalificar(
            Long id) {

        CursadoModulo cursada = entityManager.find(
                CursadoModulo.class,
                id,
                LockModeType.PESSIMISTIC_WRITE
        );

        return Optional.ofNullable(cursada);
    }

    // Recuperar el historial del alumno.

    @Override
    public List<CursadoModulo> buscarPorAlumno(
            Long idAlumno) {

        return entityManager.createQuery(
                "SELECT c FROM CursadoModulo c "
                + "JOIN FETCH c.modulo "
                + "WHERE c.alumno.id = :idAlumno",
                CursadoModulo.class
        )
        .setParameter("idAlumno", idAlumno)
        .getResultList();
    }

    // Evitar una matricula duplicada
    // en el mismo aula y modulo.

    @Override
    public boolean existeMatricula(
            Long idAlumno,
            Long idCurso,
            Long idModulo) {

        Long cantidad = entityManager.createQuery(
                "SELECT COUNT(c) FROM CursadoModulo c "
                + "WHERE c.alumno.id = :idAlumno "
                + "AND c.curso.id = :idCurso "
                + "AND c.modulo.id = :idModulo",
                Long.class
        )
        .setParameter("idAlumno", idAlumno)
        .setParameter("idCurso", idCurso)
        .setParameter("idModulo", idModulo)
        .getSingleResult();

        return cantidad > 0;
    }

    // Guardar una cursada nueva.

    @Override
    public CursadoModulo guardarNueva(
            CursadoModulo cursada) {

        entityManager.persist(cursada);
        return cursada;
    }

    // Actualizar una cursada existente.

    @Override
    public CursadoModulo actualizar(
            CursadoModulo cursada) {

        return entityManager.merge(cursada);
    }
}