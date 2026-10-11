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
}