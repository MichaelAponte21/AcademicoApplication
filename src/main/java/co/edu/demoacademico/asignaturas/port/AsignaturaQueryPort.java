package co.edu.demoacademico.asignaturas.port;

import co.edu.demoacademico.asignaturas.model.Asignatura;

/**
 * ============================================================
 * PORT (contrato público del módulo asignaturas)
 * ============================================================
 * Única puerta de entrada para que OTROS módulos consulten una asignatura.
 * Quien lo use no conoce el repositorio ni la implementación: solo
 * este contrato. Si una asignatura no existe, el módulo dueño lanza
 * NotFoundException (404).
 */
public interface AsignaturaQueryPort {

    Asignatura obtenerPorId(Long id);
}
