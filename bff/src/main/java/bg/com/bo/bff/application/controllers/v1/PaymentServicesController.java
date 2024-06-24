package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.payment.services.SubCategoryCitiesResponse;
import bg.com.bo.bff.application.dtos.response.payment.services.SubcategoriesResponse;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.services.interfaces.IPaymentServicesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Validated
@RequestMapping("api/v1/payment-services")
@Tag(name = "Payment Services Controller", description = "Controlador del módulo de Payment Services")
public class PaymentServicesController {

    private final IPaymentServicesService service;
    private final HttpServletRequest httpServletRequest;

    public PaymentServicesController(IPaymentServicesService service, HttpServletRequest httpServletRequest) {
        this.service = service;
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping("/categories/{categoryId}/subcategories")
    public ResponseEntity<SubcategoriesResponse> getSubcategories(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @Parameter(description = "Este es el código de categoria", example = "1234567")
            @Min(value = 1, message = " El categoryId debe ser mayour a 0")
            @PathVariable("categoryId") final Integer categoryId
    ) throws IOException {
        return  ResponseEntity.ok(service.getSubcategories(categoryId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }

    @Operation(summary = "This operation lists the payment cities by subcategory", description = "Lista las cidudades de pago por subcategorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene las subcategorias de pago de servicios", content = @Content(schema = @Schema(implementation = SubCategoryCitiesResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Error si la categoria no tiene subcategorias", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/subcategory/{subCategoryId}/cities")
    public ResponseEntity<SubCategoryCitiesResponse> getSubcategoryCities(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @Parameter(description = "Este es el código de subcategoria  categoria", example = "10")
            @Min(value = 1, message = " El categoryId debe ser mayour a 0")
            @PathVariable("subCategoryId") final Integer subCategoryId
    ) throws IOException {
        return  ResponseEntity.ok(service.getSubcategoryCities(subCategoryId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }

}
