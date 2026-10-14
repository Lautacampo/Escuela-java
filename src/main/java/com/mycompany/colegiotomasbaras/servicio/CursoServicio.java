package com.mycompany.colegiotomasbaras.servicio;

import com.mycompany.colegiotomasbaras.dao.CursoDAO;
import com.mycompany.colegiotomasbaras.modelo.academico.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CursoServicio {

    @Autowired
    private CursoDAO cursoDAO;

    @Transactional(readOnly = true)
    public List<Curso> obtenerTodosconModulos() {
        return cursoDAO.obtenerTodosconModulos();
    }
}