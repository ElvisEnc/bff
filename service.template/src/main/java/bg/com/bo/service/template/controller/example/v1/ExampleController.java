package bg.com.bo.service.template.controller.example.v1;

import bg.com.bo.service.template.model.ErrorResponse;
import bg.com.bo.service.template.model.Example;
import bg.com.bo.service.template.model.ExceptionNotFound;
import bg.com.bo.service.template.model.Response;
import bg.com.bo.service.template.service.ExampleService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Tag(name = "Example-Controller", description = "Controlador de Ejemplo")
public class ExampleController {
    @Autowired
    private ExampleService exampleService;
    private static final Logger logger = LogManager.getLogger(ExampleController.class.getName());

    @Operation(summary = "Endpoint Obtener.", description = "Este es un ejemplo para una petición Get.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Resultado exitoso."), @ApiResponse(responseCode = "404", description = "Resultado 404. Recurso no encontrado."), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getExample(@PathVariable int id) {
        try {
//            Example example = null;
//            example.setId(1);
            if (Objects.equals(id, 1)) throw new ExceptionNotFound("No se encontró el objeto con el id otorgado");
            if (Objects.equals(id, 2)) throw new RuntimeException("Error Generado");
            Example example = exampleService.getExample(id);
            return ResponseEntity.ok(example);
        } catch (ExceptionNotFound ex) {
            logger.error(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Internal Server Error");
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.getMessage());
        }
    }

    @Operation(summary = "Endpoint Crear.", description = "Este es un ejemplo para una petición Post.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Resultado exitoso.")})
    @PostMapping("/crear")
    public ResponseEntity<Response> postExample(@RequestBody Example example) {
        Response response = exampleService.createExample(example);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Endpoint Eliminar.", description = "Este es un ejemplo para una petición Delete.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Resultado exitoso."), @ApiResponse(responseCode = "404", description = "Resultado 404. Recurso no encontrado."), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> deletExample(@RequestParam String id) {
        try {
            Response response = exampleService.deleteExample(id);
            return ResponseEntity.ok(response);
        } catch (ExceptionNotFound ex) {
            logger.error(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Internal Server Error");
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.getMessage());
        }
    }

    @Operation(summary = "Endpoint Actualizar.", description = "Este es un ejemplo para una petición Put.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Resultado exitoso."), @ApiResponse(responseCode = "404", description = "Resultado 404. Recurso no encontrado."), @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PutMapping("/actualizar")
    public ResponseEntity<?> putExample(@RequestBody Example example) {
        try {
            if (example.getId() == 0) throw new ExceptionNotFound("No se encontró el objeto con el id otorgado");
            if (example.getId() == -1) throw new RuntimeException();
            Response response = exampleService.updateExample(example);
            return ResponseEntity.ok(response);
        } catch (ExceptionNotFound ex) {
            logger.error(ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Internal Server Error");
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.getMessage());
        }
    }
}
