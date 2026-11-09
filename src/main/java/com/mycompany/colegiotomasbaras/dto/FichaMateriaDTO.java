/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dto;

import java.util.List;

/**
 *
 * @author Usuario
 */
public record FichaMateriaDTO (
        Long id,
        String nombre,
        List<String> contenidos,
        List<ModuloMateria> modulos
) {
   public record ModuloMateria (
           Long idModulo,
           String nombre,
           List<DivisionMateria> divisiones
    ) {}
   
   public record DivisionMateria (
           Integer anioCurso,
           String division,
           Integer cicloLectivo,
           List<DocenteMateria> docentes
    ) {}
   
   public record DocenteMateria (
           Long id,
           String nombre,
           String apellido
    ) {}
}
