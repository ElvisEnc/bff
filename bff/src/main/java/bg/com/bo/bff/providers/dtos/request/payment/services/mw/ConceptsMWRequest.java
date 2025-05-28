package bg.com.bo.bff.providers.dtos.request.payment.services.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConceptsMWRequest {

    private String personId;
    private String affiliationCode;
    private String serviceCode;

}
