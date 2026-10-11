/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.personas;
import com.mycompany.colegiotomasbaras.modelo.asistencia.InasistenciaPersonal;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(name = "personal")
public abstract class Personal extends Persona {


    @Column(length = 150)
    private String titulo;

    @Column
    private Integer antiguedad;

  
    @OneToMany(mappedBy = "personal")
    private List<InasistenciaPersonal> inasistencias =
            new ArrayList<>();

  

    protected Personal() {
        super();
    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer antiguedad) {
        this.antiguedad = antiguedad;
    }

    public List<InasistenciaPersonal> getInasistencias() {
        return inasistencias;
    }

    public void setInasistencias(
            List<InasistenciaPersonal> inasistencias) {
        this.inasistencias = inasistencias;
    }
}