package bg.com.bo.bff.providers.dtos.request.loyalty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoyaltyPersonCampRequest {
    private String idPersona;
    private String codigoCampana;
}
