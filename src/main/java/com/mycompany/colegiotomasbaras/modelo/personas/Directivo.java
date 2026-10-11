/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.personas;
import jakarta.persistence.*;

/**
 *
 * @author Maximo Gallardo
 */
@Entity
@Table(name = "directivo")
public class Directivo extends Personal {

    // Constructor

    public Directivo() {
        super();
    }
}
