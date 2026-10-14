/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.controlador;
import com.mycompany.colegiotomasbaras.modelo.academico.Curso;
import com.mycompany.colegiotomasbaras.servicio.CursoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 *
 * @author Lautaro
 */

@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoControlador {
    @Autowired
    private CursoServicio cursoService;

    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.obtenerTodosconModulos();
    }
}
