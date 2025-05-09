package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.credit.card.AuthorizationCreditCardRequest;
import bg.com.bo.bff.application.dtos.request.credit.card.BlockCreditCardRequest;
import bg.com.bo.bff.application.dtos.request.credit.card.CashAdvanceRequest;
import bg.com.bo.bff.application.dtos.request.credit.card.CreditCardStatementRequest;
import bg.com.bo.bff.application.dtos.request.credit.card.FeePrepaidCardRequest;
import bg.com.bo.bff.application.dtos.request.credit.card.PayCreditCardRequest;
import bg.com.bo.bff.application.dtos.response.credit.card.AvailableCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.CashAdvanceFeeResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.CashAdvanceResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.CreditCardStatementsResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.DetailCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.DetailPrepaidCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.FeePrepaidCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.LinkserCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.ListCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.PayCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.PeriodCreditCardResponse;
import bg.com.bo.bff.application.dtos.response.credit.card.PurchaseAuthResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.commons.annotations.Numeric;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.ICreditCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/v1/credit-cards")
@Tag(name = "Credit Card Controller", description = "Controlador de Tarjeta de Crédito")
public class CreditCardController extends AbstractBFFController {
    private final ICreditCardService service;

    public CreditCardController(ICreditCardService service) {
        this.service = service;
    }

    @Operation(
            summary = "Lista de tarjetas de crédito/prepagada",
            description = "Obtiene la lista de las tarjetas de crédito o prepagadas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de tarjetas",
                    content = @Content(schema = @Schema(implementation = ListCreditCardResponse.class)))
    })
    @GetMapping("/persons/{personId}")
    public ResponseEntity<ListCreditCardResponse> getListCreditCards(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getListCard(personId));
    }

    @Operation(summary = "Detalle Tarjeta de Crédito", description = "Obtiene el detalle de la tarjeta de crédito")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Detalle",
                    content = @Content(schema = @Schema(implementation = DetailCreditCardResponse.class)))
    })
    @GetMapping("/persons/{personId}/cards/{cardId}")
    public ResponseEntity<DetailCreditCardResponse> getDetailsCreditCards(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @OnlyNumber
            @Parameter(description = "Este es el cardId", example = "12345") String cardId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getDetailsCreditCard(personId, cardId));
    }

    @Operation(summary = "Detalle Tarjeta Prepagada", description = "Obtiene el detalle de la tarjeta prepagada")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Detalle",
                    content = @Content(schema = @Schema(implementation = DetailPrepaidCardResponse.class)))
    })
    @GetMapping("/persons/{personId}/cards/{cardId}/prepaid")
    public ResponseEntity<DetailPrepaidCardResponse> getDetailsPrepaidCards(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @OnlyNumber
            @Parameter(description = "Este es el cardId", example = "12345") String cardId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getDetailsPrepaidCard(personId, cardId));
    }


    @Operation(
            summary = "Bloquear Desbloquear Tarjeta de Crédito",
            description = "Realiza el bloqueo o desbloqueo de la tarjeta de crédito")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación ejecutada",
                    content = @Content(schema = @Schema(implementation = GenericResponse.class)))
    })
    @PatchMapping("/persons/{personId}/cards/{cardId}/lock-status")
    public ResponseEntity<GenericResponse> blockCreditCard(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @OnlyNumber
            @Parameter(description = "Este es el cardId", example = "12345") String cardId,
            @RequestBody @Valid BlockCreditCardRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.blockCreditCard(personId, cardId, request));
    }

    @Operation(summary = "Disponible", description = "Obtiene el monto disponible de la tarjeta de crédito")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Datos",
                    content = @Content(schema = @Schema(implementation = AvailableCreditCardResponse.class)))
    })
    @GetMapping("/persons/{personId}/cards/{cardId}/available")
    public ResponseEntity<AvailableCreditCardResponse> getAvailableCreditCards(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @OnlyNumber
            @Parameter(description = "Este es el cardId", example = "12345") String cardId,
            @RequestParam("cmsCard") @NotBlank
            @Pattern(regexp = "13-\\d{2}-10-(\\d{6}|\\d{3})", message = "Formato inválido para cmsCard")
            @Schema(description = "número compuesto de la Tarjeta", example = "13-01-10-0000001234") String cmsCard
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getAvailable(personId, cardId, cmsCard));
    }

    @Operation(summary = "Periodos", description = "Obtiene los periodos de pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos")
    })
    @GetMapping("/persons/{personId}/cards/{cardId}/payment-periods")
    public ResponseEntity<ApiDataResponse<List<PeriodCreditCardResponse>>> getPeriods(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId") @OnlyNumber
            @Parameter(description = "Este es el cardId", example = "12345") String cardId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getPeriods(personId, cardId)));
    }

    @Operation(
            summary = "Comisión de avance de efectivo para una cuenta de tarjeta de crédito",
            description = "Obtiene el monto de comisión de avance de efectivo para una cuenta de tarjeta de crédito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monto de comisión de avance en efectivo.")
    })
    @GetMapping("/persons/{personId}/cards/cash-advance/fee")
    public ResponseEntity<CashAdvanceFeeResponse> getCashAdvanceFee(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @RequestParam("amount")
            @Numeric(min = "1", fieldName = "amount", numericType = BigDecimal.class)
            @Parameter(description = "Este es el monto de la comisión", example = "100.5") String amount,
            @RequestParam("cmsAccount") @NotBlank
            @Pattern(regexp = "13-\\d{2}-10-\\d{6}", message = "Formato inválido para cmsAccountNumber")
            @Schema(description = "número compuesto de la cuenta", example = "13-01-10-000000") String cmsAccountNumber
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getCashAdvanceFee(personId, cmsAccountNumber, new BigDecimal(amount)));
    }

    @Operation(summary = "Lista de tarjeta de crédito linkser", description = "Obtiene las tarjetas de crédito de linkser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado")
    })
    @GetMapping("/persons/{personId}/cards")
    public ResponseEntity<ApiDataResponse<List<LinkserCreditCardResponse>>> getCreditCardByCmsAccount(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @RequestParam("cmsAccount") @NotBlank
            @Pattern(regexp = "13-\\d{2}-10-\\d{3,6}", message = "Formato inválido para cmsAccount")
            @Schema(description = "número compuesto de la Cuenta", example = "13-01-10-000000") String cmsAccount
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getCreditCards(personId, cmsAccount)));
    }

    @Operation(summary = "Avance de efectico", description = "Realiza el avance de efectivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación")
    })
    @PostMapping("/persons/{personId}/cards/{cardId}/cash-advance")
    public ResponseEntity<CashAdvanceResponse> makeCashAdvance(
            @PathVariable("personId")
            @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("cardId")
            @OnlyNumber
            @Parameter(
                    description = "Este es el cardId de la cuenta de tarjeta de crédito", example = "12345") String cardId,
            @RequestBody @Valid CashAdvanceRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.makeCashAdvance(personId, cardId, request));
    }

    @Operation(summary = "Extractos", description = "Obtiene los extractos de la tarjeta de crédito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación")
    })
    @PostMapping("/persons/{personId}/cards/statements")
    public ResponseEntity<ApiDataResponse<List<CreditCardStatementsResponse>>> creditCardStatements(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @RequestBody @Valid CreditCardStatementRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.creditCardStatements(personId, request)));
    }

    @Operation(
            summary = "Lista de autorizaciones",
            description = "Obtiene la lista de autorizaciones para las compras por internet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación")
    })
    @GetMapping("/persons/{personId}/authorizations")
    public ResponseEntity<ApiDataResponse<List<PurchaseAuthResponse>>> getPurchases(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @RequestParam("cmsCard") @NotBlank
            @Pattern(regexp = "13-\\d{2}-10-\\d{10}", message = "Formato inválido para cmsCard")
            @Schema(description = "número compuesto de la Tarjeta", example = "13-01-10-0000001234") String cmsCard,
            @RequestParam("type") @NotBlank
            @Pattern(regexp = "^[(I|L)]$", message = "Parametro inválido para type")
            @Schema(description = "Tipo de habilitacion", example = "13-01-10-0000001234") String type

    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getPurchasesAuthorizations(personId, cmsCard, type)));
    }

    @Operation(
            summary = "Pago de TC o TPP",
            description = "Realiza el pago de una tarjeta de crédito o recarga de una tarjeta prepagada")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación",
                    content = @Content(
                            schema = @Schema(implementation = PayCreditCardResponse.class), mediaType = "application/json"))
    })
    @PostMapping("/persons/{personId}/accounts/{accountId}/payments")
    public ResponseEntity<PayCreditCardResponse> paymentCreditCard(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId", example = "12345") String personId,
            @PathVariable("accountId") @OnlyNumber
            @Parameter(description = "Este es el accountId", example = "12345") String accountId,
            @Valid @RequestBody PayCreditCardRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.payCreditCard(personId, accountId, request));
    }

    @Operation(summary = "Habilitación de compras por Internet", description = "Realiza habilitación de compras por internet")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operación",
                    content = @Content(
                            schema = @Schema(implementation = GenericResponse.class), mediaType = "application/json"
                    )
            )
    })
    @PostMapping("/persons/{personId}/authorizations")
    public ResponseEntity<GenericResponse> authorizationCreditCard(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId", example = "12345") String personId,
            @Valid @RequestBody AuthorizationCreditCardRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.authorizationCreditCard(personId, request));
    }

    @Operation(summary = "Comisión Tarjeta Prepagada", description = "Obtiene comisión para recarga de la tarjeta prepagada")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Detalle",
                    content = @Content(
                            schema = @Schema(implementation = FeePrepaidCardResponse.class)
                    )
            )
    })
    @PostMapping("/persons/{personId}/cards/{cardId}/prepaid/fee")
    public ResponseEntity<FeePrepaidCardResponse> getFeePrepaidCard(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId", example = "12345") String personId,
            @PathVariable("cardId") @OnlyNumber
            @Parameter(description = "Este es el cardId", example = "12345") String cardId,
            @Valid @RequestBody FeePrepaidCardRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getFeePrepaidCard(personId, cardId, request));
    }
}
