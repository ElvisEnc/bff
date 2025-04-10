package bg.com.bo.bff.application.dtos.request.loyalty;

import bg.com.bo.bff.commons.annotations.loyalty.ValidTypeBenefit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRedeemVoucherRequest {

    @Valid
    @Schema(description = "Identificador del beneficio")
    private String idBenefit;

    @ValidTypeBenefit
    @Schema(description = "Tipo de Beneficio")
    private String typeBenefit;

    @Schema(description = "Información para el canje")
    private RegisterRedeemVoucherRequest.InformationVoucher information;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InformationVoucher {

        @Valid
        @NotBlank(message = "El nombre del beneficiario es obligatorio")
        private String beneficiaryName;

        @Valid
        @NotBlank(message = "El documento del beneficiario es obligatorio")
        private String beneficiaryDocument;

        @Valid
        @NotBlank(message = "La relación con el beneficiario es obligatoria")
        private String beneficiaryRelationship;
    }
}
