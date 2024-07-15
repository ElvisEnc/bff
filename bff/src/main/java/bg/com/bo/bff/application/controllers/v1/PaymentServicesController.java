package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.dtos.request.payment.service.AffiliationDebtsRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.PaymentDebtsRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.affiliation.ServiceAffiliationRequest;
import bg.com.bo.bff.application.dtos.request.payment.service.ValidateAffiliateCriteriaRequest;
import bg.com.bo.bff.application.dtos.response.generic.ErrorResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.payment.service.*;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.commons.utils.Headers;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.IPaymentServicesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1/payment-services")
@Tag(name = "Payment Services Controller", description = "Controlador del módulo de Pago de Servicios")
public class PaymentServicesController {
    private final IPaymentServicesService service;
    private final HttpServletRequest httpServletRequest;

    public PaymentServicesController(IPaymentServicesService service, HttpServletRequest httpServletRequest) {
        this.service = service;
        this.httpServletRequest = httpServletRequest;
    }

    @Operation(summary = "Lista categorias", description = "Obtiene las categorias para pago de servicios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene las categorias de pago de servicios"),
            @ApiResponse(responseCode = "200", description = "Obtiene las categorias de pago de servicios"),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/categories")
    public ResponseEntity<ApiDataResponse<List<CategoryResponse>>> getCategories(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion
    ) throws IOException {
        List<CategoryResponse> categories = service.getCategories(Headers.getParameter(httpServletRequest));
        return ResponseEntity.ok(ApiDataResponse.of(categories));
    }

    @Operation(summary = "Lista de subcategorias", description = "Lista de las subcategorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene las subcategorias de pago de servicios", content = @Content(schema = @Schema(implementation = SubcategoriesResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Error si la categoria no tiene subcategorias", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/categories/{categoryId}/subcategories")
    public ResponseEntity<SubcategoriesResponse> getSubcategories(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @Parameter(description = "Este es el código de categoria", example = "1234567")
            @Min(value = 1, message = " El categoryId debe ser mayor a 0")
            @PathVariable("categoryId") final Integer categoryId
    ) throws IOException {
        return ResponseEntity.ok(service.getSubcategories(categoryId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }

    @Operation(summary = "Ciudades por Subcategoria", description = "Lista las cidudades de pago por subcategorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene las las ciudades", content = @Content(schema = @Schema(implementation = SubCategoryCitiesResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/subcategories/{subCategoryId}/cities")
    public ResponseEntity<SubCategoryCitiesResponse> getSubcategoryCities(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @Parameter(description = "Este es el código de subcategoria  categoria", example = "10")
            @Min(value = 1, message = " El categoryId debe ser mayor a 0")
            @PathVariable("subCategoryId") final Integer subCategoryId
    ) throws IOException {
        return ResponseEntity.ok(service.getSubcategoryCities(subCategoryId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }

    @Operation(summary = "Eliminar servicio", description = "Elimina un servicio afiliado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la operación y su descripción.", content = @Content(schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Error si la categoria no tiene subcategorias", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @DeleteMapping("/persons/{personId}/affiliate-services/{affiliateServiceId}")
    public ResponseEntity<GenericResponse> deleteAffiliation(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,

            @Parameter(description = "Este es el Person id", example = "1222220")
            @Pattern(regexp = "^\\d{1,12}$", message = "El codigo de persona es invalido")
            @NotNull
            @PathVariable("personId") final String personId,

            @Parameter(description = "Este es el número de servicio de afiliacion", example = "12220")
            @NotNull
            @Pattern(regexp = "^\\d+$", message = "Número de servicio de afiliacion es invalido")
            @PathVariable("affiliateServiceId") final String affiliateServiceId
    ) throws IOException {
        return ResponseEntity.ok(service.deleteAffiliationService(personId, affiliateServiceId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }

    @Operation(summary = "Lista Servicios Afiliados", description = "Obtiene los servicios afiliados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicios afiliados"),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Error de negocio", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/affiliate-services")
    public ResponseEntity<ApiDataResponse<List<AffiliatedServicesResponse>>> getAffiliatedServices(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotNull @Parameter(description = "Este es el personId de la persona", example = "12345") Integer personId
    ) throws IOException {
        List<AffiliatedServicesResponse> affiliateServices = service.getAffiliatedServices(personId, Headers.getParameter(httpServletRequest));
        return ResponseEntity.ok(ApiDataResponse.of(affiliateServices));
    }

    @Operation(summary = "Afiliar Servicio", description = "Endpoint para afiliar un servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servicio afiliado"),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Error de negocio", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PutMapping("/persons/{personId}/affiliate-services")
    public ResponseEntity<ServiceAffiliationResponse> serviceAffiliation(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @Valid @RequestBody ServiceAffiliationRequest request
    ) throws IOException {
        return ResponseEntity.ok(service.serviceAffiliation(personId, request, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Lista de deudas", description = "Obtiene una lista de las deudas de un servicio afiliado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deudas de un servicio afiliado"),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Error de negocio", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}/affiliate-services/{affiliateServiceId}/debts")
    public ResponseEntity<ApiDataResponse<AffiliationDebtsResponse>> getAffiliationDebts(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @NotNull @Parameter(description = "Código de persona", example = "12345") Integer personId,
            @PathVariable("affiliateServiceId") @NotNull @Parameter(description = "Id del servicio afiliado", example = "12345") Integer affiliateServiceId,
            @Valid @RequestBody AffiliationDebtsRequest request
    ) throws IOException {
        return ResponseEntity.ok(ApiDataResponse.of(service.getAffiliationDebts(personId, affiliateServiceId, request, Headers.getParameter(httpServletRequest))));
    }

    @Operation(summary = "Pago de servicio", description = "Realiza pago de una servicio afiliado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago de deuda"),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "406", description = "Error de negocio", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}/affiliate-services/{affiliateServiceId}/payment")
    public ResponseEntity<PaymentDebtsResponse> paymentDebts(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Código de persona", example = "12345") String personId,
            @PathVariable("affiliateServiceId") @OnlyNumber @Parameter(description = "Id del servicio afiliado", example = "12345") String affiliateServiceId,
            @Valid @RequestBody PaymentDebtsRequest request
    ) throws IOException {
        return ResponseEntity.ok(service.paymentDebts(personId, affiliateServiceId, request, Headers.getParameter(httpServletRequest)));
    }

    @Operation(summary = "Obtener la lista de servicios por subcategoria y ciudad", description = "Lista los servicios por subcategorias y ciudades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene las subcategorias de pago de servicios", content = @Content(schema = @Schema(implementation = ListServicesResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Error si la categoria no tiene subcategorias", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/subcategories/{subCategoryId}/cities/{cityId}")
    public ResponseEntity<ListServicesResponse> getServices(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,

            @Parameter(description = "Código de la subcategoria", example = "4")
            @Min(value = 1, message = "La subCategoryId debe ser mayor a 0")
            @PathVariable("subCategoryId") final Integer subCategoryId,

            @Parameter(description = "Código de la ciudad", example = "7")
            @Min(value = 0, message = "La cityId debe ser mayor o igual a 0")
            @PathVariable("cityId") final Integer cityId
    ) throws IOException {
        return ResponseEntity.ok(service.getServicesByCategoryAndCity(subCategoryId, cityId, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }

    @Operation(summary = "Obtener criterios de afiliación", description = "Obtiene los criterios de afiliación de un servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene las subcategorias de pago de servicios", content = @Content(schema = @Schema(implementation = AffiliateCriteriaResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @GetMapping("/persons/{personId}/affiliate-criteria")
    public ResponseEntity<AffiliateCriteriaResponse> getAffiliateCriteria(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,

            @Parameter(description = "Código de la persona", example = "123")
            @NotNull
            @Pattern(regexp = "^\\d+$", message = "Número de persona inválido")
            @PathVariable("personId") final String personId,

            @RequestParam(value = "serviceCode", required = true)
            @Parameter(description = "Código del servicio", example = "85") final String serviceCode
    ) throws IOException {
        return ResponseEntity.ok(service.getAffiliateCriteria(personId, serviceCode, Headers.getParameter(httpServletRequest,
                deviceId,
                deviceName,
                geoPositionX,
                geoPositionY,
                appVersion)));
    }


    @Operation(summary = "Validar criterios de afiliación", description = "Valida los criterios de afiliación de un servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene las subcategorias de pago de servicios", content = @Content(schema = @Schema(implementation = ValidateAffiliateCriteriaResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Error en los parametros", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Un error interno", content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}/validate-affiliate-criteria")
    public ResponseEntity<ValidateAffiliateCriteriaResponse> validateAffiliateCriteria(
            @RequestHeader("device-id") @NotBlank @Parameter(description = "Este es el Unique deviceId", example = "42ebffbd7c30307d") String deviceId,
            @RequestHeader("device-name") @Parameter(description = "Este es el deviceName", example = "ANDROID") String deviceName,
            @RequestHeader("geo-position-x") @NotBlank @Parameter(description = "Este es el geoPositionX", example = "12.265656") String geoPositionX,
            @RequestHeader("geo-position-y") @NotBlank @Parameter(description = "Este es el geoPositionY", example = "12.454545") String geoPositionY,
            @RequestHeader("app-version") @NotBlank @Parameter(description = "Este es el appVersion", example = "1.3.3") String appVersion,

            @Parameter(description = "Código de la persona", example = "123")
            @NotNull
            @Pattern(regexp = "^\\d+$", message = "Número de persona inválido")
            @PathVariable("personId") final String personId,

            @RequestParam(value = "serviceCode", required = true)
            @Parameter(description = "Código del servicio", example = "85") final String serviceCode,

            @Valid @RequestBody ValidateAffiliateCriteriaRequest body
    ) throws IOException {
        return ResponseEntity.ok(service.validateAffiliateCriteria(personId, serviceCode, body, Headers.getParameter(httpServletRequest)));
    }
}
