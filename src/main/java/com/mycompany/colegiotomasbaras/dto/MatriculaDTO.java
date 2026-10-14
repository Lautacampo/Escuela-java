/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.dto;

/**
 *
 * @author Lautaro
 */
public class MatriculaDTO {
    private Long idCurso;
    private Long idModulo;
    private boolean autorizarConAdvertencias;

    public MatriculaDTO() {
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }

    public boolean isAutorizarConAdvertencias() {
        return autorizarConAdvertencias;
    }

    public void setAutorizarConAdvertencias(boolean autorizarConAdvertencias) {
        this.autorizarConAdvertencias = autorizarConAdvertencias;
    }
}