/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dao.impl;
import com.mycompany.colegiotomasbaras.dao.EvaluacionModuloDAO;
import com.mycompany.colegiotomasbaras.modelo.evaluacion.EvaluacionModulo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Maximo Gallardo
 */
@Repository
public class EvaluacionModuloDAOImpl implements EvaluacionModuloDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Consultar las evaluaciones en orden.

    @Override
    public List<EvaluacionModulo> buscarPorCursada(
            Long idCursadaModulo) {

        return entityManager.createQuery(
                "SELECT e FROM EvaluacionModulo e "
                + "WHERE e.cursadaModulo.id = :id "
                + "ORDER BY e.nroInstancia ASC",
                EvaluacionModulo.class
        )
        .setParameter("id", idCursadaModulo)
        .getResultList();
    }

    // Registrar una evaluacion.

    @Override
    public EvaluacionModulo guardarNueva(
            EvaluacionModulo evaluacion) {

        entityManager.persist(evaluacion);
        return evaluacion;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<String> materiasNoAprobadas(Long idCursadaModulo) {
    return entityManager.createNativeQuery("""
        SELECT ma.nombre
          FROM cursada_modulo cm
          JOIN materia_modulo mm ON mm.modulo_id = cm.modulo_id
          JOIN materia ma ON ma.id = mm.materia_id
         WHERE cm.id = :id
           AND NOT EXISTS (
               SELECT 1 FROM calificacion c
                WHERE c.cursada_modulo_id = cm.id
                  AND c.materia_id = mm.materia_id
                  AND c.estado = 'Aprobado')
         ORDER BY ma.nombre
        """)
        .setParameter("id", idCursadaModulo)
        .getResultList();
}
    
}