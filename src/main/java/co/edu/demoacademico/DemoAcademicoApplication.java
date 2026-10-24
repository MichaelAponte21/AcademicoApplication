package co.edu.demoacademico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================================
 * PUNTO DE ARRANQUE DE LA APLICACIÓN
 * ============================================================
 * No pertenece a ningún módulo: es quien los pone en marcha.
 * @SpringBootApplication escanea este paquete y sus subpaquetes y
 * registra automáticamente los componentes.
 * <p>
 * El proyecto está organizado por módulos (feature-based packaging):
 * <p>
 *   common/       -> TRANSVERSAL (api: ApiResponse/ResponseBuilder,
 *                    exception: Business/NotFound, controller: GlobalExceptionHandler)
 *   estudiantes/  -> MÓDULO ESTUDIANTES   (port: EstudianteQueryPort)
 *   programas/    -> MÓDULO PROGRAMAS     (port: ProgramaQueryPort)
 *   asignaturas/  -> MÓDULO ASIGNATURAS   (port: AsignaturaQueryPort)
 *   grupos/       -> MÓDULO GRUPOS        (port: GrupoQueryPort)
 *   matriculas/   -> MÓDULO MATRÍCULAS    (usa los Ports de estudiantes y grupos)
 *   config/       -> CONFIGURACIÓN        (seeders con Faker)
 * <p>
 * Cada módulo tiene sus capas: model, dto, repository, service,
 * handler y controller. Un módulo NO usa el repositorio de otro:
 * se comunica con él a través de su Port.
 * <p>
 * Flujo de una petición:
 *   HTTP -> Controller -> Handler -> Service -> Repository -> Base de datos
 */
@SpringBootApplication
public class DemoAcademicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAcademicoApplication.class, args);
	}

}
