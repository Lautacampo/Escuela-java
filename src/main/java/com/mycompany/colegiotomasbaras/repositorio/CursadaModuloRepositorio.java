
package com.mycompany.colegiotomasbaras.repositorio;

import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Repository
public interface CursadaModuloRepositorio
        extends JpaRepository<CursadoModulo, Long> {

    // Comprueba una matricula existente
    boolean existsByAlumno_IdAndAula_IdAndModulo_Id(
            Long idAlumno,
            Long idAula,
            Long idModulo
    );

    // Recupera la trayectoria del alumno
    List<CursadoModulo> findByAlumno_Id(Long idAlumno);
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT c FROM CursadaModulo c WHERE c.id = :id")
Optional<CursadoModulo> buscarParaCalificar(
        @Param("id") Long id
);
}
