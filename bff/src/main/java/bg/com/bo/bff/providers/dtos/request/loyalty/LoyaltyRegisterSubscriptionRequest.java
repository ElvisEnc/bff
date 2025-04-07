package bg.com.bo.bff.providers.dtos.request.loyalty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoyaltyRegisterSubscriptionRequest {

    private boolean firmaDigital;
    private String idPersona;
    private String codigoCampana;
    private String numeroCuentaJTS_OID;
    private String email;
    private String suscripcionOrigen;
}
