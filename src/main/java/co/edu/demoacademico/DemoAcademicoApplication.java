package co.edu.demoacademico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================================
 * PUNTO DE ARRANQUE DE LA APLICACIÓN
 * ============================================================
 * No pertenece a ninguna de las tres capas: es quien las pone
 * en marcha. @SpringBootApplication escanea este paquete y sus
 * subpaquetes y registra automáticamente los componentes:
 *
 *   controller/  -> CAPA DE PRESENTACIÓN   (@RestController)
 *   service/     -> CAPA DE LÓGICA         (@Service)
 *   repository/  -> CAPA DE ACCESO A DATOS (@Repository)
 *   model/       -> MODELO / DOMINIO       (@Entity)
 *   exception/   -> MANEJO DE ERRORES      (@RestControllerAdvice)
 *
 * Flujo de una petición:
 *   HTTP -> Controller -> Service -> Repository -> Base de datos
 */
@SpringBootApplication
public class DemoAcademicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAcademicoApplication.class, args);
	}

}
