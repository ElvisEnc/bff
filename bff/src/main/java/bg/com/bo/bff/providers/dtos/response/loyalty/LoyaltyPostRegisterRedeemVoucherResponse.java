package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyPostRegisterRedeemVoucherResponse {
    @JsonProperty("id")
    private String identifier;

    @JsonProperty("codigoVale")
    private String codeVoucher;

    @JsonProperty("idCampana")
    private String idCampaign;

    @JsonProperty("nombreTitular")
    private String holderName;

    @JsonProperty("documentoTitular")
    private String documentHolder;

    @JsonProperty("nombreBeneficiario")
    private String beneficiaryName;

    @JsonProperty("documentoBeneficiario")
    private String beneficiaryDocument;

    @JsonProperty("idPersona")
    private String idPerson;

    @JsonProperty("codigoPersona")
    private String codePerson;

    @JsonProperty("fechaCreacion")
    private String dateCreation;

    @JsonProperty("fechaCanje")
    private String dateVoucher;

    @JsonProperty("valorCanje")
    private Double valueVoucher;

    @JsonProperty("fechaExpiracion")
    private String expirationDate;

    @JsonProperty("idBeneficio")
    private String idBenefit;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("descripcion")
    private String description;

    @JsonProperty("banner")
    private String banner;

    @JsonProperty("nota")
    private String note;

    @JsonProperty("estado")
    private String status;

    @JsonProperty("comercio")
    private LoyaltyGetTrade trade;

    @JsonProperty("tipoVale")
    private String typeValue;

    @JsonProperty("valeConsumo")
    private LoyaltyRedeemVoucher redeemVoucher;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoyaltyGetTrade {
        @JsonProperty("id")
        private String identifierTrade;

        @JsonProperty("nombre")
        private String nameTrade;

        @JsonProperty("descripcion")
        private String descriptionTrade;

        @JsonProperty("logo")
        private String logoTrade;

        @JsonProperty("categoria")
        private LoyaltyGetTradeCategoryResponse category;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoyaltyRedeemVoucher {
        @JsonProperty("valor")
        private String valueRedeemVoucher;

        @JsonProperty("tipoValor")
        private String typeValueRedeemVoucher;

    }

}
