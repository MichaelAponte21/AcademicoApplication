package co.edu.demoacademico.estudiantes.repository;

import co.edu.demoacademico.estudiantes.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ============================================================
 * CAPA: ACCESO A DATOS (Repository) — ZONA BD
 * ============================================================
 * Responsabilidad: hablar con la base de datos. Es la ÚNICA
 * capa autorizada a tocar la BD. Spring Data genera la
 * implementación a partir del nombre de los métodos.
 * <p>
 * NO debe: contener reglas de negocio ni conocer HTTP.
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByEmail(String email);

    boolean existsByEmail(String email);

    /** Email usado por OTRO estudiante (distinto id): para validar en actualizaciones. */
    boolean existsByEmailAndIdNot(String email, Long id);
}
