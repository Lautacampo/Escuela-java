package com.mycompany.colegiotomasbaras.controlador;

import com.mycompany.colegiotomasbaras.modelo.login.Usuario;
import com.mycompany.colegiotomasbaras.servicio.AuthServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthControlador {

    private final AuthServicio authServicio;

    public AuthControlador(AuthServicio authServicio) {
        this.authServicio = authServicio;
    }

    // DTOs / Records para comunicación en JSON con React
    public record LoginSolicitud(String email, String password) {}
    public record RegistroSolicitud(String nombre, String apellido, String email, String password, String rol) {}
    public record UsuarioDTO(Long id, String nombre, String apellido, String email, String rol) {}
    public record AuthRespuesta(String token, UsuarioDTO usuario) {}

    @PostMapping("/login")
    public ResponseEntity<AuthRespuesta> login(@RequestBody LoginSolicitud req) {
        if (req.email() == null || req.email().isBlank() ||
            req.password() == null || req.password().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Usuario usuario = authServicio.autenticar(req.email().trim(), req.password());

        // Extraemos el nombre del rol desde el objeto Rol
        UsuarioDTO dto = new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getRol().getNombre()
        );

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6" + usuario.getId() +
                       ",InJvbCI6Ii" + usuario.getRol().getNombre() + "\"}";

        return ResponseEntity.ok(new AuthRespuesta(token, dto));
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registro(@RequestBody RegistroSolicitud req) {
        if (req.nombre() == null || req.apellido() == null ||
            req.email() == null || req.password() == null || req.rol() == null) {
            return ResponseEntity.badRequest().build();
        }

        Usuario creado = authServicio.registrar(
                req.nombre(), req.apellido(), req.email().trim(), req.password(), req.rol()
        );

        UsuarioDTO dto = new UsuarioDTO(
                creado.getId(),
                creado.getNombre(),
                creado.getApellido(),
                creado.getEmail(),
                creado.getRol().getNombre()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}