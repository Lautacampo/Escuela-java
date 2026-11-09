/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.controlador;

import com.mycompany.colegiotomasbaras.dto.AnioMateriasDTO;
import com.mycompany.colegiotomasbaras.dto.FichaMateriaDTO;
import com.mycompany.colegiotomasbaras.servicio.MateriaServicio;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Usuario
 */

@RestController
@RequestMapping("/api/materias")
@CrossOrigin(origins = "*")
public class MateriaControlador {
    
    private final MateriaServicio materiaService;
    
    public MateriaControlador(MateriaServicio materiaService) {
        this.materiaService = materiaService;
    }
    
    // Lista todas las materias de un año
    @GetMapping
    public ResponseEntity<List<AnioMateriasDTO>> listarMateriasPorAnio() {
        return ResponseEntity.ok(
            materiaService.listarMateriasPorAnio());
    }
    
    // CASO DE USO: CONSULTAR MATERIA (año es opcional)
 @GetMapping("/{id}")
public ResponseEntity<FichaMateriaDTO> consultarMateria(
        @PathVariable("id") Long id,
        @RequestParam(value = "anio", required = false) Integer anio) {

    return ResponseEntity.ok(materiaService.consultarMateria(id, anio));
}
}
