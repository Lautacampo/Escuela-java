package com.mycompany.colegiotomasbaras.controlador;

import com.mycompany.colegiotomasbaras.dto.ModuloDTO;
import com.mycompany.colegiotomasbaras.servicio.CalificacionModuloServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/calificaciones/modulos")
@CrossOrigin(origins="*")
public class CalificacionModuloControlador {

    private final CalificacionModuloServicio servicio;

    public CalificacionModuloControlador(CalificacionModuloServicio servicio) {
        this.servicio = servicio;
    }

    // Estructura interna para emular exactamente el JSON que espera React
    public static class GrupoAnioRespuesta {
        private Integer anio;
        private List<ModuloDTO> modulos = new ArrayList<>(); // Lista inicializada para evitar nulos

        public GrupoAnioRespuesta(Integer anio, List<ModuloDTO> modulos) {
            this.anio = anio;
            this.modulos = modulos != null ? modulos : new ArrayList<>();
        }

        public Integer getAnio() { return anio; }
        public List<ModuloDTO> getModulos() { return modulos; }
    }

    @GetMapping
    public ResponseEntity<?> obtenerModulos() {
        List<ModuloDTO> modulos = servicio.listarModulos();
        
        List<GrupoAnioRespuesta> respuesta = new ArrayList<>();
        
        // Agrupamos todos los módulos bajo el año 1 para que el frontend pueda renderizarlos.
        // (Si tu ModuloDTO tiene un método getAnio(), puedes separarlos dinámicamente)
        respuesta.add(new GrupoAnioRespuesta(1, modulos));
        
        return ResponseEntity.ok(respuesta);
    }
}