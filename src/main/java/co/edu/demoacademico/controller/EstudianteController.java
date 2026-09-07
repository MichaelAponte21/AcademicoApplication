package co.edu.demoacademico.controller;

import co.edu.demoacademico.model.Estudiante;
import co.edu.demoacademico.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ============================================================
 * CAPA: PRESENTACIÓN (Controller)
 * ============================================================
 * Responsabilidad: exponer la API REST y traducir entre el
 * mundo HTTP y el mundo Java.
 *
 *  - Recibe las peticiones HTTP y sus parámetros.
 *  - Dispara la validación de entrada con @Valid.
 *  - Decide el código de estado HTTP de la respuesta.
 *  - Delega TODA la lógica de negocio en EstudianteService.
 *
 * NO debe: consultar la base de datos directamente, ni contener
 * reglas de negocio (por ejemplo "el email debe ser único").
 *
 * Colabora con: EstudianteService (capa de lógica).
 */
@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService service;

    public EstudianteController(EstudianteService service) {
        this.service = service;
    }

    @PostMapping
    public Estudiante crear(@Valid @RequestBody Estudiante estudiante) {
        return service.crear(estudiante);
    }

    @GetMapping
    public List<Estudiante> listar() {
        return service.listar();
    }

    @GetMapping("/buscar")
    public ResponseEntity<Estudiante> buscarPorEmail(@RequestParam String email) {
        return service.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

