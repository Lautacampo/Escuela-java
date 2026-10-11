/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.colegiotomasbaras.repositorio;
import com.mycompany.colegiotomasbaras.modelo.personas.CursadoModulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author Lautaro
 */
@Repository
public interface CursadaModuloRepositorio extends JpaRepository<Alumno, Long> {
    
}
