/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dao.impl;
import com.mycompany.colegiotomasbaras.dao.CursoDAO;
import com.mycompany.colegiotomasbaras.modelo.academico.Curso;

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
public class CursoDAOImpl implements CursoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Curso> buscarPorId(Long id) {
        Curso aula = entityManager.find(Curso.class, id);
        return Optional.ofNullable(aula);
    }
    
    @Override
    public List<Curso> obtenerTodosconModulos() {
        String jpql = "SELECT DISTINCT c FROM Curso c LEFT JOIN FETCH c.modulos ORDER BY c.anioCurso, c.division";
        return entityManager.createQuery(jpql, Curso.class).getResultList();
    }
}
