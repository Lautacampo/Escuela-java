/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dto;
import java.util.List;
/**
 *
 * @author Maximo Gallardo
 */
public record ResultadoMatricula(
        boolean registrada,
        Long idCursadaModulo,
        boolean requiereDecisionDirectivo,
        List<String> advertencias
) {
}