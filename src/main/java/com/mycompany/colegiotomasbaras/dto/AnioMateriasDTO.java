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
public record AnioMateriasDTO (
        Integer anio,
        List<MateriaResumen> materias
) {
    public record MateriaResumen(
            Long id,
            String nombre
    ) {}
}
