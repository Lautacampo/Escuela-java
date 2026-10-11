
package com.mycompany.colegiotomasbaras.repositorio;

import com.mycompany.colegiotomasbaras.modelo.academico.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepositorio
        extends JpaRepository<Curso, Long> {

}
