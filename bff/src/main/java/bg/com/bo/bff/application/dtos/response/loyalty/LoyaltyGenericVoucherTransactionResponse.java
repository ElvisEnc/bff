package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyGenericVoucherTransactionResponse {
    @Schema(description = "Identificador del vale")
    @JsonProperty("identifier")
    private String identifier;

    @Schema(description = "Código del vale")
    @JsonProperty("voucherCode")
    private String voucherCode;

    @Schema(description = "Identificador de la campaña")
    @JsonProperty("campaignId")
    private String campaignId;

    @Schema(description = "Nombre del titular del vale")
    @JsonProperty("holderName")
    private String holderName;

    @Schema(description = "Documento de identidad del titular")
    @JsonProperty("holderDocument")
    private String holderDocument;

    @Schema(description = "Nombre del beneficiario")
    @JsonProperty("beneficiaryName")
    private String beneficiaryName;

    @Schema(description = "Documento de identidad del beneficiario")
    @JsonProperty("beneficiaryDocument")
    private String beneficiaryDocument;

    @Schema(description = "Identificador de la persona")
    @JsonProperty("personId")
    private String personId;

    @Schema(description = "Código interno de la persona")
    @JsonProperty("personCode")
    private String personCode;

    @Schema(description = "Fecha de creación del vale")
    @JsonProperty("creationDate")
    private String creationDate;

    @Schema(description = "Fecha de canje del vale")
    @JsonProperty("redemptionDate")
    private String redemptionDate;

    @Schema(description = "Valor de canje del vale")
    @JsonProperty("redemptionValue")
    private Integer redemptionValue;

    @Schema(description = "Fecha de expiración del vale")
    @JsonProperty("expirationDate")
    private String expirationDate;

    @Schema(description = "Identificador del beneficio asociado")
    @JsonProperty("benefitId")
    private String benefitId;

    @Schema(description = "Nombre del vale o beneficio")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Descripción del vale o beneficio")
    @JsonProperty("description")
    private String description;

    @Schema(description = "URL del banner del vale")
    @JsonProperty("banner")
    private String banner;

    @Schema(description = "Nota adicional del vale")
    @JsonProperty("note")
    private String note;

    @Schema(description = "Estado actual del vale")
    @JsonProperty("status")
    private String status;

    @Schema(description = "Información del comercio asociado al vale")
    @JsonProperty("store")
    private LoyaltyStoreFeaturedResponse store;

    @Schema(description = "Tipo de vale (por ejemplo: descuento, consumo)")
    @JsonProperty("voucherType")
    private String voucherType;

    @Schema(description = "Información sobre el valor de consumo del vale")
    @JsonProperty("voucherConsumption")
    private VoucherConsumption voucherConsumption;

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VoucherConsumption {

        @Schema(description = "Valor del vale de consumo")
        @JsonProperty("valueVoucher")
        private String valueVoucher;

        @Schema(description = "Tipo de valor (por ejemplo: Bs, %)")
        @JsonProperty("valueType")
        private String valueType;
    }
}
