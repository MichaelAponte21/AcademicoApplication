package co.edu.demoacademico.exception;

/**
 * Regla de negocio violada (por ejemplo: email duplicado) -> 400 Bad Request.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
