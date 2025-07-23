package bg.com.bo.bff.providers.dtos.request.loyalty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoyaltyStatementPointRequest {

    private String codigoPersona;
    private String codigoCampana;
    private String fechaInicial;
    private String fechaFinal;
}
