package co.edu.demoacademico.asignaturas.handler;

import co.edu.demoacademico.asignaturas.dto.AsignaturaCreateDTO;
import co.edu.demoacademico.asignaturas.dto.AsignaturaDTO;
import co.edu.demoacademico.asignaturas.model.Asignatura;
import co.edu.demoacademico.asignaturas.service.AsignaturaService;
import co.edu.demoacademico.programas.model.Programa;
import co.edu.demoacademico.programas.port.ProgramaQueryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AsignaturaHandler {

    private final AsignaturaService asignaturaService;
    private final ProgramaQueryPort programaPort;

    public AsignaturaHandler(AsignaturaService asignaturaService, ProgramaQueryPort programaPort) {
        this.asignaturaService = asignaturaService;
        this.programaPort = programaPort;
    }

    public AsignaturaDTO crear(AsignaturaCreateDTO in) {
        Programa programa = programaPort.obtenerPorId(in.getProgramaId());

        Asignatura a = new Asignatura();
        a.setCodigo(in.getCodigo());
        a.setNombre(in.getNombre());
        a.setCreditos(in.getCreditos());
        a.setPrograma(programa);

        return toDto(asignaturaService.crear(a));
    }

    public List<AsignaturaDTO> listar() {
        return asignaturaService.listar().stream().map(this::toDto).toList();
    }

    public void eliminar(Long id) {
        asignaturaService.eliminar(id);
    }

    private AsignaturaDTO toDto(Asignatura a) {
        AsignaturaDTO dto = new AsignaturaDTO();
        dto.setId(a.getId());
        dto.setCodigo(a.getCodigo());
        dto.setNombre(a.getNombre());
        dto.setCreditos(a.getCreditos());
        dto.setProgramaId(a.getPrograma().getId());
        dto.setProgramaNombre(a.getPrograma().getNombre());
        return dto;
    }
}
