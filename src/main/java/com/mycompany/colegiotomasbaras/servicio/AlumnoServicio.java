/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.servicio;
import com.mycompany.colegiotomasbaras.modelo.personas.Alumno;
import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import com.mycompany.colegiotomasbaras.modelo.academico.Curso;
import com.mycompany.colegiotomasbaras.modelo.academico.Modulo;
import com.mycompany.colegiotomasbaras.modelo.enums.CondicionAsistencia;
import com.mycompany.colegiotomasbaras.modelo.enums.EstadoAcademico;
import com.mycompany.colegiotomasbaras.repositorio.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author Lautaro
 */

@Service
public class AlumnoServicio {
    
    private final AlumnoRepositorio alumnoRepo;
    private final CursoRepositorio cursoRepo;
    private final CursadaModuloRepositorio cursadamoduloRepo;
    private final ModuloRepositorio moduloRepo;
    
    public AlumnoServicio(AlumnoRepositorio alumnoRepo,CursoRepositorio cursoRepo, CursadaModuloRepositorio cursadamoduloRepo, ModuloRepositorio moduloRepo ){
        this.alumnoRepo=alumnoRepo;
        this.cursoRepo=cursoRepo;
        this.cursadamoduloRepo=cursadamoduloRepo;
        this.moduloRepo=moduloRepo;
    }
    
   // CU : listar alumnos
    public List<Alumno> listarTodosAlumnos(){
        return alumnoRepo.findAll();
    }
    
    //CU : registrar alumno
    
    @Transactional //envuelve tood el metodosi algo falla hace deshace todo, solo guarda los que pasaron sin errores
    public Alumno registarAlumno(Alumno nuevoAlumno){
        if(alumnoRepo.existByDni(nuevoAlumno.getDni())){
            throw new RuntimeException("Ya existe un alumno registrado con el DNI :"+ nuevoAlumno.getDni());
        }
        return alumnoRepo.save(nuevoAlumno);
    }
    
}
