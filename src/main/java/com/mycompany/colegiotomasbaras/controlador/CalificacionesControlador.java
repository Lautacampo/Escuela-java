package com.mycompany.colegiotomasbaras.controlador;

import com.mycompany.colegiotomasbaras.dto.CalificacionesDTO.*;
import com.mycompany.colegiotomasbaras.servicio.CalificacionesServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
@CrossOrigin(origins = "*")
public class CalificacionesControlador {

    private final CalificacionesServicio servicio;

    public CalificacionesControlador(CalificacionesServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/modulos")
    public ResponseEntity<List<AnioModulos>> modulos() {
        return ResponseEntity.ok(servicio.listarModulosPorAnio());
    }

    @GetMapping("/modulos/{moduloId}/divisiones/{division}")
    public ResponseEntity<Grilla> grilla(@PathVariable Long moduloId,
                                         @PathVariable String division) {
        return ResponseEntity.ok(servicio.obtenerGrilla(moduloId, division));
    }

    @PutMapping
    public ResponseEntity<Void> setCalificacion(@RequestBody SetEstado dto) {
        servicio.guardarEstado(dto);
        return ResponseEntity.ok().build();
    }
}