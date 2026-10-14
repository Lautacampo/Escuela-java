/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.academico;
import com.mycompany.colegiotomasbaras.modelo.personas.Docente;
import com.mycompany.colegiotomasbaras.modelo.personas.Preceptor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(name = "curso")
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String division;

    @Column(name = "anio_curso", nullable = false)
    private Integer anioCurso;

    // Asociacion con CicloLectivo

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ciclo_lectivo_id", nullable = false)
    private CicloLectivo cicloLectivo;

    // Asociacion con Preceptor

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "preceptor_id", nullable = false)
    private Preceptor preceptor;

    // Asociacion con Docente

    @ManyToMany(mappedBy = "cursos")
    private List<Docente> docentes = new ArrayList<>();

    // Asociacion con Modulo

    @ManyToMany
    @JoinTable(
        name = "aula_modulo",
        joinColumns = @JoinColumn(name = "aula_id"),
        inverseJoinColumns = @JoinColumn(name = "modulo_id")
    )
    private List<Modulo> modulos = new ArrayList<>();

    // Asociacion con CursadaModulo
  

    @OneToMany(mappedBy = "curso")
    private List<CursadoModulo> cursadas = new ArrayList<>();

    

    public Curso() {
    }


    public Long getId() {
        return id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Integer getAnioCurso() {
        return anioCurso;
    }

    public void setAnioCurso(Integer anioCurso) {
        this.anioCurso = anioCurso;
    }

    public CicloLectivo getCicloLectivo() {
        return cicloLectivo;
    }

    public void setCicloLectivo(CicloLectivo cicloLectivo) {
        this.cicloLectivo = cicloLectivo;
    }

    public Preceptor getPreceptor() {
        return preceptor;
    }

    public void setPreceptor(Preceptor preceptor) {
        this.preceptor = preceptor;
    }

    public List<Docente> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Docente> docentes) {
        this.docentes = docentes;
    }

    public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public List<CursadoModulo> getCursadas() {
        return cursadas;
    }

    public void setCursadas(List<CursadoModulo> cursadas) {
        this.cursadas = cursadas;
    }

  
}
