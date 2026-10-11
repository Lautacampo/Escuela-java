
package com.mycompany.colegiotomasbaras.repositorio;

import com.mycompany.colegiotomasbaras.modelo.personas.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepositorio
        extends JpaRepository<Alumno, Long> {

    boolean existsByDni(String dni);

}
