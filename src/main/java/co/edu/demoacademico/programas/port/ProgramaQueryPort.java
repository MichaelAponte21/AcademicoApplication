package co.edu.demoacademico.programas.port;

import co.edu.demoacademico.programas.model.Programa;

/**
 * ============================================================
 * PORT (contrato público del módulo programas)
 * ============================================================
 * Única puerta de entrada para que OTROS módulos consulten un programa.
 * Quien lo use no conoce el repositorio ni la implementación: solo
 * este contrato. Si un programa no existe, el módulo dueño lanza
 * NotFoundException (404).
 */
public interface ProgramaQueryPort {

    Programa obtenerPorId(Long id);
}
