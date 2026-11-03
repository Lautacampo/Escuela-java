/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.controlador;

import com.mycompany.colegiotomasbaras.modelo.login.Usuario;
import com.mycompany.colegiotomasbaras.modelo.login.Usuario;
import com.mycompany.colegiotomasbaras.servicio.AuthServicio;

import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;

import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Maximo Gallardo
 */
@RestController
@RequestMapping("/api/auth")
public class AuthControlador {

    // Servicio que autentica y registra usuarios
    private final AuthServicio authServicio;

    // Componente encargado de firmar tokens JWT
    private final JwtEncoder jwtEncoder;


    // Constructor con inyección de dependencias
    public AuthControlador(
            AuthServicio authServicio,
            JwtEncoder jwtEncoder) {

        this.authServicio = authServicio;
        this.jwtEncoder = jwtEncoder;
    }


    // DTOs utilizados para comunicarse con React

    public record LoginSolicitud(
            String email,
            String password
    ) {}

    public record RegistroSolicitud(
            String nombre,
            String apellido,
            String email,
            String password,
            String rol
    ) {}

    public record UsuarioDTO(
            Long id,
            String nombre,
            String apellido,
            String email,
            String rol
    ) {}

    public record AuthRespuesta(
            String token,
            UsuarioDTO usuario
    ) {}


    // CASO DE USO: INICIAR SESIÓN

    @PostMapping("/login")
    public ResponseEntity<AuthRespuesta> login(
            @RequestBody LoginSolicitud req) {

        // 1. Validar los datos recibidos
        if (req == null
                || req.email() == null
                || req.email().isBlank()
                || req.password() == null
                || req.password().isBlank()) {

            return ResponseEntity.badRequest().build();
        }

        // 2. Autenticar mediante AuthServicio
        Usuario usuario = authServicio.autenticar(
                req.email().trim(),
                req.password()
        );

        // 3. Obtener el rol del usuario
        String rol = usuario.getRol()
                .getNombre()
                .trim()
                .toLowerCase(Locale.ROOT);

        // 4. Crear DTO del usuario
        UsuarioDTO dto = new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                rol
        );

        // 5. Establecer fecha de emisión y vencimiento
        Instant ahora = Instant.now();

        Instant vencimiento = ahora.plus(
                Duration.ofHours(2)
        );

        // 6. Construir los datos del JWT
        JwtClaimsSet claims = JwtClaimsSet.builder()

                .issuer("colegio-tomas-baras-java")

                .issuedAt(ahora)

                .expiresAt(vencimiento)

                .subject(usuario.getId().toString())

                .claim("email", usuario.getEmail())

                .claim("rol", rol)

                .build();

        // 7. Especificar algoritmo de firma
        JwsHeader encabezado = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        // 8. Generar un token JWT firmado
        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(
                        encabezado,
                        claims
                )
        ).getTokenValue();

        // 9. Devolver JWT y datos del usuario
        return ResponseEntity.ok(
                new AuthRespuesta(token, dto)
        );
    }


    // CASO DE USO: REGISTRAR USUARIO

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registro(
            @RequestBody RegistroSolicitud req) {

        // 1. Validar los datos
        if (req == null
                || req.nombre() == null
                || req.apellido() == null
                || req.email() == null
                || req.password() == null
                || req.rol() == null) {

            return ResponseEntity.badRequest().build();
        }

        // 2. Registrar usuario mediante el servicio
        Usuario creado = authServicio.registrar(
                req.nombre(),
                req.apellido(),
                req.email().trim(),
                req.password(),
                req.rol()
        );

        // 3. Construir el DTO de respuesta
        UsuarioDTO dto = new UsuarioDTO(
                creado.getId(),
                creado.getNombre(),
                creado.getApellido(),
                creado.getEmail(),
                creado.getRol().getNombre()
        );

        // 4. Devolver usuario registrado
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }
}
