package co.edu.demoacademico.controller;

import co.edu.demoacademico.api.ApiResponse;
import co.edu.demoacademico.api.ResponseBuilder;
import co.edu.demoacademico.dto.MatriculaCreateDTO;
import co.edu.demoacademico.dto.MatriculaDTO;
import co.edu.demoacademico.handler.MatriculaHandler;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    private final MatriculaHandler handler;

    public MatriculaController(MatriculaHandler handler) {
        this.handler = handler;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MatriculaDTO>> matricular(@Valid @RequestBody MatriculaCreateDTO in) {
        return ResponseBuilder.created("Matrícula registrada", handler.matricular(in));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<MatriculaDTO>>> listar(@ParameterObject Pageable pageable) {
        return ResponseBuilder.ok("OK", handler.listar(pageable));
    }
}
