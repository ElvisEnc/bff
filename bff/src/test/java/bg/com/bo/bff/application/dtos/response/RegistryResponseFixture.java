package bg.com.bo.bff.application.dtos.response;

import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.dtos.response.RegistryResponse;

public class RegistryResponseFixture {

    public static RegistryResponse withDefault() {
        return RegistryResponse.builder()
                .appKey("MIICIjANBgkqhkiG9w0BAQEFAAOCA...")
                .personId(1L)
                .build();
    }
}
