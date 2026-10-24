package co.edu.demoacademico.repository;

import co.edu.demoacademico.model.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

    boolean existsByCodigo(String codigo);
}
