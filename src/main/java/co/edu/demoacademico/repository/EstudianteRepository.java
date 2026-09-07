package co.edu.demoacademico.repository;

import co.edu.demoacademico.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ============================================================
 * CAPA: ACCESO A DATOS (Repository) — ZONA BD
 * ============================================================
 * Responsabilidad: hablar con la base de datos. Es la ÚNICA
 * capa autorizada a tocar la BD.
 *
 *  - Al extender JpaRepository hereda gratis save(), findAll(),
 *    findById(), deleteById(), etc.
 *  - Spring Data genera la implementación en tiempo de ejecución:
 *    esta interfaz no tiene clase concreta escrita a mano.
 *  - Los "query methods" (findByEmail) se traducen a SQL a partir
 *    del nombre del método, sin escribir la consulta.
 *
 * NO debe: contener reglas de negocio ni conocer HTTP.
 *
 * Entidad gestionada: Estudiante (clave primaria de tipo Long).
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    // ============================
    // ZONA DE ACCESO A LA BD (JPA)
    // ============================
    // Traducido por Spring Data a: SELECT * FROM estudiante WHERE email = ?
    // Devuelve Optional porque el estudiante puede no existir.
    Optional<Estudiante> findByEmail(String email);
}