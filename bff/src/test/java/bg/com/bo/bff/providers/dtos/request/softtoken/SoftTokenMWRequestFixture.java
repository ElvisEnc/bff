package bg.com.bo.bff.providers.dtos.request.softtoken;

import bg.com.bo.bff.providers.dtos.request.softtoken.mw.*;

public class SoftTokenMWRequestFixture {

    public static SoftTokenMWRequest withDefault() {
        return SoftTokenMWRequest.builder()
                .codPerson(1212)
                .codApplication(1)
                .codCanal(1)
                .codLanguage(1)
                .imei("test")
                .ksBga("test")
                .didBga("test")
                .operatingSystem("test")
                .build();
    }

    public static SoftTokenSentCodeMWRequest withDefaultCode() {
        return SoftTokenSentCodeMWRequest.builder()
                .codPerson(1212)
                .codApplication(1)
                .codCanal(1)
                .codLanguage(1)
                .imei("test")
                .ksBga("test")
                .didBga("test")
                .operatingSystem("test")
                .email("test@test.com")
                .telephone("74125874")
                .build();
    }

    public static SoftTokenValidateCodeMWRequest withDefaultValidationCode() {
        return SoftTokenValidateCodeMWRequest.builder()
                .codPerson(1212)
                .codApplication(1)
                .codCanal(1)
                .codLanguage(1)
                .imei("test")
                .ksBga("test")
                .didBga("test")
                .operatingSystem("test")
                .enrollmentSecurityCode("pruebaaasdff")
                .build();
    }

    public static SoftTokenValidateQuestionMWRequest withDefaultValidationQuestion() {
        return SoftTokenValidateQuestionMWRequest.builder()
                .codPerson(1212)
                .codApplication(1)
                .codCanal(1)
                .codLanguage(1)
                .imei("test")
                .ksBga("test")
                .didBga("test")
                .operatingSystem("test")
                .answerQuestionSecurityEnrollment("vnfkjgnfgnkjfgrf")
                .model("iphone")
                .build();
    }

    public static SoftTokenMWRequest withDefaultParameter() {
        return SoftTokenMWRequest.builder()
                .codPerson(1212)
                .codApplication(1)
                .codCanal(1)
                .codLanguage(1)
                .imei("test")
                .ksBga("test")
                .didBga("test")
                .operatingSystem("test")
                .build();
    }

    public static SoftTokenRegistrationTokenMWRequest withDefaultRegistrationToken() {
        return SoftTokenRegistrationTokenMWRequest.builder()
                .codPerson(1212)
                .codApplication(1)
                .codCanal(1)
                .codLanguage(1)
                .imei("test")
                .ksBga("test")
                .didBga("test")
                .operatingSystem("test")
                .token("test")
                .build();
    }

    public static SofTokenValidateMWRequest withDefaultTokenValidation() {
        return SofTokenValidateMWRequest.builder()
                .personId("1212")
                .deviceId("test")
                .build();
    }

    public static SofTokenGenerateTokenMWRequest withDefaultGenerateToken() {
        return SofTokenGenerateTokenMWRequest.builder()
                .personId("1212")
                .codCanal("3")
                .deviceId("test")
                .build();
    }

    public static SofTokenEnrollmentMWRequest withDefaultEnrollment() {
        return SofTokenEnrollmentMWRequest.builder()
                .personId("1212")
                .deviceId("test")
                .deviceModel("test")
                .telephone("test")
                .build();
    }

    public static SoftTokenValidateTokenMWRequest withDefaultValidateToken() {
        return SoftTokenValidateTokenMWRequest.builder()
                .personId("1212")
                .token("test")
                .build();
    }
}
