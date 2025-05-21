package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyQrTransactionAPIResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("codigoVale")
    private String voucherCode;

    @JsonProperty("idCampana")
    private String campignId;

    @JsonProperty("nombreTitular")
    private String holderName;

    @JsonProperty("documentoTitular")
    private String documentHolder;

    @JsonProperty("nombreBeneficiario")
    private String recipientName;

    @JsonProperty("documentoBeneficiario")
    private String recipientDocument;

    @JsonProperty("idPersona")
    private String personId;

    @JsonProperty("codigoPersona")
    private String personCode;

    @JsonProperty("fechaCreacion")
    private String registerDate;

    @JsonProperty("fechaCanje")
    private String redemptionDate;

    @JsonProperty("valorCanje")
    private int redemptionValue;

    @JsonProperty("fechaExpiracion")
    private String expirationDate;

    @JsonProperty("idBeneficio")
    private String benefitId;

    @JsonProperty("nombre")
    private String voucherName;

    @JsonProperty("descripcion")
    private String description;

    @JsonProperty("banner")
    private String banner;

    @JsonProperty("nota")
    private String note;

    @JsonProperty("tipoVale")
    private String voucherType;

    @JsonProperty("estado")
    private String voucherStatus;

    @JsonProperty("comercio")
    private LoyaltyFeaturedMerchantAPIResponse merchant;

    @JsonProperty("valePasaje")
    private TravelVoucher travelVoucher;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TravelVoucher {
        @JsonProperty("parentesco")
        private String relationship;

        @JsonProperty("tipoPasaje")
        private String travelVoucherType;

        @JsonProperty("origen")
        private String origin;

        @JsonProperty("destino")
        private String destination;

    }


}
