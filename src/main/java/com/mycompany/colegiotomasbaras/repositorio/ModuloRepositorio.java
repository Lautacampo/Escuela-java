
package com.mycompany.colegiotomasbaras.repositorio;

import com.mycompany.colegiotomasbaras.modelo.academico.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloRepositorio
        extends JpaRepository<Modulo, Long> {

}
