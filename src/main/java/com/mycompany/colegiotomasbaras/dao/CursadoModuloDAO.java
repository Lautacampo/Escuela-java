/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dao;

import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;

import java.util.List;
import java.util.Optional;
/**
 *
 * @author Maximo Gallardo
 */
public interface CursadoModuloDAO {
    
    Optional<CursadoModulo> buscarParaCalificar(Long id);

    // Buscar las cursadas historicas de un alumno.
    List<CursadoModulo> buscarPorAlumno(Long idAlumno);

    // Verificar si ya existe la matricula.
    boolean existeMatricula(
            Long idAlumno,
            Long idAula,
            Long idModulo
    );

    // Registrar una nueva cursada.
    CursadoModulo guardarNueva(CursadoModulo cursada);

    // Actualizar una cursada existente.
    CursadoModulo actualizar(CursadoModulo cursada);
}
