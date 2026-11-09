/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.servicio;

import com.mycompany.colegiotomasbaras.dao.MateriaDAO;
import com.mycompany.colegiotomasbaras.dto.AnioMateriasDTO;
import com.mycompany.colegiotomasbaras.dto.FichaMateriaDTO;
import com.mycompany.colegiotomasbaras.modelo.academico.Curso;
import com.mycompany.colegiotomasbaras.modelo.academico.Materia;
import com.mycompany.colegiotomasbaras.modelo.academico.Modulo;
import com.mycompany.colegiotomasbaras.modelo.personas.Docente;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Usuario
 */

@Service
public class MateriaServicio {
    
    // Interfaz del patron DAO
    private final MateriaDAO materiaDAO;
    
    public MateriaServicio(MateriaDAO materiaDAO){
        this.materiaDAO = materiaDAO;
    }
    
    // Materias agrupadas por año de cursado
    @Transactional(readOnly = true)
    public List<AnioMateriasDTO> listarMateriasPorAnio() {
        
        Map<Integer, Map<Long, Materia>> porAnio = new TreeMap();
        
        for (Materia materia : materiaDAO.listarTodas()) {
            for (Modulo modulo : materia.getModulos()) {
                for (Curso curso : modulo.getCursos()) {
                    porAnio.computeIfAbsent(curso.getAnioCurso(), k -> new TreeMap<>()).put(materia.getId(), materia);
                }
            }
        }
        
        return porAnio.entrySet().stream()
            .map(e -> new AnioMateriasDTO(
                e.getKey(),
                e.getValue().values().stream()
                    .sorted(Comparator.comparing(
                        Materia::getNombre))
                    .map(m -> new AnioMateriasDTO.MateriaResumen(m.getId(), m.getNombre()))
                        .toList()))
            .toList();
    }
    
    private boolean dictaMateria(Docente docente, Materia materia) {
        return docente.getMaterias().stream()
                .anyMatch(m -> m.getId().equals(materia.getId()));
    }
    
    // Docentes de un curso específico que dictan una materia en particular
    private List<FichaMateriaDTO.DocenteMateria> docentesDe(Curso curso, Materia materia) {
        return curso.getDocentes().stream()
                .filter(d -> dictaMateria(d, materia))
                .sorted(Comparator
                        .comparing(Docente::getApellido)
                        .thenComparing(Docente::getNombre))
                .map(d -> new FichaMateriaDTO.DocenteMateria(d.getId(), d.getNombre(), d.getApellido()))
                .toList();
    }
    
    // Divisiones que cursan un modulo particular con sus docentes
    private List<FichaMateriaDTO.DivisionMateria> divisionesDe(Modulo modulo, Materia materia, Integer anio) {
        return modulo.getCursos().stream()
                .filter(c -> anio == null || anio.equals(c.getAnioCurso()))
                .sorted(Comparator
                        .comparing(Curso::getAnioCurso)
                        .thenComparing(Curso::getDivision)
                        .thenComparing(
                            (Curso c) -> c.getCicloLectivo().getAnio(),
                            Comparator.reverseOrder()))
                .map(c -> new FichaMateriaDTO.DivisionMateria(c.getAnioCurso(), c.getDivision(), c.getCicloLectivo().getAnio(), docentesDe(c, materia)))
                .toList();
    }
    
    // CASO DE USO: CONSULTAR MATERIA
    @Transactional(readOnly = true)
    public FichaMateriaDTO consultarMateria(Long idMateria, Integer anio) {
        
        if(idMateria == null || idMateria <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El ID de la materia no es válido"
            );
        }
        
        if (anio != null && anio <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El año no es válido"
            );
        }
        
        Materia materia = materiaDAO.buscarPorId(idMateria)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Materia no encontrada"
                )
            );
        
        List<FichaMateriaDTO.ModuloMateria> modulos =
            materia.getModulos().stream()
                .sorted(Comparator.comparing(
                        Modulo::getNumeroLeyenda))
                .map(modulo -> new FichaMateriaDTO.ModuloMateria(
                        modulo.getId(),
                        modulo.getNumeroLeyenda(), 
                        divisionesDe(modulo, materia, anio)))
                .filter(m -> anio == null || !m.divisiones().isEmpty())
                .toList();
        
        return new FichaMateriaDTO(
            materia.getId(),
            materia.getNombre(),
            List.copyOf(materia.getContenidos()),
            modulos);
    }
}
