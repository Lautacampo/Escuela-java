/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.academico;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(name = "ciclo_lectivo")
public class CicloLectivo {

 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer anio;

   

    @OneToMany(mappedBy = "cicloLectivo")
    private List<Curso> cursos = new ArrayList<>();


    public CicloLectivo() {
    }


    public Long getId() {
        return id;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }
    }