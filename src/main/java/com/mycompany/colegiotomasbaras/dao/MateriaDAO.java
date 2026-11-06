package com.mycompany.colegiotomasbaras.dao;

import com.mycompany.colegiotomasbaras.modelo.academico.Materia;
import java.util.List;
import java.util.Optional;

public interface MateriaDAO {
    Optional<Materia> buscarPorId(Long id);
    List<Materia> listarTodas();
}