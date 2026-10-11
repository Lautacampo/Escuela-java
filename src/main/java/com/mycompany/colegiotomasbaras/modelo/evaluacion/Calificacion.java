/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.evaluacion;
import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import com.mycompany.colegiotomasbaras.modelo.academico.Materia;
import jakarta.persistence.*;



/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(
    name = "calificacion",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_calificacion_cursada_materia",
            columnNames = {
                "cursada_modulo_id",
                "materia_id"
            }
        )
    }
)
public class Calificacion {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributo del Modelo de Dominio
    @Column(length = 40)
    private String estado;
    
    // Asociacion con CursadaModulo

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "cursada_modulo_id",
        nullable = false
    )
    private CursadoModulo cursadaModulo;

    // Asociacion con Materia
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "materia_id",
        nullable = false
    )
    private Materia materia;

    

    public Calificacion() {
    }


    public Long getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CursadoModulo getCursadaModulo() {
        return cursadaModulo;
    }

    public void setCursadaModulo(
            CursadoModulo cursadaModulo) {
        this.cursadaModulo = cursadaModulo;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
}
