/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.personas;

import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(name = "alumno")
public class Alumno extends Persona {

    // Atributos propios del alumno

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(length = 120)
    private String lugarNacimiento;

    @Column
    private Boolean trabaja;

    @Column(length = 40)
    private String telefonoEmergencia;

    @Column
    private Integer cantidadHijos;

    // Asociacion con CursadaModulo

    @OneToMany(mappedBy = "alumno")
    private List<CursadoModulo> cursadas =
            new ArrayList<>();

    // Constructor

    public Alumno() {
        super();
    }

    // Getters y Setters

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(
            LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(
            String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public Boolean getTrabaja() {
        return trabaja;
    }

    public void setTrabaja(Boolean trabaja) {
        this.trabaja = trabaja;
    }

    public String getTelefonoEmergencia() {
        return telefonoEmergencia;
    }

    public void setTelefonoEmergencia(
            String telefonoEmergencia) {
        this.telefonoEmergencia = telefonoEmergencia;
    }

    public Integer getCantidadHijos() {
        return cantidadHijos;
    }

    public void setCantidadHijos(Integer cantidadHijos) {
        this.cantidadHijos = cantidadHijos;
    }

    public List<CursadoModulo> getCursadas() {
        return cursadas;
    }

    public void setCursadas(
            List<CursadoModulo> cursadas) {
        this.cursadas = cursadas;
    }

    // Atributo derivado: edad

    @Transient
    public Integer getEdad() {

        if (fechaNacimiento == null) {
            return null;
        }

        return Period.between(
                fechaNacimiento,
                LocalDate.now()
        ).getYears();
    }
}