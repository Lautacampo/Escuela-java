package com.mycompany.colegiotomasbaras.dao.impl;

import com.mycompany.colegiotomasbaras.dao.MateriaDAO;
import com.mycompany.colegiotomasbaras.modelo.academico.Materia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MateriaDAOImpl implements MateriaDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Materia> buscarPorId(Long id) {
        Materia materia = em.find(Materia.class, id);
        return Optional.ofNullable(materia);
    }

    @Override
    public List<Materia> listarTodas() {
        return em.createQuery("SELECT m FROM Materia m", Materia.class).getResultList();
    }
}