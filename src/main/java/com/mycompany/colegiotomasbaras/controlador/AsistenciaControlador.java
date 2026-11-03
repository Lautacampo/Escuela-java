package com.mycompany.colegiotomasbaras.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin(origins = "*")
public class AsistenciaControlador {

    @GetMapping
    public ResponseEntity<List<?>> obtenerAsistencias() {
     
        return ResponseEntity.ok(List.of());
    }
}