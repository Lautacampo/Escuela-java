/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.colegiotomasbaras.repositorio;
import com.mycompany.colegiotomasbaras.modelo.evaluacion.EvaluacionModulo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Maximo Gallardo
 */
@Repository
public interface EvaluacionModuloRepositorio
        extends JpaRepository<EvaluacionModulo, Long> {

    // Recupera las evaluaciones de una cursada,
    // ordenadas por numero de instancia.

    List<EvaluacionModulo>
            findByCursadaModulo_IdOrderByNroInstanciaAsc(
                    Long idCursadaModulo
            );
}