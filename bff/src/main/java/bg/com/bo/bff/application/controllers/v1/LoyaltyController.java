package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCityListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchantListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevel;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyMerchantVoucherCategoryResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyQrTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategoryListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactedListResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.services.interfaces.ILoyaltyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("api/v1/loyalty")
@Tag(name = "Loyalty Controller", description = "Controlador del programa VAMOS")
public class LoyaltyController extends AbstractBFFController {
    private final ILoyaltyService service;

    public LoyaltyController(ILoyaltyService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener codigo de sistema", description = "Obtener el codigo de sistema de la persona",
            operationId = "getSystemCode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener el codigo de sistema de la persona")
    })
    @GetMapping("/persons/{personId}/system-code")
    public ResponseEntity<LoyaltySystemCodeResponse> getSystemCode(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getSystemCode(personId));
    }

    @Operation(summary = "Obtener la sumatoria de los puntos", description = "Obtener la sumatoria de los puntos acumulados",
            operationId = "getSumPoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener la sumatoria de los puntos acumulados")
    })
    @GetMapping("/persons/{personId}/system-code/{codeSystem}/sum-points")
    public ResponseEntity<LoyaltySumPointResponse> getSumPoint(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("codeSystem") @Parameter(description = "Este es el codigo de sistema de la persona", example = "12345") String codeSystem
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getSumPoint(personId, codeSystem));
    }

    @Operation(summary = "Registrar suscripción", description = "Realizar la suscripción al programa VAMOS",
            operationId = "registerSubscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Realizar la suscripción al programa VAMOS")
    })
    @PostMapping(path = "/persons/{personId}/accounts/{accountId}/register-subscription", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GenericResponse> registerSubscription(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("accountId") @OnlyNumber @Parameter(description = "Este es la cuenta de la persona (jts_oid)", example = "12345") String accountId,
            @Valid @RequestBody RegisterSubscriptionRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.registerSubscription(personId, accountId, request));
    }

    @Operation(summary = "Registra el canje de vale", description = "Registra el canje de vale del programa VAMOS",
            operationId = "registerRedeemVoucher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registra el canje de vale del programa VAMOS")
    })
    @PostMapping(path = "/persons/{personId}/system-code/{codeSystem}/redeem-voucher", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LoyaltyRedeemVoucherResponse> registerRedeemVoucher(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("codeSystem") @OnlyNumber @Parameter(description = "Este es el codigo de sistema de la persona", example = "12345") String codeSystem,
            @Valid @RequestBody RegisterRedeemVoucherRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.registerRedeemVoucher(personId, codeSystem, request));
    }

    @Operation(summary = "Obtener el nivel", description = "Obtener el nivel de la persona en el programa",
            operationId = "getLevel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener el nivel de la persona en el programa")
    })
    @GetMapping("/persons/{personId}/level")
    public ResponseEntity<LoyaltyLevel> getLevel(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getLevel(personId));
    }

    @Operation(summary = "Obtener el listado de categorias de comercios.",
            description = "Obtener listado de categorias de comercios",
            operationId = "getTradeCategories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener el nivel de la persona en el programa")
    })
    @GetMapping("/persons/{personId}/trade-categories")
    public ResponseEntity<LoyaltyTradeCategoryListResponse> getTradesCategory(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getTradeCategories(personId));

    }

    @Operation(summary = "Obtener el listado de comercios destacados.",
            description = "Obtener el listado de los  comerciosque sobresalieron/descataron.",
            operationId = "getFeaturedMerchants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener listado de comercios destacados.")
    })
    @GetMapping("/persons/{personId}/featured-merchants")
    public ResponseEntity<ApiDataResponse<LoyaltyFeaturedMerchantListResponse>> getFeaturedMerchants(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getFeaturedMerchants(personId)));
    }

    @Operation(summary = "Obtener el listado de ciudades/departamentos.",
            description = "Obtener el listado completo de las ciudades/departamentos.",
            operationId = "getCityList")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de ciudades/departamentos.")
    })
    @GetMapping("/persons/{personId}/cities")
    public ResponseEntity<ApiDataResponse<LoyaltyCityListResponse>> getCityList(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getCityList(personId)));
    }

    @Operation(summary = "Obtener el listado de los comercios por ciudades.",
            description = "Obtener el listado de los comercios por ciudades y categorias.",
            operationId = "getCityCategoryMerchanst")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/persons/{personId}/merchants")
    public ResponseEntity<ApiDataResponse<LoyaltyFeaturedMerchantListResponse>> getCityCategoryMerchants(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @RequestParam("cityId") @Parameter(description = "ID de la ciudad.") UUID cityId,
            @RequestParam("categoryId") @Parameter(description = "ID de la categoria.") UUID categoryId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getCityCategoryMerchants(personId, cityId, categoryId)));
    }

    @Operation(
            summary = "Obtener detalle de voucher.",
            description = "Obtener detalle de voucher con todos los detalles y su categoria.",
            operationId = "getVoucherDetail"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Obtener detalle de voucher."
            )
    })
    @GetMapping("/persons/{personId}/vouchers/{voucherId}/type/{voucherType}")
    public ResponseEntity<ApiDataResponse<LoyaltyQrTransactionResponse>> getVoucherDetail(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("voucherId") @Parameter(description = "ID de la ciudad.") UUID voucherId,
            @PathVariable("voucherType") @Parameter(description = "ID de la categoria.") String voucherType
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getVoucherDetail(personId, voucherId, voucherType)));
    }

    @Operation(
            summary = "Obtener listado de vouchers",
            description = "Obtener listado de vouchers/vales de un comercio por categoria",
            operationId = "getCategoryVoucherList"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de voucher.")
    })
    @GetMapping("/persons/{personId}/vouchers")
    public ResponseEntity<ApiDataResponse<LoyaltyMerchantVoucherCategoryResponse>> getCategoryVoucherList(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @RequestParam("merchantId") @Parameter(description = "ID del comercio.") UUID merchantId,
            @RequestParam("categoryId") @Parameter(description = "ID de la categoria.") UUID categoryId,
            @RequestParam("campaignId") @Parameter(description = "ID de la campaña.") int campaignId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(
                service.getMerchantCampaignVouchers(personId, merchantId, categoryId, campaignId)
        ));
    }

    @Operation(
            summary = "Obtener el listado de vouchers canjeados.",
            description = "Obtener listado de vouchers/vales que fueron canjeados",
            operationId = "getVoucherTrasanctedList"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de vouchers/vales canjeados."
            )
    })
    @GetMapping("/persons/{personId}/vouchers/transacted")
    public ResponseEntity<LoyaltyVoucherTransactedListResponse> getVoucherTransactedList(
            @PathVariable("personId") @OnlyNumber
            @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @RequestParam("campaignId") @Parameter(description = "ID de la categoria.") int campaignId,
            @RequestParam("state") @Parameter(description = "ID del estado.") String state
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(
                service.getVoucherTransactedList(personId, campaignId, state)
        );
    }
}
