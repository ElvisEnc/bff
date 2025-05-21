package bg.com.bo.bff.providers.dtos.request.loyalty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoyaltyRegisterRedeemVoucherRequest {

    private String codigoCampana;
    private String idCodigoSistema;
    private String idBeneficio;
    private String tipoBeneficio;
    private Information informacion;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Information {
        private String nombreBeneficiario;
        private String documentoBeneficiario;
        private String parentesco;
    }
}
