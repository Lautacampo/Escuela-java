package com.mycompany.colegiotomasbaras.servicio;

import com.mycompany.colegiotomasbaras.dao.UsuarioDAO;
import com.mycompany.colegiotomasbaras.modelo.login.Rol;
import com.mycompany.colegiotomasbaras.modelo.login.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AuthServicio {

    private final UsuarioDAO usuarioDAO;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final List<String> ROLES_VALIDOS = List.of("directivo", "docente", "preceptor");

    public AuthServicio(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional(readOnly = true)
    public Usuario autenticar(String email, String password) {
       
        Usuario usuario = usuarioDAO.buscarPorMail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Credenciales inválidas"
                ));

        
        if (!Boolean.TRUE.equals(usuario.getActivo()) || !passwordEncoder.matches(password, usuario.getContrahash())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Credenciales inválidas"
            );
        }

        return usuario;
    }

    @Transactional
    public Usuario registrar(String nombre, String apellido, String email, String password, String rolNombre) {
        if (rolNombre == null || !ROLES_VALIDOS.contains(rolNombre.toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol inválido");
        }

        if (password == null || password.length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña debe tener al menos 4 caracteres");
        }

        if (usuarioDAO.existePorMail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
        }

        
        Rol rolEncontrado = usuarioDAO.buscarRolPorNombre(rolNombre.toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El rol especificado no existe"));

        String hash = passwordEncoder.encode(password);

      
        Usuario nuevoUsuario = new Usuario();

        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setContrahash(hash);
        nuevoUsuario.setActivo(true);

         // Asociar el usuario con su rol
        nuevoUsuario.setRol(rolEncontrado);

        return usuarioDAO.guardar(nuevoUsuario);

    }
}
