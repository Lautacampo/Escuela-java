/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dto;

/**
 *
 * @author Lautaro
 */
public class ModuloDTO {
    private Long id;
    private String numeroLeyenda;
    
    
    public ModuloDTO(){}
    
    public ModuloDTO(Long id, String nombre) {
        this.id = id;
        this.numeroLeyenda = nombre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return numeroLeyenda; }
    public void setNombre(String nombre) { this.numeroLeyenda = nombre; }
}
    
