/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.colegiotomasbaras.repositorio;

import com.mycompany.colegiotomasbaras.modelo.personas.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Lautaro
 */
@Repository
public interface AlumnoRepositorio extends JpaRepository<Alumno, Long> {
    
    boolean existsByDni(String dni);// SpringData no se escriben consultas Sql, spring lee el nombre del metodo en ingles y automaticamente arma el eselect 
    //SELECT COUNT (*) FROM persona WHERE dni=?
    
    
}
