/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.evaluacion;
import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import com.mycompany.colegiotomasbaras.modelo.enums.TipoInstancia;
import jakarta.persistence.*;
import java.time.LocalDate;
/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(
    name = "evaluacion_modulo",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_evaluacion_cursada_instancia",
            columnNames = {
                "cursada_modulo_id",
                "nro_instancia"
            }
        )
    }
)
public class EvaluacionModulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Double nota;

    @Column(name = "nro_instancia", nullable = false)
    private Integer nroInstancia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_instancia",
            nullable = false, length = 20)
    private TipoInstancia tipoInstancia;

    // Asociacion con CursadaModulo
  
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "cursada_modulo_id",
        nullable = false
    )
    private CursadoModulo cursadaModulo;
    
    public EvaluacionModulo() {
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

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Integer getNroInstancia() {
        return nroInstancia;
    }

    public void setNroInstancia(Integer nroInstancia) {
        this.nroInstancia = nroInstancia;
    }

    public TipoInstancia getTipoInstancia() {
        return tipoInstancia;
    }

    public void setTipoInstancia(
            TipoInstancia tipoInstancia) {
        this.tipoInstancia = tipoInstancia;
    }

    public CursadoModulo getCursadaModulo() {
        return cursadaModulo;
    }

    public void setCursadaModulo(
            CursadoModulo cursadaModulo) {
        this.cursadaModulo = cursadaModulo;
    }
}
