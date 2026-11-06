/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.evaluacion;

import com.mycompany.colegiotomasbaras.modelo.academico.Materia;
import com.mycompany.colegiotomasbaras.modelo.enums.EstadoMateria;
import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import com.mycompany.colegiotomasbaras.modelo.personas.Docente;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 *
 * @author Lautaro
 */

@Entity
@Table(name="calificacion_materia")

public class CalificacionMateria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cursada_modulo_id", nullable = false)
    private CursadoModulo cursadaModulo;

    @Column(name = "estado", length = 15)
    private String estado; // "APROBADO", "DESAPROBADO" o null

    
    
    public CalificacionMateria(){}
    
    public CalificacionMateria( Materia materia, CursadoModulo cursadaModulo, String estado){
        this.materia=materia;
        this.cursadaModulo=cursadaModulo;
        this.estado=estado;
    }
    
    
    public Long getId(){return id;}
    public Materia getMateria(){return materia;}
    public void setMateria(Materia materia) { this.materia = materia; }
    public CursadoModulo getCursadaModulo() { return cursadaModulo; }
    public void setCursadaModulo(CursadoModulo cursadaModulo) { this.cursadaModulo = cursadaModulo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    







}
