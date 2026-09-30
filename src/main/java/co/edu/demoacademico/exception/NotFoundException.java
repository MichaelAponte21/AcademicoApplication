package co.edu.demoacademico.exception;

/**
 * El recurso solicitado no existe -> 404 Not Found.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
