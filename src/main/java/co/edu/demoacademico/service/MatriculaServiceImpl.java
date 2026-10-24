package co.edu.demoacademico.service;

import co.edu.demoacademico.exception.BusinessException;
import co.edu.demoacademico.exception.NotFoundException;
import co.edu.demoacademico.model.Estudiante;
import co.edu.demoacademico.model.Grupo;
import co.edu.demoacademico.model.Matricula;
import co.edu.demoacademico.repository.EstudianteRepository;
import co.edu.demoacademico.repository.GrupoRepository;
import co.edu.demoacademico.repository.MatriculaRepository;
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
 * Parte 1: usa directamente los repositorios de Estudiante y Grupo.
 * En la Parte 2 se reemplazan por Ports.
 */
@Service
@Transactional
public class MatriculaServiceImpl implements MatriculaService {

    private final EstudianteRepository estudianteRepo;
    private final GrupoRepository grupoRepo;
    private final MatriculaRepository matriculaRepo;

    public MatriculaServiceImpl(EstudianteRepository estudianteRepo,
                                GrupoRepository grupoRepo,
                                MatriculaRepository matriculaRepo) {
        this.estudianteRepo = estudianteRepo;
        this.grupoRepo = grupoRepo;
        this.matriculaRepo = matriculaRepo;
    }

    @Override
    public Matricula matricular(Long estudianteId, Long grupoId) {

        // 1) existencia
        Estudiante estudiante = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado: " + estudianteId));

        Grupo grupo = grupoRepo.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo no encontrado: " + grupoId));

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
