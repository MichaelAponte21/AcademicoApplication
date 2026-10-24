package co.edu.demoacademico.programas.repository;

import co.edu.demoacademico.programas.model.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Long> {

    boolean existsByCodigo(String codigo);
}
