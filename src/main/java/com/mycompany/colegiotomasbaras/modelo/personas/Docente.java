/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.personas;
import com.mycompany.colegiotomasbaras.modelo.academico.Curso;
import com.mycompany.colegiotomasbaras.modelo.academico.Materia;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(name = "docente")
public class Docente extends Personal {

    // Asociacion con Materia

    @ManyToMany
    @JoinTable(
        name = "docente_materia",
        joinColumns =
            @JoinColumn(name = "docente_id"),
        inverseJoinColumns =
            @JoinColumn(name = "materia_id")
    )
    private List<Materia> materias =
            new ArrayList<>();

    // Asociacion con Aula

    @ManyToMany
    @JoinTable(
        name = "docente_aula",
        joinColumns =
            @JoinColumn(name = "docente_id"),
        inverseJoinColumns =
            @JoinColumn(name = "aula_id")
    )
    private List<Curso> cursos = new ArrayList<>();


    public Docente() {
        super();
    }


    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public List<Curso> getAulas() {
        return cursos;
    }

    public void setAulas(List<Curso> cursos) {
        this.cursos = cursos;
    }
}