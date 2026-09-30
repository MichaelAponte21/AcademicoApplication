package co.edu.demoacademico.dto;

/**
 * DTO de salida: lo que la API expone de un estudiante.
 * Desacopla el contrato de la API de la entidad JPA.
 */
public class EstudianteDTO {

    private Long id;
    private String nombre;
    private String email;

    public EstudianteDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
