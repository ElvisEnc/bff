package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.application.dtos.request.TransferRequest;
import bg.com.bo.bff.application.dtos.request.registry.RegistryCredentialsRequest;
import bg.com.bo.bff.application.dtos.request.registry.RegistryDeviceIdentificatorRequest;
import bg.com.bo.bff.application.dtos.request.registry.RegistryOldDeviceIdentificatorRequest;
import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.providers.dtos.requests.loginagn.CredentialsRequest;

public class RegistryRequestFixture {

    public static RegistryRequest withDefault() {
        return RegistryRequest.builder()
                .credentials(RegistryCredentialsRequest.builder()
                        .password("1234")
                        .passwordBiometric("abcd")
                        .type(1)
                        .personId(1L)
                        .build())
                .deviceIdentificator(RegistryDeviceIdentificatorRequest.builder()
                        .appVersion("v1")
                        .boot("mw8998-002.0069.00")
                        .debug(false)
                        .emulator(false)
                        .firstInstallTime(1517681764528L)
                        .soCodeName("iPhone7.2")
                        .systemBuildId("12A269")
                        .systemName("iPhone OS")
                        .systemVersion("Version del OS del dispositivo")
                        .uniqueId("1243asdf")
                        .userAgent("Mozilla/5.0")
                        .build())
                .oldDeviceIdentificator(RegistryOldDeviceIdentificatorRequest.builder()
                        .os("ANDROID")
                        .channel("1")
                        .didbga("c1f6e182d5e1d00c")
                        .id("c1f6e182d5e1d00c")
                        .imei("asdopmfwpeof32")
                        .ksBga("c1f6e182d5e1d00c")
                        .model("samsung")
                        .rsid("c1f6e182d5e1d00c")
                        .version("$aver")
                        .build())
                .userKey("MIICIjANBgkqhkiG9w0BAQEFAAOCA...")
                .geoReference("12.12312,12.23223")
                .build();
    }
}
