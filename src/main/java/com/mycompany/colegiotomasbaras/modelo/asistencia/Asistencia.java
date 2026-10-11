/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.asistencia;

import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(name = "asistencia")
public class Asistencia {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "valor_falta", nullable = false)
    private Double valorFalta;

    // Asociacion con CursadaModulo
   

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cursada_modulo_id", nullable = false)
    private CursadoModulo cursadaModulo;


    public Asistencia() {
    }


    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getValorFalta() {
        return valorFalta;
    }

    public void setValorFalta(Double valorFalta) {
        this.valorFalta = valorFalta;
    }

    public CursadoModulo getCursadoModulo() {
        return cursadaModulo;
    }

    public void setCursadoModulo(
            CursadoModulo cursadaModulo) {
        this.cursadaModulo = cursadaModulo;
    }
}
