package co.edu.demoacademico.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * CAPA: PRESENTACIÓN (apoyo) — MANEJO DE ERRORES
 * ============================================================
 * Responsabilidad: traducir las excepciones que suben desde las
 * capas inferiores a respuestas HTTP claras para el cliente.
 *
 * Pertenece conceptualmente a la capa de Presentación: es el
 * único sitio, junto al Controller, que decide códigos HTTP.
 * Gracias a esta clase el Service puede lanzar excepciones de
 * negocio sin saber nada de HTTP.
 *
 *   EmailYaRegistradoException     -> 409 Conflict
 *   DataIntegrityViolationException -> 409 Conflict
 *   MethodArgumentNotValidException -> 400 Bad Request
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Regla de negocio violada: el email ya existe -> 409 Conflict */
    @ExceptionHandler(EmailYaRegistradoException.class)
    public ResponseEntity<ApiError> manejarEmailYaRegistrado(EmailYaRegistradoException ex,
                                                            HttpServletRequest request) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Red de seguridad para la condición de carrera: si dos peticiones simultáneas
     * pasan la validación previa, la constraint UNIQUE de la BD falla aquí.
     * Se responde igual que el caso anterior para que el cliente vea un 409 coherente.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> manejarViolacionIntegridad(DataIntegrityViolationException ex,
                                                              HttpServletRequest request) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                "El email ya está registrado",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /** Validaciones de @Valid fallidas -> 400 con el detalle por campo */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarValidacion(MethodArgumentNotValidException ex,
                                                     HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errores.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Error de validación en los datos enviados",
                request.getRequestURI(),
                errores
        );
        return ResponseEntity.badRequest().body(error);
    }
}
