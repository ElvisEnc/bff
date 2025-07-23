package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.attention.points.DetailAttentionPointResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.ListAttentionPointsResponse;
import bg.com.bo.bff.application.dtos.response.attention.points.PendingTicketResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.services.interfaces.IAttentionPointsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/attention-points")
@Tag(name = "Puntos de atención controller", description = "Controlador de Punto de atención")

public class AttentionPointsController {
    private final IAttentionPointsService service;
    public AttentionPointsController(IAttentionPointsService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener lista de puntos de atención", description = "Obtiene la lista de los puntos de atención")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de puntos de atención", content = @Content(schema = @Schema(implementation = ListAttentionPointsResponse.class)))
    })
    @SecurityRequirements()
    @GetMapping("/points")
    public ResponseEntity<ListAttentionPointsResponse> getListAttentionPoints(    ) throws IOException {
        return ResponseEntity.ok(service.getListAttentionPoints());
    }

    @Operation(summary = "Obtener datos del punto de atención", description = "Obtiene la lista de los datos de un punto de atención")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle del punto de atención", content = @Content(schema = @Schema(implementation = DetailAttentionPointResponse.class)))
    })
    @SecurityRequirements()
    @GetMapping("/points/{pointId}")
    public ResponseEntity<DetailAttentionPointResponse> getDetailAttentionPoint(
            @PathVariable("pointId") @OnlyNumber @Parameter(description = "Este es el pointId del punto de atención", example = "1") String pointId
    ) throws IOException {
        return ResponseEntity.ok(service.getDetailAttentionPoint(pointId));
    }

    @Operation(summary = "Tickets en espera", description = "Obtiene la cantidad de tickets en espera de un punto de atención")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets del punto de atención", content = @Content(schema = @Schema(implementation = PendingTicketResponse.class)))
    })
    @SecurityRequirements()
    @GetMapping("/points/{pointId}/tickets")
    public ResponseEntity<PendingTicketResponse> getPendingTickets(
            @PathVariable("pointId") @OnlyNumber @Parameter(description = "Este es el pointId del punto de atención", example = "1") String pointId
    ) throws IOException {
        return ResponseEntity.ok(service.getPendingTickets(pointId));
    }
}
