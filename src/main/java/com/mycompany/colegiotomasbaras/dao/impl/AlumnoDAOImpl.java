/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dao.impl;
import com.mycompany.colegiotomasbaras.dao.AlumnoDAO;
import com.mycompany.colegiotomasbaras.modelo.personas.Alumno;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
/**
 *
 * @author Maximo Gallardo
 */
@Repository
public class AlumnoDAOImpl implements AlumnoDAO {


    @PersistenceContext
    private EntityManager entityManager;

    // Registrar un nuevo alumno

    @Override
    public Alumno guardar(Alumno alumno) {

        entityManager.persist(alumno);

        return alumno;
    }

    // Buscar alumno por identificador

    @Override
    public Optional<Alumno> buscarPorId(Long id) {

        Alumno alumno = entityManager.find(
                Alumno.class, id
        );

        return Optional.ofNullable(alumno);
    }

    // Consultar todos los alumnos

    @Override
    public List<Alumno> listarTodos() {

        return entityManager.createQuery(
                "SELECT a FROM Alumno a "
                + "ORDER BY a.apellido, a.nombre",
                Alumno.class
        ).getResultList();
    }

    // Comprobar si existe el DNI en Persona

    @Override
    public boolean existePorDni(String dni) {

        Long cantidad = entityManager.createQuery(
                "SELECT COUNT(p) FROM Persona p "
                + "WHERE p.dni = :dni",
                Long.class
        )
        .setParameter("dni", dni)
        .getSingleResult();

        return cantidad > 0;
    }
    
    @Override
    public List<Alumno> obtenerTodos() {

        String jpql = "SELECT DISTINCT a FROM Alumno a " +
                      "LEFT JOIN FETCH a.cursadas cm " +
                      "LEFT JOIN FETCH cm.curso c " +
                      "LEFT JOIN FETCH cm.modulo m";
        
        return entityManager.createQuery(jpql, Alumno.class).getResultList();
    }
    
}