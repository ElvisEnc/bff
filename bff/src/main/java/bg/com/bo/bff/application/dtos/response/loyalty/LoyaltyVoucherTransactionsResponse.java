package bg.com.bo.bff.application.dtos.response.loyalty;

import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetStoreFeaturedResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltyGetTransactionsResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyVoucherTransactionsResponse {

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

    @Schema(description = "Nombre del beneficiario del vale")
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

    @Schema(description = "Valor de canje en puntos")
    @JsonProperty("redemptionValue")
    private Integer redemptionValue;

    @Schema(description = "Fecha de expiración del vale")
    @JsonProperty("expirationDate")
    private String expirationDate;

    @Schema(description = "Identificador del beneficio asociado")
    @JsonProperty("benefitId")
    private String benefitId;

    @Schema(description = "Nombre del vale")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Descripción del vale")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Ruta del banner promocional del vale")
    @JsonProperty("banner")
    private String banner;

    @Schema(description = "Indicador si el vale fue canjeado (0: no, 1: sí)")
    @JsonProperty("redeemed")
    private Integer redeemed;

    @Schema(description = "Fecha en la que se canjeó el vale")
    @JsonProperty("redemptionDate")
    private String redemptionDate;

    @Schema(description = "Identificador del administrador o gestor que realizó el canje")
    @JsonProperty("managerId")
    private String managerId;

    @Schema(description = "Costo monetario del vale")
    @JsonProperty("voucherCost")
    private Integer voucherCost;

    @Schema(description = "Tipo de vale (por ejemplo, CONSUMO)")
    @JsonProperty("voucherType")
    private String voucherType;

    @Schema(description = "Porcentaje asumido por la empresa o institución")
    @JsonProperty("assumedPercentage")
    private Integer assumedPercentage;

    @Schema(description = "Nota adicional relacionada con el vale")
    @JsonProperty("note")
    private String note;

    @Schema(description = "Estado actual del vale (por ejemplo, VIGENTE)")
    @JsonProperty("status")
    private String status;

    @Schema(description = "Información del comercio asociado al vale")
    @JsonProperty("store")
    private LoyaltyStoreFeaturedResponse store;

    @Schema(description = "Información del valor de consumo del vale")
    @JsonProperty("consumptionVoucher")
    private ConsumptionVoucher consumptionVoucher;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConsumptionVoucher {

        @Schema(description = "Valor del vale")
        @JsonProperty("valueVoucher")
        private String valueVoucher;

        @Schema(description = "Tipo de valor (por ejemplo, Bs)")
        @JsonProperty("valueType")
        private String valueType;
    }
}
