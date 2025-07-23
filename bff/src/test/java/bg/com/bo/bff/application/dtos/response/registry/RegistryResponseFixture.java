package bg.com.bo.bff.application.dtos.response.registry;

public class RegistryResponseFixture {

    public static RegistryResponse withDefault() {
        return RegistryResponse.builder()
                .appKey("MIICIjANBgkqhkiG9w0BAQEFAAOCA...")
                .personId(1L)
                .build();
    }
}
