package co.edu.demoacademico.matriculas.handler;

import co.edu.demoacademico.matriculas.dto.MatriculaCreateDTO;
import co.edu.demoacademico.matriculas.dto.MatriculaDTO;
import co.edu.demoacademico.matriculas.model.Matricula;
import co.edu.demoacademico.matriculas.service.MatriculaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class MatriculaHandler {

    private final MatriculaService service;

    public MatriculaHandler(MatriculaService service) {
        this.service = service;
    }

    public MatriculaDTO matricular(MatriculaCreateDTO in) {
        return toDto(service.matricular(in.getEstudianteId(), in.getGrupoId()));
    }

    public Page<MatriculaDTO> listar(Pageable pageable) {
        return service.listar(pageable).map(this::toDto);
    }

    private MatriculaDTO toDto(Matricula m) {
        MatriculaDTO dto = new MatriculaDTO();
        dto.setId(m.getId());
        dto.setEstudianteId(m.getEstudiante().getId());
        dto.setEstudianteNombre(m.getEstudiante().getNombre());
        dto.setGrupoId(m.getGrupo().getId());
        dto.setCodigoGrupo(m.getGrupo().getCodigoGrupo());
        dto.setFechaRegistro(m.getFechaRegistro());
        return dto;
    }
}
