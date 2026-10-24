package co.edu.demoacademico.matriculas.service;

import co.edu.demoacademico.common.exception.BusinessException;
import co.edu.demoacademico.estudiantes.model.Estudiante;
import co.edu.demoacademico.estudiantes.port.EstudianteQueryPort;
import co.edu.demoacademico.grupos.model.Grupo;
import co.edu.demoacademico.grupos.port.GrupoQueryPort;
import co.edu.demoacademico.matriculas.model.Matricula;
import co.edu.demoacademico.matriculas.repository.MatriculaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ============================================================
 * CAPA: LÓGICA DE NEGOCIO — Matrículas
 * ============================================================
 * Reglas:
 *  1. El estudiante y el grupo deben existir   -> NotFoundException (404)
 *  2. No se repite la pareja (estudiante, grupo) -> BusinessException (400)
 *  3. No se supera el cupo del grupo            -> BusinessException (400)
 * <p>
 * Regla arquitectónica: este módulo NO inyecta repositorios de otros
 * módulos. Consulta estudiantes y grupos solo a través de sus Ports
 * (EstudianteQueryPort y GrupoQueryPort); la existencia la valida el
 * módulo dueño, que lanza el NotFoundException.
 */
@Service
@Transactional
public class MatriculaServiceImpl implements MatriculaService {

    private final EstudianteQueryPort estudiantePort;
    private final GrupoQueryPort grupoPort;
    private final MatriculaRepository matriculaRepo;

    public MatriculaServiceImpl(EstudianteQueryPort estudiantePort,
                                GrupoQueryPort grupoPort,
                                MatriculaRepository matriculaRepo) {
        this.estudiantePort = estudiantePort;
        this.grupoPort = grupoPort;
        this.matriculaRepo = matriculaRepo;
    }

    @Override
    public Matricula matricular(Long estudianteId, Long grupoId) {

        // 1) existencia: se valida por contrato público (404 desde el módulo dueño)
        Estudiante estudiante = estudiantePort.obtenerPorId(estudianteId);
        Grupo grupo = grupoPort.obtenerPorId(grupoId);

        // 2) no duplicado
        if (matriculaRepo.existsByEstudianteIdAndGrupoId(estudianteId, grupoId)) {
            throw new BusinessException("El estudiante ya está matriculado en ese grupo");
        }

        // 3) cupo
        long matriculados = matriculaRepo.countByGrupoId(grupoId);
        if (matriculados >= grupo.getCupoMax()) {
            throw new BusinessException("El grupo no tiene cupo disponible");
        }

        // 4) guardar
        Matricula m = new Matricula();
        m.setEstudiante(estudiante);
        m.setGrupo(grupo);
        return matriculaRepo.save(m);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Matricula> listar(Pageable pageable) {
        return matriculaRepo.findAll(pageable);
    }
}
