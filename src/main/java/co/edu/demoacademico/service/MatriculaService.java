package co.edu.demoacademico.service;

import co.edu.demoacademico.model.Matricula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MatriculaService {

    Matricula matricular(Long estudianteId, Long grupoId);

    Page<Matricula> listar(Pageable pageable);
}
