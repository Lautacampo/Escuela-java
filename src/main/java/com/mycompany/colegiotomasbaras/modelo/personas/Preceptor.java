/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.personas;
import com.mycompany.colegiotomasbaras.modelo.academico.Curso;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Maximo Gallardo
 */

@Entity
@Table(name = "preceptor")
public class Preceptor extends Personal {

    // Asociacion con curso
    @OneToMany(mappedBy = "preceptor")
    private List<Curso> cursos =new ArrayList<>();

    // Constructor

    public Preceptor() {
        super();
    }

    // Getters y Setters

    public List<Curso> getAulas() {
        return cursos;
    }

    public void setAulas(List<Curso> cursos) {
        this.cursos = cursos;
    }
}