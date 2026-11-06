package com.mycompany.colegiotomasbaras.servicio;

import com.mycompany.colegiotomasbaras.dao.CalificacionesDAO;
import com.mycompany.colegiotomasbaras.dao.CursoDAO;
import com.mycompany.colegiotomasbaras.dto.CalificacionesDTO.*;
import com.mycompany.colegiotomasbaras.modelo.academico.*;
import com.mycompany.colegiotomasbaras.modelo.evaluacion.Calificacion;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CalificacionesServicio {

    private static final String APROBADO = "Aprobado";
    private static final String DESAPROBADO = "Desaprobado";
    private static final String EN_CURSO = "En Curso";

    private final CalificacionesDAO dao;
    private final CursoDAO cursoDAO;

    public CalificacionesServicio(CalificacionesDAO dao, CursoDAO cursoDAO) {
        this.dao = dao;
        this.cursoDAO = cursoDAO;
    }

    // GET /api/calificaciones/modulos
    @Transactional(readOnly = true)
    public List<AnioModulos> listarModulosPorAnio() {
        Map<Integer, Map<Long, Modulo>> modulosPorAnio = new TreeMap<>();
        Map<Integer, Map<Long, TreeSet<String>>> divisionesPorAnio = new TreeMap<>();

        for (Curso curso : cursoDAO.obtenerTodosconModulos()) {
            int anio = curso.getAnioCurso();
            for (Modulo m : curso.getModulos()) {
                modulosPorAnio.computeIfAbsent(anio, k -> new TreeMap<>()).put(m.getId(), m);
                divisionesPorAnio.computeIfAbsent(anio, k -> new TreeMap<>())
                        .computeIfAbsent(m.getId(), k -> new TreeSet<>())
                        .add(curso.getDivision());
            }
        }

        List<AnioModulos> resultado = new ArrayList<>();
        modulosPorAnio.forEach((anio, modulos) -> {
            List<ModuloResumen> lista = new ArrayList<>();
            modulos.forEach((id, m) -> lista.add(new ModuloResumen(
                    id,
                    m.getNumeroLeyenda(),
                    m.getMaterias().stream().map(Materia::getNombre).sorted().toList(),
                    List.copyOf(divisionesPorAnio.get(anio).get(id)))));
            resultado.add(new AnioModulos(anio, lista));
        });
        return resultado;
    }

    // GET /api/calificaciones/modulos/{moduloId}/divisiones/{division}
    @Transactional(readOnly = true)
    public Grilla obtenerGrilla(Long moduloId, String division) {
        Modulo modulo = dao.buscarModulo(moduloId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Módulo no encontrado"));
        String div = division.toUpperCase(Locale.ROOT);

        List<Materia> materias = modulo.getMaterias().stream()
                .sorted(Comparator.comparing(Materia::getNombre)).toList();
        List<CursadoModulo> cursadas = dao.buscarCursadasConAlumno(moduloId, div);

        Map<Long, Map<Long, String>> estados = new HashMap<>();
        if (!cursadas.isEmpty()) {
            List<Long> ids = cursadas.stream().map(CursadoModulo::getId).toList();
            for (Calificacion c : dao.buscarPorCursadas(ids)) {
                estados.computeIfAbsent(c.getCursadaModulo().getId(), k -> new HashMap<>())
                        .put(c.getMateria().getId(), normalizar(c.getEstado()));
            }
        }

        List<AlumnoGrilla> alumnos = cursadas.stream().map(c -> {
            Map<Long, String> porMateria = new LinkedHashMap<>();
            Map<Long, String> guardados = estados.getOrDefault(c.getId(), Map.of());
            for (Materia m : materias) {
                porMateria.put(m.getId(), guardados.getOrDefault(m.getId(), EN_CURSO));
            }
            return new AlumnoGrilla(c.getId(), c.getAlumno().getApellido(),
                    c.getAlumno().getNombre(), porMateria);
        }).toList();

        return new Grilla(
                materias.stream().map(m -> new MateriaGrilla(m.getId(), m.getNombre())).toList(),
                alumnos);
    }

    // PUT /api/calificaciones
    @Transactional
    public void guardarEstado(SetEstado dto) {
        if (dto.cursadaModuloId() == null || dto.materiaId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos inválidos");
        }
        String estado = parsear(dto.estado());
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos inválidos");
        }

        CursadoModulo cursada = dao.buscarCursada(dto.cursadaModuloId())
                .orElseThrow(() -> noExiste());
        Materia materia = dao.buscarMateria(dto.materiaId())
                .orElseThrow(() -> noExiste());
        if (!cursada.getModulo().getMaterias().contains(materia)) {
            throw noExiste();
        }

        Calificacion cal = dao.buscar(cursada.getId(), materia.getId()).orElseGet(() -> {
            Calificacion nueva = new Calificacion();
            nueva.setCursadaModulo(cursada);
            nueva.setMateria(materia);
            return nueva;
        });
        cal.setEstado(estado);
        dao.guardar(cal);
    }

    private static ResponseStatusException noExiste() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "La cursada o la materia no existe");
    }

    private static String parsear(String e) {
        if (e == null) return null;
        return switch (e.trim().toUpperCase(Locale.ROOT).replace('_', ' ')) {
            case "APROBADO" -> APROBADO;
            case "DESAPROBADO" -> DESAPROBADO;
            case "EN CURSO" -> EN_CURSO;
            default -> null;
        };
    }

    private static String normalizar(String e) {
        String p = parsear(e);
        return p == null ? EN_CURSO : p;
    }
}