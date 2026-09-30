package co.edu.demoacademico.handler;

import co.edu.demoacademico.dto.EstudianteCreateDTO;
import co.edu.demoacademico.dto.EstudianteDTO;
import co.edu.demoacademico.dto.EstudianteUpdateDTO;
import co.edu.demoacademico.model.Estudiante;
import co.edu.demoacademico.service.EstudianteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * ============================================================
 * CAPA: ADAPTACIÓN (Handler) — DTO <-> Entity
 * ============================================================
 * El Controller habla en DTOs; el Service habla en entidades.
 * Este componente hace la conversión entre ambos mundos.
 */
@Component
public class EstudianteHandler {

    private final EstudianteService service;

    public EstudianteHandler(EstudianteService service) {
        this.service = service;
    }

    public EstudianteDTO crear(EstudianteCreateDTO in) {
        Estudiante entity = new Estudiante();
        entity.setNombre(in.getNombre());
        entity.setEmail(in.getEmail());

        return toDto(service.crear(entity));
    }

    public EstudianteDTO obtener(Long id) {
        return toDto(service.obtenerPorId(id));
    }

    public EstudianteDTO buscarPorEmail(String email) {
        return toDto(service.buscarPorEmail(email));
    }

    public Page<EstudianteDTO> listar(Pageable pageable) {
        return service.listar(pageable).map(this::toDto);
    }

    public EstudianteDTO actualizar(Long id, EstudianteUpdateDTO in) {
        Estudiante entity = new Estudiante();
        entity.setNombre(in.getNombre());
        entity.setEmail(in.getEmail());

        return toDto(service.actualizar(id, entity));
    }

    public void eliminar(Long id) {
        service.eliminar(id);
    }

    private EstudianteDTO toDto(Estudiante e) {
        EstudianteDTO dto = new EstudianteDTO();
        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setEmail(e.getEmail());
        return dto;
    }
}
