package co.edu.demoacademico.estudiantes.port;

import co.edu.demoacademico.estudiantes.model.Estudiante;

/**
 * ============================================================
 * PORT (contrato público del módulo estudiantes)
 * ============================================================
 * Única puerta de entrada para que OTROS módulos consulten un estudiante.
 * Quien lo use no conoce el repositorio ni la implementación: solo
 * este contrato. Si un estudiante no existe, el módulo dueño lanza
 * NotFoundException (404).
 */
public interface EstudianteQueryPort {

    Estudiante obtenerPorId(Long id);
}
