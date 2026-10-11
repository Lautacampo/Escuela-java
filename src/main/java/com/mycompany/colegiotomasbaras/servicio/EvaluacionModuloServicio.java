
package com.mycompany.colegiotomasbaras.servicio;

import com.mycompany.colegiotomasbaras.config.ConfiguracionAcademica;

import com.mycompany.colegiotomasbaras.dao.CursadoModuloDAO;
import com.mycompany.colegiotomasbaras.dao.EvaluacionModuloDAO;

import com.mycompany.colegiotomasbaras.dto.CalificarModuloSolicitud;
import com.mycompany.colegiotomasbaras.dto.CalificarModuloRespuesta;

import com.mycompany.colegiotomasbaras.modelo.academico.CursadoModulo;
import com.mycompany.colegiotomasbaras.modelo.evaluacion.EvaluacionModulo;

import com.mycompany.colegiotomasbaras.modelo.enums.EstadoAcademico;
import com.mycompany.colegiotomasbaras.modelo.enums.TipoInstancia;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EvaluacionModuloServicio {

    // Interfaces del patron DAO

    private final CursadoModuloDAO cursadaDAO;
    private final EvaluacionModuloDAO evaluacionDAO;

    // Patron Singleton

    private final ConfiguracionAcademica configuracion;

    // Constructor

    public EvaluacionModuloServicio(
            CursadoModuloDAO cursadaDAO,
            EvaluacionModuloDAO evaluacionDAO) {

        this.cursadaDAO = cursadaDAO;
        this.evaluacionDAO = evaluacionDAO;

        this.configuracion =
                ConfiguracionAcademica.getInstance();
    }

    // CASO DE USO: CALIFICAR MODULO

    @Transactional
    public CalificarModuloRespuesta calificarModulo(
            CalificarModuloSolicitud solicitud) {

        // 1. Buscar la cursada mediante DAO.

        CursadoModulo cursada =
                cursadaDAO.buscarParaCalificar(
                        solicitud.idCursadaModulo()
                ).orElseThrow(() ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cursada no encontrada"
                    )
                );

        // 2. Verificar el estado academico.

        if (cursada.getEstadoAcademico()
                == EstadoAcademico.APROBADO) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El modulo ya esta aprobado"
            );
        }

        if (cursada.getEstadoAcademico()
                == EstadoAcademico.A_RECURSAR) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El modulo debe ser recursado"
            );
        }

        // 3. Consultar evaluaciones anteriores.

        List<EvaluacionModulo> evaluaciones =
                evaluacionDAO.buscarPorCursada(
                        cursada.getId()
                );

        // 4. Consultar el patron Singleton.

        int maximoInstancias =
                configuracion.getMaximoInstancias();

        double notaMinima =
                configuracion.getNotaMinimaAprobacion();

        // 5. Determinar siguiente instancia.

        int siguienteInstancia = evaluaciones.size() + 1;

        if (siguienteInstancia > maximoInstancias) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Se agotaron las instancias"
            );
        }

        if (solicitud.nroInstancia() != siguienteInstancia) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La siguiente instancia es "
                    + siguienteInstancia
            );
        }

        // 6. Determinar el tipo de instancia.

        TipoInstancia tipoEsperado;

        if (siguienteInstancia == 1) {
            tipoEsperado = TipoInstancia.COLEGIADA;

        } else if (siguienteInstancia == maximoInstancias) {
            tipoEsperado = TipoInstancia.ESPECIAL;

        } else {
            tipoEsperado = TipoInstancia.CALENDARIO;
        }

        if (solicitud.tipoInstancia() != tipoEsperado) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tipo de instancia incorrecto"
            );
        }

        // 7. Validar la nota numerica.

        if (solicitud.nota() == null
                || !Double.isFinite(solicitud.nota())
                || solicitud.nota() < 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La nota no es valida"
            );
        }

        // 8. Crear evaluacion.

        EvaluacionModulo evaluacion =
                new EvaluacionModulo();

        evaluacion.setCursadaModulo(cursada);
        evaluacion.setFecha(solicitud.fecha());
        evaluacion.setNota(solicitud.nota());
        evaluacion.setNroInstancia(
                solicitud.nroInstancia()
        );
        evaluacion.setTipoInstancia(
                solicitud.tipoInstancia()
        );

        // 9. Guardar la evaluacion mediante DAO.

        EvaluacionModulo guardada =
                evaluacionDAO.guardarNueva(evaluacion);

        // 10. Determinar el estado academico.

        EstadoAcademico nuevoEstado;

        if (solicitud.nota() >= notaMinima) {

            nuevoEstado = EstadoAcademico.APROBADO;

        } else if (siguienteInstancia == maximoInstancias) {

            nuevoEstado = EstadoAcademico.A_RECURSAR;

        } else {

            nuevoEstado = EstadoAcademico.PENDIENTE;
        }

        // 11. Actualizar cursada mediante DAO.

        cursada.setEstadoAcademico(nuevoEstado);

        cursadaDAO.actualizar(cursada);

        // 12. Responder al controlador.

        return new CalificarModuloRespuesta(
                guardada.getId(),
                cursada.getId(),
                guardada.getNroInstancia(),
                guardada.getNota(),
                nuevoEstado
        );
    }
}
