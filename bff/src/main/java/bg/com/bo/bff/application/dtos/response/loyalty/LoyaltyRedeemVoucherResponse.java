package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoyaltyRedeemVoucherResponse {

    @Schema(description = "identificador del vale")
    @JsonProperty("identifier")
    private String identifier;

    @Schema(description = "codigo del vale")
    @JsonProperty("codeVoucher")
    private String codeVoucher;

    @Schema(description = "identificador de la Campana")
    @JsonProperty("idCampaign")
    private String idCampaign;

    @Schema(description = "nombre del Titular")
    @JsonProperty("holderName")
    private String holderName;

    @Schema(description = "documento del Titular")
    @JsonProperty("documentHolder")
    private String documentHolder;

    @Schema(description = "nombre del Beneficiario")
    @JsonProperty("beneficiaryName")
    private String beneficiaryName;

    @Schema(description = "documento de Beneficiario")
    @JsonProperty("beneficiaryDocument")
    private String beneficiaryDocument;

    @Schema(description = "identificador de ñaPersona")
    @JsonProperty("idPerson")
    private String idPerson;

    @Schema(description = "codigo de Persona")
    @JsonProperty("codePerson")
    private String codePerson;

    @Schema(description = "fecha de Creacion")
    @JsonProperty("dateCreation")
    private String dateCreation;

    @Schema(description = "valor del Canje")
    @JsonProperty("valueVoucher")
    private Double valueVoucher;

    @Schema(description = "fecha de Expiracion")
    @JsonProperty("expirationDate")
    private String expirationDate;

    @Schema(description = "identificador del Beneficio")
    @JsonProperty("idBenefit")
    private String idBenefit;

    @Schema(description = "fecha del Canje")
    @JsonProperty("dateVoucher")
    private String dateVoucher;

    @Schema(description = "nombre")
    @JsonProperty("name")
    private String name;

    @Schema(description = "descripcion")
    @JsonProperty("description")
    private String description;

    @Schema(description = "banner")
    @JsonProperty("banner")
    private String banner;

    @Schema(description = "0 no canjeado, 1 canjeado.")
    @JsonProperty("isRedeemed")
    private int isRedeemed;

    @Schema(description = "Fecha del canje.")
    @JsonProperty("redemptionDate")
    private String redemptionDate;

    @Schema(description = "Fecha del canje.")
    @JsonProperty("managerId")
    private String managerId;

    @Schema(description = "Costo del voucher")
    @JsonProperty("voucherCost")
    private BigDecimal voucherCost;

    @Schema(description = "tipo de vale")
    @JsonProperty("typeValue")
    private String typeValue;

    @Schema(description = "Valor asumido del voucher.")
    @JsonProperty("assumedPercentage")
    private BigDecimal assumedPercentage;

    @Schema(description = "nota")
    @JsonProperty("note")
    private String note;

    @Schema(description = "estado")
    @JsonProperty("status")
    private String status;

    @Schema(description = "comercio")
    @JsonProperty("trade")
    private LoyaltyTrade trade;

    @Schema(description = "vale de consumo")
    @JsonProperty("redeemVoucher")
    private LoyaltyRedeemVoucher redeemVoucher;

    @Schema(description = "vale de consumo")
    @JsonProperty("redeemProduct")
    private LoyaltyRedeemVoucher redeemProduct;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoyaltyTrade {
        @Schema(description = "identificador del comercio")
        @JsonProperty("identifierTrade")
        private String identifierTrade;

        @Schema(description = "nombre del comercio")
        @JsonProperty("nameTrade")
        private String nameTrade;

        @Schema(description = "descripcion del comercio")
        @JsonProperty("descriptionTrade")
        private String descriptionTrade;

        @Schema(description = "logo del comercio")
        @JsonProperty("logoTrade")
        private String logoTrade;

        @Schema(description = "categoria del comercio")
        @JsonProperty("category")
        private LoyaltyTradeCategoryResponse category;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoyaltyRedeemVoucher {
        @Schema(description = "valor del vale de consumo")
        @JsonProperty("valueRedeemVoucher")
        private String valueRedeemVoucher;

        @Schema(description = "tipo de valor del vale de consumo")
        @JsonProperty("typeValueRedeemVoucher")
        private String typeValueRedeemVoucher;
    }
}
