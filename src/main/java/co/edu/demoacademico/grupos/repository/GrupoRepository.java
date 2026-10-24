package co.edu.demoacademico.grupos.repository;

import co.edu.demoacademico.grupos.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    boolean existsByCodigoGrupo(String codigoGrupo);
}
