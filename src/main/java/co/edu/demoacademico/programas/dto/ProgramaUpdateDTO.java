package co.edu.demoacademico.programas.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada para actualizar un Programa (el código no se modifica).
 */
public class ProgramaUpdateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
