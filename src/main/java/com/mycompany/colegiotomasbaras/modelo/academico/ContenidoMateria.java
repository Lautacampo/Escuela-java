/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.colegiotomasbaras.modelo.academico;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author Usuario
 */
@Embeddable
public class ContenidoMateria {
    
  @Column(name = "anio_curso", nullable = false)
  private Integer anioCurso;
  
  @Column(nullable = false, length = 500)
  private String descripcion;
  
  protected ContenidoMateria(){
  }
  
  public ContenidoMateria(Integer anioCurso, String descripcion) {
      this.anioCurso = anioCurso;
      this.descripcion = descripcion;
  }
  
  public Integer getAnioCurso(){
      return anioCurso;
  }
  
  public String getDescripcion(){
      return descripcion;
  }
}
