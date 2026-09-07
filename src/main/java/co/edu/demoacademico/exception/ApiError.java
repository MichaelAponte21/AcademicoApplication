package co.edu.demoacademico.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Cuerpo de respuesta estándar para los errores de la API.
 * 'errores' solo se incluye cuando hay validaciones de campo fallidas.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String mensaje,
        String path,
        Map<String, String> errores
) {
    public ApiError(int status, String error, String mensaje, String path) {
        this(LocalDateTime.now(), status, error, mensaje, path, null);
    }

    public ApiError(int status, String error, String mensaje, String path, Map<String, String> errores) {
        this(LocalDateTime.now(), status, error, mensaje, path, errores);
    }
}
