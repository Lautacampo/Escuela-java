/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.academico;
import com.mycompany.colegiotomasbaras.modelo.personas.Alumno;
import com.mycompany.colegiotomasbaras.modelo.asistencia.Asistencia;
import com.mycompany.colegiotomasbaras.modelo.evaluacion.EvaluacionModulo;
import com.mycompany.colegiotomasbaras.modelo.evaluacion.Calificacion;
import com.mycompany.colegiotomasbaras.modelo.enums.CondicionAsistencia;
import com.mycompany.colegiotomasbaras.modelo.enums.EstadoAcademico;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(name = "cursada_modulo")
public class CursadoModulo {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos del Modelo de Dominio

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CondicionAsistencia condicion =
            CondicionAsistencia.REGULAR;

    @Column(name = "cantidad_faltas_acumuladas",
            nullable = false)
    private Double cantFaltasAcumuladas = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_academico",
            nullable = false, length = 20)
    private EstadoAcademico estadoAcademico =
            EstadoAcademico.EN_CURSO;

    // Asociacion con Alumno
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    // Asociacion con Curso
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    // Asociacion con Modulo

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "modulo_id", nullable = false)
    private Modulo modulo;

    // Composicion con Asistencia
    @JsonIgnore
    @OneToMany(
        mappedBy = "cursadaModulo",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<Asistencia> asistencias = new ArrayList<>();

    // Composicion con EvaluacionModulo
    @JsonIgnore
    @OneToMany(
        mappedBy = "cursadaModulo",
        cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<EvaluacionModulo> evaluaciones =new ArrayList<>();

    // Asociacion con Calificacion
    @JsonIgnore
    @OneToMany(mappedBy = "cursadaModulo")
    private List<Calificacion> calificaciones =new ArrayList<>();


    public CursadoModulo() {
    }



    public Long getId() {
        return id;
    }

    public CondicionAsistencia getCondicion() {
        return condicion;
    }

    public void setCondicion(
            CondicionAsistencia condicion) {
        this.condicion = condicion;
    }

    public Double getCantFaltasAcumuladas() {
        return cantFaltasAcumuladas;
    }

    public void setCantFaltasAcumuladas(
            Double cantFaltasAcumuladas) {
        this.cantFaltasAcumuladas = cantFaltasAcumuladas;
    }

    public EstadoAcademico getEstadoAcademico() {
        return estadoAcademico;
    }

    public void setEstadoAcademico(
            EstadoAcademico estadoAcademico) {
        this.estadoAcademico = estadoAcademico;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(
            List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    public List<EvaluacionModulo> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(
            List<EvaluacionModulo> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(
            List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }
}
