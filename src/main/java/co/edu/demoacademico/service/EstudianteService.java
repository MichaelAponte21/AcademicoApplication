package co.edu.demoacademico.service;

import co.edu.demoacademico.exception.EmailYaRegistradoException;
import co.edu.demoacademico.model.Estudiante;
import co.edu.demoacademico.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ============================================================
 * CAPA: LÓGICA DE NEGOCIO (Service)
 * ============================================================
 * Responsabilidad: aplicar las reglas del negocio académico.
 * Es el intermediario entre la capa de Presentación y la de
 * Acceso a Datos.
 *
 *  - Regla implementada: el email de un estudiante es único.
 *  - Orquesta las llamadas al Repository.
 *  - Lanza excepciones de negocio (EmailYaRegistradoException)
 *    que la capa de Presentación traduce a códigos HTTP.
 *
 * NO debe: conocer HTTP (nada de ResponseEntity ni códigos de
 * estado), ni escribir SQL a mano.
 *
 * Colabora con: EstudianteRepository (capa de acceso a datos).
 */
@Service
public class EstudianteService {

    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public Estudiante crear(Estudiante estudiante) {

        // ----------------------------
        // ZONA DE LÓGICA DE NEGOCIO:
        // Regla: email único
        // ----------------------------
        repository.findByEmail(estudiante.getEmail())
                .ifPresent(e -> {
                    throw new EmailYaRegistradoException(estudiante.getEmail());
                });

        // ============================
        // ZONA DE ACCESO A LA BD:
        // Persistencia vía Repository
        // ============================
        return repository.save(estudiante);
    }

    public List<Estudiante> listar() {
        // ============================
        // ZONA DE ACCESO A LA BD:
        // Consulta vía Repository
        // ============================
        return repository.findAll();
    }

    public Optional<Estudiante> buscarPorEmail(String email) {
        // ============================
        // ZONA DE ACCESO A LA BD:
        // Consulta vía Repository
        // ============================
        return repository.findByEmail(email);
    }
}