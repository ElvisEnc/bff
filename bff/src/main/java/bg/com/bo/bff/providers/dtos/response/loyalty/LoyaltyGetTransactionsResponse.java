package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyGetTransactionsResponse {
    @JsonProperty("id")
    private String identifier;

    @JsonProperty("codigoVale")
    private String voucherCode;

    @JsonProperty("idCampana")
    private String campaignId;

    @JsonProperty("nombreTitular")
    private String holderName;

    @JsonProperty("documentoTitular")
    private String holderDocument;

    @JsonProperty("nombreBeneficiario")
    private String beneficiaryName;

    @JsonProperty("documentoBeneficiario")
    private String beneficiaryDocument;

    @JsonProperty("idPersona")
    private String personId;

    @JsonProperty("codigoPersona")
    private String personCode;

    @JsonProperty("fechaCreacion")
    private String creationDate;

    @JsonProperty("valorCanje")
    private Integer redemptionValue;

    @JsonProperty("fechaExpiracion")
    private String expirationDate;

    @JsonProperty("idBeneficio")
    private String benefitId;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("descripcion")
    private String description;

    @JsonProperty("banner")
    private String banner;

    @JsonProperty("isCanjeado")
    private Integer redeemed;

    @JsonProperty("fechaCanje")
    private String redemptionDate;

    @JsonProperty("administradorGestorId")
    private String managerId;

    @JsonProperty("costoVale")
    private Integer voucherCost;

    @JsonProperty("tipoVale")
    private String voucherType;

    @JsonProperty("porcentajeAsumido")
    private Integer assumedPercentage;

    @JsonProperty("nota")
    private String note;

    @JsonProperty("estado")
    private String status;

    @JsonProperty("comercio")
    private LoyaltyGetStoreFeaturedResponse store;

    @JsonProperty("valeConsumo")
    private GetConsumptionVoucher consumptionVoucher;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetConsumptionVoucher {

        @JsonProperty("valor")
        private String valueVoucher;

        @JsonProperty("tipoValor")
        private String valueType;
    }
}
