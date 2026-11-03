
package com.mycompany.colegiotomasbaras.servicio;


import com.mycompany.colegiotomasbaras.dao.AlumnoDAO;
import com.mycompany.colegiotomasbaras.dao.CursoDAO;
import com.mycompany.colegiotomasbaras.dao.ModuloDAO;
import com.mycompany.colegiotomasbaras.dao.CursadoModuloDAO;

import com.mycompany.colegiotomasbaras.dto.ResultadoMatricula;

import com.mycompany.colegiotomasbaras.modelo.personas.Alumno;
import com.mycompany.colegiotomasbaras.modelo.academico.Curso;
import com.mycompany.colegiotomasbaras.modelo.academico.Modulo;
import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;

import com.mycompany.colegiotomasbaras.modelo.enums.CondicionAsistencia;
import com.mycompany.colegiotomasbaras.modelo.enums.EstadoAcademico;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
public class AlumnoServicio {

    // Interfaces del patron DAO

    private final AlumnoDAO alumnoDAO;
    private final CursoDAO cursoDAO;
    private final ModuloDAO moduloDAO;
    private final CursadoModuloDAO cursadaDAO;


    public AlumnoServicio(
            AlumnoDAO alumnoDAO,
            CursoDAO cursoDAO,
            ModuloDAO moduloDAO,
            CursadoModuloDAO cursadaDAO) {

        this.alumnoDAO = alumnoDAO;
        this.cursoDAO =  cursoDAO;
        this.moduloDAO = moduloDAO;
        this.cursadaDAO = cursadaDAO;
    }

    // Consultar alumnos

    @Transactional(readOnly = true)
    public List<Alumno> listarTodosAlumnos() {
        return alumnoDAO.listarTodos();
    }

    // CASO DE USO: REGISTRAR ALUMNO

    @Transactional
    public Alumno registrarAlumno(Alumno nuevoAlumno) {

        if (nuevoAlumno == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los datos del alumno son obligatorios"
            );
        }

        if (nuevoAlumno.getNombre() == null
                || nuevoAlumno.getNombre().isBlank()
                || nuevoAlumno.getApellido() == null
                || nuevoAlumno.getApellido().isBlank()
                || nuevoAlumno.getDni() == null
                || nuevoAlumno.getDni().isBlank()
                || nuevoAlumno.getFechaNacimiento() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Faltan datos obligatorios"
            );
        }

        if (alumnoDAO.existePorDni(nuevoAlumno.getDni())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una persona con ese DNI"
            );
        }

        return alumnoDAO.guardar(nuevoAlumno);
    }

    // CASO DE USO: MATRICULAR ALUMNO

    @Transactional(readOnly=true)
    public List<Alumno> listarAlumnosConCursada() {
    return alumnoDAO.obtenerTodos().stream()
            .sorted(Comparator.comparing(Alumno::getApellido)
                    .thenComparing(Alumno::getNombre))
            .toList();
    }
    

    @Transactional
    public ResultadoMatricula matricularAlumno(
            Long idAlumno,
            Long idCurso,
            Long idModulo,
            boolean autorizarConAdvertencias) {

        // 1. Buscar al alumno utilizando DAO.

        Alumno alumno = alumnoDAO.buscarPorId(idAlumno)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alumno no encontrado"
                    )
                );

        // 2. Buscar el aula utilizando DAO.

        Curso curso = cursoDAO.buscarPorId(idCurso)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Curso no encontrada"
                    )
                );

        // 3. Buscar el modulo utilizando DAO.

        Modulo modulo = moduloDAO.buscarPorId(idModulo)
                .orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Modulo no encontrado"
                    )
                );

        // 4. Verificar que el modulo corresponda al aula.

        boolean corresponde = curso.getModulos().stream()
                .anyMatch(m ->
                    m.getId().equals(modulo.getId())
                );

        if (!corresponde) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El modulo no corresponde al aula"
            );
        }

        // 5. Comprobar matricula duplicada.

        if (cursadaDAO.existeMatricula(
                idAlumno, idCurso, idModulo)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El alumno ya esta matriculado"
            );
        }

        // 6. Consultar trayectoria academica.

        List<CursadoModulo> historial =
                cursadaDAO.buscarPorAlumno(idAlumno);

        List<String> advertencias = new ArrayList<>();

        // 7. Comprobar correlatividades.

        for (Modulo requisito : modulo.getPrerrequisitos()) {

            boolean aprobado = historial.stream()
                    .anyMatch(c ->
                        c.getModulo().getId()
                            .equals(requisito.getId())
                        &&
                        c.getEstadoAcademico()
                            == EstadoAcademico.APROBADO
                    );

            if (!aprobado) {
                advertencias.add(
                    "No tiene aprobado el Modulo "
                    + requisito.getNumeroLeyenda()
                );
            }
        }

        // 8. Solicitar decision del Directivo
        // cuando existen advertencias.

        if (!advertencias.isEmpty()
                && !autorizarConAdvertencias) {

            return new ResultadoMatricula(
                    false,
                    null,
                    true,
                    advertencias
            );
        }


        // 9. Crear una nueva cursada.

        CursadoModulo cursada = new CursadoModulo();

        cursada.setAlumno(alumno);
        cursada.setCurso(curso);
        cursada.setModulo(modulo);

        cursada.setCondicion(
                CondicionAsistencia.REGULAR
        );

        cursada.setCantFaltasAcumuladas(0.0);

        cursada.setEstadoAcademico(
                EstadoAcademico.EN_CURSO
        );

        // 10. Guardar utilizando DAO.

        CursadoModulo guardada =
                cursadaDAO.guardarNueva(cursada);

        // 11. Devolver resultado.

        return new ResultadoMatricula(
                true,
                guardada.getId(),
                false,
                advertencias
        );
    }
    

    
}
