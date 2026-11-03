package com.mycompany.colegiotomasbaras.controlador;

import com.mycompany.colegiotomasbaras.dto.MatriculaDTO;
import com.mycompany.colegiotomasbaras.dto.ResultadoMatricula;
import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import com.mycompany.colegiotomasbaras.modelo.enums.EstadoAcademico;
import com.mycompany.colegiotomasbaras.modelo.personas.Alumno;
import com.mycompany.colegiotomasbaras.servicio.AlumnoServicio;
import com.mycompany.colegiotomasbaras.dto.FichaAlumnoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@CrossOrigin(origins = "*")
public class AlumnoControlador {

    private final AlumnoServicio alumnoService;

    // Inyeccion de dependencias
    public AlumnoControlador(AlumnoServicio alumnoService) {
        this.alumnoService = alumnoService;
    }

    // Nuevo record para las notas adentro de cada cursada
    public record EvaluacionHistorial(
            Integer nroInstancia,
            String fecha,
            Double nota,
            String tipoInstancia
    ) {}

    // Nuevo record para cada fila de la trayectoria académica
    public record CursadaHistorial(
            Long idCursada,
            Integer anioCurso,
            String division,
            String modulo,
            String estadoAcademico,
            Double faltas,
            List<EvaluacionHistorial> evaluaciones // Agregado para ver los detalles de las notas
    ) {}

    // Respuesta resumida para React con todos los datos de la ficha
    public record AlumnoResumen(
            Long id,
            Long cursadaModuloId, 
            String nombre,
            String apellido,
            String dni,
            Integer anioCurso,
            String division,
            String modulo,
            Double faltasAcumuladas,
            String condicion,
            
            // --- DATOS PERSONALES ---
            java.time.LocalDate fechaNacimiento,
            Integer edad,
            String cuil,
            String lugarNacimiento,
            String domicilio,
            String telefono,
            String correoElectronico,
            String telefonoEmergencia,
            Integer cantidadHijos,
            String situacionLaboral,
            
            // --- HISTORIAL (TRAYECTORIA ACADEMICA) ---
            List<CursadaHistorial> trayectoria
    ) {}

    // Arma el resumen de un alumno a partir de su cursada actual
    private AlumnoResumen aResumen(Alumno alumno) {

        List<CursadoModulo> cursadas = alumno.getCursadas() == null
                ? List.of()
                : alumno.getCursadas();

        CursadoModulo actual = cursadas.stream()
                .filter(c -> c.getEstadoAcademico() == EstadoAcademico.EN_CURSO)
                .max(Comparator.comparing(CursadoModulo::getId))
                .orElse(null);

        // Armamos el historial completo de la trayectoria incluyendo evaluaciones
        List<CursadaHistorial> historial = cursadas.stream()
                .map(c -> {
                    // Mapeamos las evaluaciones de esta cursada (forma corregida y limpia)
                    List<EvaluacionHistorial> evals;
                    
                    if (c.getEvaluaciones() == null) {
                        evals = List.of();
                    } else {
                        evals = c.getEvaluaciones().stream()
                                .map(e -> new EvaluacionHistorial(
                                        e.getNroInstancia(),
                                        e.getFecha() != null ? e.getFecha().toString() : null,
                                        e.getNota(),
                                        e.getTipoInstancia() != null ? e.getTipoInstancia().name() : null
                                ))
                                .toList();
                    }

                    return new CursadaHistorial(
                            c.getId(),
                            c.getCurso() != null ? c.getCurso().getAnioCurso() : null,
                            c.getCurso() != null ? c.getCurso().getDivision() : null,
                            c.getModulo() != null ? c.getModulo().getNumeroLeyenda() : null,
                            c.getEstadoAcademico() != null ? c.getEstadoAcademico().name() : "DESCONOCIDO",
                            c.getCantFaltasAcumuladas(),
                            evals
                    );
                })
                .toList();

        return new AlumnoResumen(
                alumno.getId(),
                actual != null ? actual.getId() : null,
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getDni(),
                
                // Datos de la cursada
                actual != null ? actual.getCurso().getAnioCurso() : null,
                actual != null ? actual.getCurso().getDivision() : null,
                actual != null ? actual.getModulo().getNumeroLeyenda() : null,
                actual != null ? actual.getCantFaltasAcumuladas() : 0.0,
                actual != null && actual.getCondicion() != null
                        ? actual.getCondicion().name()
                        : null,
                        
                // Datos personales
                alumno.getFechaNacimiento(),
                alumno.getEdad(), 
                alumno.getCuil(), 
                alumno.getLugarNacimiento(),
                alumno.getDomicilio(), 
                alumno.getTelefono(), 
                alumno.getEmail(), 
                alumno.getTelefonoEmergencia(),
                alumno.getCantidadHijos(),
                
                // Mapeo del booleano trabaja a String
                (alumno.getTrabaja() != null && alumno.getTrabaja()) ? "Trabaja" : "No trabaja",
                
                // Pasamos la lista del historial al final
                historial
        );
    }

    // Consultar alumnos
    @GetMapping
    public ResponseEntity<List<AlumnoResumen>> listarTodosAlumnos() {

        List<AlumnoResumen> alumnos =
                alumnoService.listarAlumnosConCursada()
                    .stream()
                    .map(this::aResumen)
                    .toList();

        return ResponseEntity.ok(alumnos);
    }

    // CASO DE USO: REGISTRAR ALUMNO
    @PostMapping
    public ResponseEntity<AlumnoResumen> registrarAlumno(
            @RequestBody Alumno alumno) {

        Alumno nuevoAlumno =
                alumnoService.registrarAlumno(alumno);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(aResumen(nuevoAlumno));
    }
    
     // CASO DE USO: CONSULTAR ALUMNO
    @GetMapping("/{id}")
    public ResponseEntity<FichaAlumnoDTO> consultarAlumno(
            @PathVariable("id") Long id) {

        FichaAlumnoDTO ficha = alumnoService.consultarAlumno(id);

        return ResponseEntity.ok(ficha);
    }

    // CASO DE USO: MATRICULAR ALUMNO
    @PostMapping("/{idAlumno}/matricular")
    public ResponseEntity<ResultadoMatricula> matricularAlumno(
            @PathVariable Long idAlumno,
            @RequestBody MatriculaDTO dto) {

        ResultadoMatricula resultado =
                alumnoService.matricularAlumno(
                        idAlumno,
                        dto.getIdCurso(),
                        dto.getIdModulo(),
                        dto.isAutorizarConAdvertencias()
                );

        HttpStatus estado = resultado.registrada()
                ? HttpStatus.CREATED
                : HttpStatus.OK;

        return ResponseEntity
                .status(estado)
                .body(resultado);
    }
}
