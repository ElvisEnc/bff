package bg.com.bo.bff.mappings.providers.loyalty;

import bg.com.bo.bff.application.dtos.request.loyalty.RegisterSubscriptionRequest;
import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltySumPointResponse;
import bg.com.bo.bff.providers.dtos.request.loyalty.LoyaltyRegisterSubscriptionRequest;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySumPointServerResponse;
import bg.com.bo.bff.providers.dtos.response.loyalty.LoyaltySystemCodeServerResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoyaltyMapper implements ILoyaltyMapper{

    @Override
    public LoyaltySystemCodeResponse convertResponse(LoyaltySystemCodeServerResponse response) {
        return LoyaltySystemCodeResponse.builder()
                .codeSystem(response.getCodeSystem())
                .build();
    }

    @Override
    public LoyaltySumPointResponse convertResponse(LoyaltySumPointServerResponse response) {
        return LoyaltySumPointResponse.builder()
                .points(response.getPoint())
                .build();
    }

    @Override
    public Map<String, String> mapperRequestGet(String personId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("sesion", "01042025154313ffe5a4bb00d20a5b");
        headers.put("idpersona", personId);
        return headers;
    }


    @Override
    public Map<String, String> mapperRequestPost(String personId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("sesion", "01042025154313ffe5a4bb00d20a5b");
        headers.put("idpersona", personId);
        headers.put("Content-Type", "application/json");
        return headers;
    }


    @Override
    public LoyaltyRegisterSubscriptionRequest mapperRequest(String personId, String accountId, RegisterSubscriptionRequest request) {
        return LoyaltyRegisterSubscriptionRequest.builder()
                .firmaDigital(true)
                .idPersona(personId)
                .codigoCampana("1")
                .numeroCuentaJTS_OID(accountId)
                .email(request.getEmail())
                .suscripcionOrigen("GANAMOVIL")
                .build();
    }
}

