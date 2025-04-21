package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyQrTransactionResponse {
    @Schema(description = "Identificador del voucher/vale.")
    @JsonProperty("identifier")
    private String identifier;

    @Schema(description = "Codigo del voucher/vale.")
    @JsonProperty("voucherCode")
    private String voucherCode;

    @Schema(description = "ID de la campaña del voucher/vale.")
    @JsonProperty("campaignId")
    private String campignId;

    @Schema(description = "Nombre del titular del voucher/vale.")
    @JsonProperty("holderName")
    private String holderName;

    @Schema(description = "Nro. documento del titular del voucher/vale.")
    @JsonProperty("documentHolder")
    private String documentHolder;

    @Schema(description = "Nombre del beneficiario del voucher/vale.")
    @JsonProperty("recipientName")
    private String recipientName;

    @Schema(description = "Nro. documento del beneficiario del voucher/vale.")
    @JsonProperty("recipientDocument")
    private String recipientDocument;

    @Schema(description = "Id de la persona.")
    @JsonProperty("personId")
    private String personId;

    @Schema(description = "Codigo de la persona.")
    @JsonProperty("personCode")
    private String personCode;

    @Schema(description = "Fecha de registro del voucher/vale.")
    @JsonProperty("registerDate")
    private String registerDate;

    @Schema(description = "Fecha de canje del voucher/vale.")
    @JsonProperty("redemptionDate")
    private String redemptionDate;

    @Schema(description = "Valor del voucher/vale.")
    @JsonProperty("redemptionValue")
    private int redemptionValue;

    @Schema(description = "Fecha de expiración del voucher/vale.")
    @JsonProperty("expirationDate")
    private String expirationDate;

    @Schema(description = "Id del beneficio del vale.")
    @JsonProperty("benefitId")
    private String benefitId;

    @Schema(description = "Nombre del voucher/vale.")
    @JsonProperty("voucherName")
    private String voucherName;

    @Schema(description = "Descripción del voucher/vale.")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Banner del voucher/vale.")
    @JsonProperty("banner")
    private String banner;

    @Schema(description = "Nota del voucher/vale.")
    @JsonProperty("note")
    private String note;

    @Schema(description = "Tipo de voucher/vale.")
    @JsonProperty("voucherType")
    private String voucherType;

    @Schema(description = "Estado del voucher/vale.")
    @JsonProperty("voucherStatus")
    private String voucherStatus;

    @Schema(description = "Comercio")
    @JsonProperty("merchant")
    private LoyaltyFeaturedMerchant merchant;

    @Schema(description = "Vale pasaje.")
    @JsonProperty("travelVoucher")
    private TravelVoucher travelVoucher;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TravelVoucher {
        @Schema(description = "Parentesco.")
        @JsonProperty("relationship")
        private String relationship;

        @Schema(description = "Tipo de vale de pasaje.")
        @JsonProperty("travelVoucherType")
        private String travelVoucherType;

        @Schema(description = "Origen de partida del pasaje.")
        @JsonProperty("origin")
        private String origin;

        @Schema(description = "Destino del pasaje.")
        @JsonProperty("destination")
        private String destination;

    }

}
