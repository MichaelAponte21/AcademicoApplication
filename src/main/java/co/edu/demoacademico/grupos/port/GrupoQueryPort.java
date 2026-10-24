package co.edu.demoacademico.grupos.port;

import co.edu.demoacademico.grupos.model.Grupo;

/**
 * ============================================================
 * PORT (contrato público del módulo grupos)
 * ============================================================
 * Única puerta de entrada para que OTROS módulos consulten un grupo.
 * Quien lo use no conoce el repositorio ni la implementación: solo
 * este contrato. Si un grupo no existe, el módulo dueño lanza
 * NotFoundException (404).
 */
public interface GrupoQueryPort {

    Grupo obtenerPorId(Long id);
}
