package bg.com.bo.bff.providers.dtos.response.onboarding.manager;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw.DisableDeviceMWResponse;
import bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw.ListDevicesMWResponse;

import java.util.Collections;

import static bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw.ListDevicesMWResponse.DeviceMW;

public class OnboardingManagerMWResponseFixture {

    public static ListDevicesMWResponse withDefaultListDevicesOnboarding() {
        return ListDevicesMWResponse.
                builder()
                .data(Collections.singletonList(DeviceMW.builder()
                        .identifier(484848)
                        .personNumber(151515)
                        .phoneNumber("7497777777")
                        .system("SystemAndroid")
                        .model("A7")
                        .registrationDate("2025/04/08")
                        .migrationDate("2025/04/08")
                        .lastConnectionDate("2025/04/08")
                        .failedAttemptsCount(0)
                        .statusCode("A")
                        .applicationCode(2)
                        .session("abcde321")
                        .deviceIdentifierK("device0001")
                        .deviceIdentifier("device0001")
                        .deviceIp("localhost")
                        .boot("boot")
                        .osCode("A8")
                        .osName("Android")
                        .osVersion("A8")
                        .compiledOsCode("8")
                        .biometricEnablement("OK")
                        .biometricToken("OK")
                        .agentUser("5464546")
                        .installationDate("2025/04/08")
                        .debugging(0)
                        .emulator(0)
                        .registeredUser("2025/04/08")
                        .userRegistrationDate("2025/04/08")
                        .registrationBranch(1)
                        .modifiedUser("Ok")
                        .modificationDate("2025/04/08")
                        .modificationBranch("2025/04/08")
                        .authenticationTypeCode(2)
                        .clientId("151515")
                        .roleCode("1")
                        .clientProtocol("Ok")
                        .accessType("Ok")
                        .clientSecret("Ok").build())
                ).build();
    }

    public static ListDevicesMWResponse withDefaultListDevicesOnboardingWithNull() {
        return ListDevicesMWResponse.
                builder()
                .data(null).build();
    }

    public static ListDevicesMWResponse withDefaultListDevicesOnboardingWithDataNull() {
        return null;
    }


    public static GenericResponse withDefaultDisableDevice() {
        return GenericResponse.builder()
                .title("Dispositivo desvinculado.")
                .message("Se ha desvinculado el dispositivo exitosamente.")
                .code("SUCCESS").build();
    }

    public static GenericResponse withDefaultDisableDeviceWithError() {
        return GenericResponse.builder()
                .title("Ocurrió un problema")
                .message("Ocurrio algo inesperado, no se pudo desactivar el dispositivo.")
                .code("ERROR").build();
    }

    public static DisableDeviceMWResponse withDefaultDisableDeviceWithSuccess() {
        return DisableDeviceMWResponse.builder()
                .data(DisableDeviceMWResponse.DataMWResponse.builder()
                        .codeResponse("COD000")
                        .messageResponse("OK")
                        .build())
                .build();
    }

}
