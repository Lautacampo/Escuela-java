
package com.mycompany.colegiotomasbaras.dto;

import com.mycompany.colegiotomasbaras.modelo.enums.TipoInstancia;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CalificarModuloSolicitud(

        @NotNull
        Long idCursadaModulo,

        @NotNull
        LocalDate fecha,

        @NotNull
        Double nota,

        @NotNull
        @Min(1)
        @Max(4)
        Integer nroInstancia,

        @NotNull
        TipoInstancia tipoInstancia

) {
}
