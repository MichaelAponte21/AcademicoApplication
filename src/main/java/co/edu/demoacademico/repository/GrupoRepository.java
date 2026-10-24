package co.edu.demoacademico.repository;

import co.edu.demoacademico.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    boolean existsByCodigoGrupo(String codigoGrupo);
}
