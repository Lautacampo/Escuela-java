/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.config;

/**
 *
 * @author Maximo Gallardo
 */
public final class ConfiguracionAcademica {
    // Unica instancia de la clase.
    private static final ConfiguracionAcademica INSTANCIA =
            new ConfiguracionAcademica();

    // Reglas academicas del colegio.
    private final double notaMinimaAprobacion;
    private final int maximoInstancias;

    // Constructor privado: impide crear objetos
    
    private ConfiguracionAcademica() {

        this.notaMinimaAprobacion = 7.0;
        this.maximoInstancias = 4;
    }

    // Punto de acceso a la unica instancia.
    public static ConfiguracionAcademica getInstance() {
        return INSTANCIA;
    }

    // Consultar la nota minima de aprobacion.
    public double getNotaMinimaAprobacion() {
        return notaMinimaAprobacion;
    }

    // Consultar el maximo de instancias.
    public int getMaximoInstancias() {
        return maximoInstancias;
    }
}
