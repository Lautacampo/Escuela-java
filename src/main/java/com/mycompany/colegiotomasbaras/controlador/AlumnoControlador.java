
package com.mycompany.colegiotomasbaras.controlador;

import com.mycompany.colegiotomasbaras.modelo.personas.Alumno;
import com.mycompany.colegiotomasbaras.servicio.AlumnoServicio;
import com.mycompany.colegiotomasbaras.dto.ResultadoMatricula;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@CrossOrigin(origins = "http://localhost:5173")
public class AlumnoControlador {

    private final AlumnoServicio alumnoService;

    // Inyeccion de dependencias

    public AlumnoControlador(AlumnoServicio alumnoService) {
        this.alumnoService = alumnoService;
    }

    // Respuesta resumida para React

    public record AlumnoResumen(
            Long id,
            String nombre,
            String apellido,
            String dni
    ) {}

    // Consultar alumnos

    @GetMapping
    public ResponseEntity<List<AlumnoResumen>> listarTodosAlumnos() {

        List<AlumnoResumen> alumnos =
                alumnoService.listarTodosAlumnos()
                    .stream()
                    .map(alumno -> new AlumnoResumen(
                            alumno.getId(),
                            alumno.getNombre(),
                            alumno.getApellido(),
                            alumno.getDni()
                    ))
                    .toList();

        return ResponseEntity.ok(alumnos);
    }

    // CASO DE USO: REGISTRAR ALUMNO

    @PostMapping
    public ResponseEntity<AlumnoResumen> registrarAlumno(
            @RequestBody Alumno alumno) {

        Alumno nuevoAlumno =
                alumnoService.registrarAlumno(alumno);

        AlumnoResumen respuesta = new AlumnoResumen(
                nuevoAlumno.getId(),
                nuevoAlumno.getNombre(),
                nuevoAlumno.getApellido(),
                nuevoAlumno.getDni()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);
    }

    // CASO DE USO: MATRICULAR ALUMNO

    @PostMapping("/{idAlumno}/matricular")
    public ResponseEntity<ResultadoMatricula> matricularAlumno(
            @PathVariable Long idAlumno,
            @RequestParam Long idAula,
            @RequestParam Long idModulo,
            @RequestParam(defaultValue = "false")
                boolean autorizarConAdvertencias) {

        ResultadoMatricula resultado =
                alumnoService.matricularAlumno(
                        idAlumno,
                        idAula,
                        idModulo,
                        autorizarConAdvertencias
                );

        HttpStatus estado = resultado.registrada()
                ? HttpStatus.CREATED
                : HttpStatus.OK;

        return ResponseEntity
                .status(estado)
                .body(resultado);
    }
}
