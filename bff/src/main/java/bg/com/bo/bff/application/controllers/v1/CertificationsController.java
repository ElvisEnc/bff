package bg.com.bo.bff.application.controllers.v1;

import bg.com.bo.bff.application.config.request.tracing.AbstractBFFController;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationAccountsResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationHistoryResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationPrefExchRateResponse;
import bg.com.bo.bff.application.dtos.response.certifications.CertificationTypesResponse;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;
import bg.com.bo.bff.services.interfaces.ICertificationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1/certifications")
@Tag(name = "Certifications Controller", description = "Contains all the controller methods for Certifications")
public class CertificationsController extends AbstractBFFController {

    private final ICertificationsService service;

    public CertificationsController(ICertificationsService service) {
        this.service = service;
    }

    @Operation(summary = "Get Certificates Types", description = "Endpoint to get the certificates types")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Certification types list", content = @Content(schema = @Schema(implementation = CertificationTypesResponse.class, type = "array")))
            }
    )
    @GetMapping("/persons/{personId}/application/{appCode}")
    public ResponseEntity<ApiDataResponse<List<CertificationTypesResponse>>> getCertsTypes(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el numero de persona", example = "12345") String personId,
            @PathVariable("appCode") @OnlyNumber @Parameter(description = "Este es el numero de persona", example = "1") String appCode
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getCertificateTypes(
                personId,
                appCode
        )));
    }

    @Operation(summary = "Get Accounts List", description = "Endpoint to get a client´s accounts")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Accounts List", content = @Content(schema = @Schema(implementation = CertificationAccountsResponse.class, type = "array")))
            }
    )
    @GetMapping("accounts/persons/{personId}")
    public ResponseEntity<ApiDataResponse<List<CertificationAccountsResponse>>> getAccountsList(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el numero de persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getAccounts(personId)));
    }

    @Operation(summary = "Get Preferred Exchange Rate", description = "Endpoint to get the preferred exchange rate")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Accounts List", content = @Content(schema = @Schema(implementation = CertificationPrefExchRateResponse.class, type = "array")))
            }
    )
    @GetMapping("pref-exchange/persons/{personId}")
    public ResponseEntity<ApiDataResponse<List<CertificationPrefExchRateResponse>>> getPreferredExchangeRate(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el numero de persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getPreferredExchRate(personId)));
    }

    @Operation(summary = "Get Preferred Exchange Rate", description = "Endpoint to get the preferred exchange rate")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Accounts List", content = @Content(schema = @Schema(implementation = CertificationPrefExchRateResponse.class, type = "array")))
            }
    )
    @GetMapping("historic-certs/persons/{personId}")
    public ResponseEntity<ApiDataResponse<List<CertificationHistoryResponse>>> getCertificationsHistory(
            @PathVariable("personId") @OnlyNumber @Parameter(description = "Este es el numero de persona", example = "12345") String personId
    ) throws IOException {
        getDeviceDataHeader();
        return ResponseEntity.ok(ApiDataResponse.of(service.getCertificationsHistory(personId)));
    }
}
