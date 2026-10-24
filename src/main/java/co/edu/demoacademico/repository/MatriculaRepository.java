package co.edu.demoacademico.repository;

import co.edu.demoacademico.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    /** Regla "no duplicado": ¿ya existe la pareja (estudiante, grupo)? */
    boolean existsByEstudianteIdAndGrupoId(Long estudianteId, Long grupoId);

    /** Regla "cupo": cuántos estudiantes tiene ya el grupo. */
    long countByGrupoId(Long grupoId);
}
