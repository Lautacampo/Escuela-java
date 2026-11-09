/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.academico;
import com.mycompany.colegiotomasbaras.modelo.personas.Docente;
import com.mycompany.colegiotomasbaras.modelo.evaluacion.Calificacion;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(name = "materia")
public class Materia {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;
    
    // Contenidos de la materia
    @ElementCollection
    @CollectionTable(
        name = "materia_contenido",
        joinColumns = @JoinColumn(name = "materia_id")
    )
    @OrderColumn(name = "orden")
    @Column(name = "contenido", nullable = false, length = 200)
    private List<String> contenidos = new ArrayList<>();
    
    // Asociacion con Modulo

    @ManyToMany(mappedBy = "materias")
    private List<Modulo> modulos = new ArrayList<>();

    // Asociacion con Docente

    @ManyToMany(mappedBy = "materias")
    private List<Docente> docentes = new ArrayList<>();

// Asociacion con Calificacion

 @OneToMany(mappedBy = "materia")
private List<Calificacion> calificaciones = new ArrayList<>();



public List<Calificacion> getCalificaciones() {
    return calificaciones;
}


public void setCalificaciones(
        List<Calificacion> calificaciones) {
    this.calificaciones = calificaciones;
}

    public Materia() {
    }


    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public List<String> getContenidos() {
        return contenidos;
    }
    
    public void setContenidos(List<String> contenidos) {
        this.contenidos = contenidos;
    }

    public List<Modulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public List<Docente> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Docente> docentes) {
        this.docentes = docentes;
    }
}
