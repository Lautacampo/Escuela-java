/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dao;
import com.mycompany.colegiotomasbaras.modelo.personas.Alumno;

import java.util.List;
import java.util.Optional;
/**
 *
 * @author Maximo Gallardo
 */
public interface AlumnoDAO {
       
    Alumno guardar(Alumno alumno);

    Optional<Alumno> buscarPorId(Long id);

    List<Alumno> listarTodos();

    boolean existePorDni(String dni);
    
    List<Alumno> obtenerTodos();
    
    

}
