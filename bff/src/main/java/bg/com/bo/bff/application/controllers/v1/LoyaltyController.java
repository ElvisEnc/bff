package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyImageRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.LoyaltyStatementRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterRedeemVoucherRequest;
import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCategoryPromotionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyImageResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyInitialPointsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyLevelResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPointResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyPromotionResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyCityListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyFeaturedMerchantListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyMerchantVoucherCategoryResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyQrTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyRedeemVoucherResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGeneralInfoResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStatementResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyStoreFeaturedResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTermsConditionsResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyGenericVoucherTransactionResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactionsResponse;
import bg.com.bo.bff.commons.annotations.loyalty.ValidTypeBenefit;
import bg.com.bo.bff.commons.annotations.loyalty.ValidTypeStatus;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategoryListResponse;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyVoucherTransactedListResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
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
import java.util.List;
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
    public ResponseEntity<LoyaltyPointResponse> getSumPoint(
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
    public ResponseEntity<LoyaltyLevelResponse> getLevel(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getLevel(personId));
    }

    @Operation(summary = "Obtener puntos por periodo", description = "Obtener puntos por periodo en el programa",
            operationId = "getPointsPeriod")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener puntos por periodo en el programa")
    })
    @GetMapping("/persons/{personId}/system-code/{codeSystem}/points-period")
    public ResponseEntity<LoyaltyPointResponse> getPointsPeriod(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("codeSystem") @OnlyNumber @Parameter(description = "Este es el codigo de sistema de la persona", example = "12345") String codeSystem
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getPointsPeriod(personId, codeSystem));
    }

    @Operation(summary = "Obtener puntos por periodo", description = "Obtener puntos por periodo en el programa",
            operationId = "getPointsPeriod")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener puntos por periodo en el programa")
    })
    @GetMapping("/persons/{personId}/initial-points-vamos")
    public ResponseEntity<LoyaltyInitialPointsResponse> getInitialPointsVAMOS(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getInitialPointsVAMOS(personId));
    }

    @Operation(summary = "Verifica su subscripcion", description = "Verifica su subscripcion en el programa",
            operationId = "verifySubscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verifica su subscripcion en el programa")
    })
    @GetMapping("/persons/{personId}/verify-subscription")
    public ResponseEntity<GenericResponse> verifySubscription(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.verifySubscription(personId));
    }

    @Operation(summary = "Obtener el extracto de los puntos", description = "Obtener el extracto de los puntos del programa VAMOS",
            operationId = "getStatementPoints")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener el extracto de los puntos del programa VAMOS")
    })
    @PostMapping(path = "/persons/{personId}/system-code/{codeSystem}/statement-points", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<LoyaltyStatementResponse>> getStatementPoints(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("codeSystem") @OnlyNumber @Parameter(description = "Este es el codigo de sistema de la persona", example = "12345") String codeSystem,
            @Valid @RequestBody LoyaltyStatementRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.statementPoints(personId, codeSystem, request));
    }

    @Operation(summary = "Obtiene la informacion general", description = "Obtiene la informacion general de la persona en el programa",
            operationId = "getGeneralInformation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene la informacion general de la persona en el programa")
    })
    @GetMapping("/persons/{personId}/general-information")
    public ResponseEntity<LoyaltyGeneralInfoResponse> getGeneralInformation(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getGeneralInformation(personId));
    }

    @Operation(summary = "Obtiene la informacion de una imagen", description = "Obtiene la informacion de una imagen del programa",
            operationId = "getImageInformation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene la informacion de una imagen del programa")
    })
    @GetMapping("/image/{imageId}/image-information")
    public ResponseEntity<LoyaltyImageResponse> getImageInformation (
            @PathVariable("imageId") @OnlyNumber @Parameter(description = "Este es la imagenId", example = "12345") String imageId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getImageInformation(imageId));
    }

    @Operation(summary = "Obtiene la informacion de las imagenes", description = "Obtiene la informacion de las imagenes que requiere",
            operationId = "getImageInformation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene la informacion de las imagenes que requiere")
    })
    @PostMapping(path = "/images-information", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<LoyaltyImageResponse>> getImagesInformation (
            @Valid @RequestBody LoyaltyImageRequest request
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getImagesInformation(request));
    }

    @Operation(summary = "Obtiene la categoria de promocion", description = "Obtiene la categoria de promocion del programa",
            operationId = "getCategoryPromotions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene la categoria de promocion del programa")
    })
    @GetMapping("/persons/{personId}/category-promotions")
    public ResponseEntity<List<LoyaltyCategoryPromotionResponse>> getCategoryPromotions (
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getCategoryPromotions(personId));
    }

    @Operation(summary = "Obtiene la categoria de niveles", description = "Obtiene la informacion de niveles del programa",
            operationId = "getCategoryPointsLevels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene la informacion de niveles del programa")
    })
    @GetMapping("/persons/{personId}/category-points-level")
    public ResponseEntity<List<LoyaltyLevelResponse>> getCategoryPointsLevels (
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getCategoryPointsLevels(personId));
    }

    @Operation(summary = "Obtiene los terminos y condiciones", description = "Obtiene los terminos y condiciones del programa",
            operationId = "termsConditions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtiene los terminos y condiciones programa")
    })
    @GetMapping("/persons/{personId}/terms-conditions")
    public ResponseEntity<LoyaltyTermsConditionsResponse> termsConditions (
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.termsConditions(personId));
    }

    @Operation(summary = "Verificar fluJo con o sin VAMOS", description = "Verificar fluJo con o sin VAMOS",
            operationId = "termsConditions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificar fluJo con o sin VAMOS")
    })
    @GetMapping("/persons/{personId}/check-flow")
    public ResponseEntity<GenericResponse> checkFlow (
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.checkFlow(personId));
    }

    @Operation(summary = "Obtener promocion", description = "Obtener promocion del programa",
            operationId = "getPromotions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener promocion del programa")
    })
    @GetMapping("/persons/{personId}/promotion/{promotionId}/promotion")
    public ResponseEntity<LoyaltyPromotionResponse> getPromotions (
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("promotionId") @Parameter(description = "Este es el promotionId de la promocion", example = "12345") String promotionId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getPromotions(personId, promotionId));
    }

    @Operation(summary = "Obtener los comercios destacados", description = "Obtener los comercios destacados del programa",
            operationId = "getStoreFeatured")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener los comercios destacados del programa")
    })
    @GetMapping("/persons/{personId}/store-featured")
    public ResponseEntity<List<LoyaltyStoreFeaturedResponse>> getStoreFeatured (
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getStoreFeatured(personId));
    }

    @Operation(summary = "Obtener el vale qr transaccionado", description = "Obtener el vale qr transaccionado del programa",
            operationId = "getQRTransactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener el vale qr transaccionado del programa")
    })
    @GetMapping("/persons/{personId}/voucher/{voucherId}/type/{typeVoucher}/qr-transactions")
    public ResponseEntity<LoyaltyGenericVoucherTransactionResponse> getQRTransactions (
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("voucherId") @Parameter(description = "Este es el identificador del vale", example = "CONSUMO") String voucherId,
            @PathVariable("typeVoucher") @ValidTypeBenefit @Parameter(description = "Este es el personId de la persona", example = "12345") String typeVoucher
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getQRTransactions(personId, voucherId, typeVoucher));
    }

    @Operation(summary = "Obtener el vale transaccionado", description = "Obtener el vale transaccionado del programa",
            operationId = "getVoucherTransactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtener el vale transaccionado del programa")
    })
    @GetMapping("/persons/{personId}/system-code/{codeSystem}/status/{status}/voucher-transactions")
    public ResponseEntity<List<LoyaltyVoucherTransactionsResponse>> getVoucherTransactions (
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el personId de la persona", example = "12345") String personId,
            @PathVariable("codeSystem") @OnlyNumber @Parameter(description = "Este es el codigo de sistema de la persona", example = "12345") String codeSystem,
            @PathVariable("status") @ValidTypeStatus @Parameter(description = "Este es el estado del vale", example = "VIGENTE") String status
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(service.getVoucherTransactions(personId, codeSystem, status));
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
