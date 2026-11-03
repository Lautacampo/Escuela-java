package com.mycompany.colegiotomasbaras.dao.impl;

import com.mycompany.colegiotomasbaras.dao.UsuarioDAO;
import com.mycompany.colegiotomasbaras.modelo.login.Rol;
import com.mycompany.colegiotomasbaras.modelo.login.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Lautaro
 */
@Repository
public class UsuarioDAOImpl implements UsuarioDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Usuario guardar(Usuario usuario) {
        entityManager.persist(usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorMail(String email) {
        try {
            Usuario usuario = entityManager.createQuery(
                    "SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.ofNullable(usuario);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean existePorMail(String email) {
        Long cantidad = entityManager.createQuery(
                "SELECT COUNT(u) FROM Usuario u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return cantidad > 0;
    }

    @Override
    public Optional<Rol> buscarRolPorNombre(String nombre) {
        try {
            Rol rol = entityManager.createQuery(
                    "SELECT r FROM Rol r WHERE LOWER(r.nombre) = LOWER(:nombre)", Rol.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
            return Optional.ofNullable(rol);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}