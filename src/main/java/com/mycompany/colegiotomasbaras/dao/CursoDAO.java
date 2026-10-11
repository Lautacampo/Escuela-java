/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dao;

import com.mycompany.colegiotomasbaras.modelo.academico.Curso;
import java.util.Optional;

/**
 *
 * @author Maximo Gallardo
 */
public interface CursoDAO {
    Optional<Curso> buscarPorId(Long id);
}
