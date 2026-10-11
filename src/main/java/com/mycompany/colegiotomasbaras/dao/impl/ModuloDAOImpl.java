
package com.mycompany.colegiotomasbaras.dao.impl;

import com.mycompany.colegiotomasbaras.dao.ModuloDAO;
import com.mycompany.colegiotomasbaras.modelo.academico.Modulo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ModuloDAOImpl implements ModuloDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Modulo> buscarPorId(Long id) {

        Modulo modulo = entityManager.find(
                Modulo.class, id
        );

        return Optional.ofNullable(modulo);
    }
}
