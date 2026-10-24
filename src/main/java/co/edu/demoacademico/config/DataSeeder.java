package co.edu.demoacademico.config;

import co.edu.demoacademico.estudiantes.model.Estudiante;
import co.edu.demoacademico.estudiantes.repository.EstudianteRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

/**
 * Siembra estudiantes de prueba con Faker al arrancar, si
 * app.seed.enabled=true y la tabla está vacía.
 * La cantidad se controla con app.seed.cantidad-estudiantes.
 */
@Configuration
public class DataSeeder {

    @Value("${app.seed.enabled:true}")
    private boolean enabled;

    @Value("${app.seed.cantidad-estudiantes:50}")
    private int cantidad;

    @Bean
    CommandLineRunner seedEstudiantes(EstudianteRepository repo) {
        return args -> {
            if (!enabled) return;
            if (repo.count() > 0) return;

            Faker faker = new Faker(Locale.of("es"));
            int creados = 0;

            while (creados < cantidad) {
                String nombre = faker.name().fullName();
                // El prefijo est{n}_ garantiza la unicidad del email
                String email = ("est" + creados + "_" + faker.internet().emailAddress())
                        .toLowerCase()
                        .replace(" ", "")
                        .replace("..", ".");

                Estudiante e = new Estudiante();
                e.setNombre(nombre);
                e.setEmail(email);

                try {
                    repo.save(e);
                    creados++;
                } catch (Exception ex) {
                    // si el email falla (formato/único), reintenta
                }
            }
        };
    }
}
