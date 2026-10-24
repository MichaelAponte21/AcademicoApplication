package co.edu.demoacademico.asignaturas.service;

import co.edu.demoacademico.asignaturas.model.Asignatura;
import co.edu.demoacademico.asignaturas.port.AsignaturaQueryPort;
import co.edu.demoacademico.asignaturas.repository.AsignaturaRepository;
import co.edu.demoacademico.common.exception.BusinessException;
import co.edu.demoacademico.common.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Regla: el código de la asignatura es único.
 */
@Service
@Transactional
public class AsignaturaServiceImpl implements AsignaturaService, AsignaturaQueryPort {

    private final AsignaturaRepository repo;

    public AsignaturaServiceImpl(AsignaturaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Asignatura crear(Asignatura a) {
        if (repo.existsByCodigo(a.getCodigo())) {
            throw new BusinessException("Ya existe una asignatura con el código: " + a.getCodigo());
        }
        return repo.save(a);
    }

    @Override
    @Transactional(readOnly = true)
    public Asignatura obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Asignatura no encontrada: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asignatura> listar() {
        return repo.findAll();
    }

    @Override
    public void eliminar(Long id) {
        repo.delete(obtenerPorId(id));
    }
}
