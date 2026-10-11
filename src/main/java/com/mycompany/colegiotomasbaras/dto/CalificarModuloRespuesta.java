/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dto;
import com.mycompany.colegiotomasbaras.modelo.enums.EstadoAcademico;
import com.mycompany.colegiotomasbaras.modelo.enums.TipoInstancia;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
/**
 *
 * @author Maximo Gallardo
 */
public record CalificarModuloRespuesta(

        Long idEvaluacion,
        Long idCursadaModulo,
        Integer nroInstancia,
        Double nota,
        EstadoAcademico estadoAcademico

) {
}