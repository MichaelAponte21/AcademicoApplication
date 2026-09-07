package co.edu.demoacademico.exception;

/**
 * Se lanza cuando se intenta crear un estudiante con un email
 * que ya existe en la base de datos (regla de negocio: email único).
 */
public class EmailYaRegistradoException extends RuntimeException {

    public EmailYaRegistradoException(String email) {
        super("El email '" + email + "' ya está registrado");
    }
}
