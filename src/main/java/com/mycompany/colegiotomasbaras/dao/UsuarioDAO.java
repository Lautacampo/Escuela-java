package com.mycompany.colegiotomasbaras.dao;

import com.mycompany.colegiotomasbaras.modelo.login.Rol;
import com.mycompany.colegiotomasbaras.modelo.login.Usuario;
import java.util.Optional;

public interface UsuarioDAO {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorMail(String email);
    Boolean existePorMail(String email);
    Optional<Rol> buscarRolPorNombre(String nombre);
}