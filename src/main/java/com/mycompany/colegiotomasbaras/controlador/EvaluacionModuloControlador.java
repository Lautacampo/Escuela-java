/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.controlador;
import com.mycompany.colegiotomasbaras.dto.CalificarModuloSolicitud;
import com.mycompany.colegiotomasbaras.dto.CalificarModuloRespuesta;
import com.mycompany.colegiotomasbaras.servicio.EvaluacionModuloServicio;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author Maximo Gallardo
 */
@RestController
@RequestMapping("/api/evaluaciones-modulo")
@CrossOrigin(origins = "*")
public class EvaluacionModuloControlador {

    private final EvaluacionModuloServicio servicio;


    public EvaluacionModuloControlador(
            EvaluacionModuloServicio servicio) {

        this.servicio = servicio;
    }

    // CASO DE USO: CALIFICAR MODULO

    @PostMapping
    public ResponseEntity<CalificarModuloRespuesta> calificarModulo(
            @Valid @RequestBody
            CalificarModuloSolicitud solicitud) {

        CalificarModuloRespuesta respuesta =
                servicio.calificarModulo(solicitud);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);
    }
}