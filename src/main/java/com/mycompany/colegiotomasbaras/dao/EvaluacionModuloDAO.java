/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dao;
import com.mycompany.colegiotomasbaras.modelo.evaluacion.EvaluacionModulo;

import java.util.List;
/**
 *
 * @author Maximo Gallardo
 */
public interface EvaluacionModuloDAO {
    
    List<EvaluacionModulo> buscarPorCursada(
            Long idCursadaModulo
    );

    EvaluacionModulo guardarNueva(
            EvaluacionModulo evaluacion
    );
}
