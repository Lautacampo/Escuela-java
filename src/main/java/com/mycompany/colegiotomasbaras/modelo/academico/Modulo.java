/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.academico;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(name = "modulo")
public class Modulo {
    
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String numeroLeyenda;

    // Asociacion con Materia
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "materia_modulo",
        joinColumns =
            @JoinColumn(name = "modulo_id"),
        inverseJoinColumns =
            @JoinColumn(name = "materia_id")
    )
    private List<Materia> materias = new ArrayList<>();

    // Asociacion con Curso

    @JsonIgnore // evita bucle
    @ManyToMany(mappedBy = "modulos")
    private List<Curso> cursos = new ArrayList<>();

    // Asociacion con CursadaModulo
    @JsonIgnore
    @OneToMany(mappedBy = "modulo")
    private List<CursadoModulo> cursadas = new ArrayList<>();

    // Autoasociacion: correlatividades
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "modulo_correlativo",
        joinColumns =
            @JoinColumn(name = "modulo_id"),
        inverseJoinColumns =
            @JoinColumn(name = "modulo_correlativo_id")
    )
    private List<Modulo> prerrequisitos =
            new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "prerrequisitos")
    private List<Modulo> modulosPosteriores =new ArrayList<>();

 

    public Modulo() {
    }


    public Long getId() {
        return id;
    }

    public String getNumeroLeyenda() {
        return numeroLeyenda;
    }

    public void setNumeroLeyenda(String numeroLeyenda) {
        this.numeroLeyenda = numeroLeyenda;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<CursadoModulo> getCursadas() {
        return cursadas;
    }

    public void setCursadas(List<CursadoModulo> cursadas) {
        this.cursadas = cursadas;
    }

    public List<Modulo> getPrerrequisitos() {
        return prerrequisitos;
    }

    public void setPrerrequisitos(
            List<Modulo> prerrequisitos) {
        this.prerrequisitos = prerrequisitos;
    }

    public List<Modulo> getModulosPosteriores() {
        return modulosPosteriores;
    }

    public void setModulosPosteriores(
            List<Modulo> modulosPosteriores) {
        this.modulosPosteriores = modulosPosteriores;
    }
}
