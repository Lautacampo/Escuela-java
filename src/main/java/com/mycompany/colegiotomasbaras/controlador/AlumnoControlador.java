/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.controlador;
import com.mycompany.colegiotomasbaras.modelo.personas.Alumno;
import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import com.mycompany.colegiotomasbaras.servicio.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 * @author Lautaro
 */

@RestController
@RequestMapping("/api/alumnos")
@CrossOrigin(origins = "http://localhost:5173") 


public class AlumnoControlador {
    
    private final AlumnoService = alumnoService;
    
    
    public AlumnoControlador (AlumnoServicio alumnoService){
        this.alumnoService=alumnoService;
    }
    
    
    // CU : listar alumnos
    
    @GetMapping // Avisa a SpringBoot que cuando alguien haga una peticion para leer datos en esta URL, ejecuta este codigo. se llama get solo pide ver informacion no guarda ni modific nada
    //ResponseEntity "caja de encomiendoa" ek servido va a mandar por internet al navegadorlo que va en <> indica que va en esa caja, en esta caso lista de alumnos
    // el list<alumnos> de adentro llama al service para que vaya a la bdd busque los alumnos y los guarde en la varibael alumnos
    // el retunr , envio final , agarras los alumnos y los metes en la caja y se le pega la etiqueta HttpStatus.OK es el codigo 200 de web 
    
    public ResponseEntity<List<Alumno>> listarTodosAlumnos(){
        List<Alumno> alumnos = alumnoService.listarTodosAlumnos();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }
    
   
    // analizar las siguientes funciones 
    
    // CU : Registrar Alumno
    
    @PostMapping
    public ResponseEntity<?> registrarAlumno(@RequestBody Alumno alumno) {
        try {
            Alumno nuevoAlumno = alumnoService.registrarAlumno(alumno);
            return new ResponseEntity<>(nuevoAlumno, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Si el DNI ya existe, el servicio tira error y acá le devolvemos un mensaje a React
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // CU : Matricular Alumno
    
    @PostMapping("/{idAlumno}/matricular")
    public ResponseEntity<?> matricularAlumno(
            @PathVariable Long idAlumno,
            @RequestParam Long idCurso,
            @RequestParam Long idModulo) {
        try {
            CursadoModulo matricula = alumnoService.matricularAlumno(idAlumno, idCurso, idModulo);
            return new ResponseEntity<>(matricula, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    
    
}
