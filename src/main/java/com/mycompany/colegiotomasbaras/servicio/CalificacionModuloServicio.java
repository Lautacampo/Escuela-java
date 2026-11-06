package com.mycompany.colegiotomasbaras.servicio;

import com.mycompany.colegiotomasbaras.dao.ModuloDAO;
import com.mycompany.colegiotomasbaras.dto.ModuloDTO;
import com.mycompany.colegiotomasbaras.modelo.academico.Modulo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalificacionModuloServicio {

    private final ModuloDAO moduloDAO;

    public CalificacionModuloServicio(ModuloDAO moduloDAO) {
        this.moduloDAO = moduloDAO;
    }

    @Transactional(readOnly = true)
    public List<ModuloDTO> listarModulos() {
        List<Modulo> modulosEntity = moduloDAO.obtenerTodos();
        
        // Convertimos la Entidad a DTO
        return modulosEntity.stream()
                .map(m -> new ModuloDTO(m.getId(), m.getNumeroLeyenda()))
                .collect(Collectors.toList());
    }
}