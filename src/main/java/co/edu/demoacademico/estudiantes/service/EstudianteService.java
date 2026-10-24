package co.edu.demoacademico.estudiantes.service;

import co.edu.demoacademico.estudiantes.model.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * ============================================================
 * CAPA: LÓGICA DE NEGOCIO (contrato del Service)
 * ============================================================
 * Define QUÉ operaciones ofrece el negocio académico sobre
 * estudiantes. La implementación está en EstudianteServiceImpl.
 */
public interface EstudianteService {

    Estudiante crear(Estudiante e);

    Estudiante obtenerPorId(Long id);

    Estudiante buscarPorEmail(String email);

    Page<Estudiante> listar(Pageable pageable);

    Estudiante actualizar(Long id, Estudiante e);

    void eliminar(Long id);
}
