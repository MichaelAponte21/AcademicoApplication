package co.edu.demoacademico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * ============================================================
 * CAPA: MODELO / DOMINIO (Entity)
 * ============================================================
 * Responsabilidad: representar el dato del negocio. Es una
 * entidad JPA: cada instancia corresponde a una fila de la
 * tabla 'estudiante'.
 *
 * Es transversal a las tres capas: la usan el Controller, el
 * Service y el Repository.
 *
 *  - @Entity / @Table la mapean a la tabla de la BD.
 *  - Las anotaciones de Bean Validation (@NotBlank, @Email)
 *    definen las reglas de formato, que el Controller activa
 *    con @Valid.
 *  - La restricción unique=true sobre 'email' es el respaldo a
 *    nivel de BD de la regla de negocio del Service.
 */
@Entity
@Table(name = "estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    @Column(nullable = false, unique = true)
    private String email;

    public Estudiante() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}