package bg.com.bo.bff.application.dtos.response.softtoken;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;

public class SoftTokenResponseFixture {

    public static SoftTokenWelcomeResponse withDefaultWelcome() {
        return SoftTokenWelcomeResponse.builder()
                .message("ok")
                .build();
    }

    public static SoftTokenDataEnrollmentResponse withDefaultDataEnrollment() {
        return SoftTokenDataEnrollmentResponse.builder()
                .telephone("71474147")
                .email("test@test.com")
                .build();
    }

    public static SoftTokenQuestionEnrollmentResponse withDefaultQuestion() {
        return SoftTokenQuestionEnrollmentResponse.builder()
                .question("test")
                .format("date")
                .textHelp("tests")
                .build();
    }


    public static GenericResponse withDefaultGeneric() {
        return GenericResponse.builder()
                .code("ENROLLMENT")
                .message("ENROLLMENT")
                .title("ENROLLMENT")
                .build();
    }

    public static GenericResponse withDefaultGenericCode() {
        return GenericResponse.builder()
                .code("COD000")
                .message("Código enviado")
                .title("")
                .build();
    }

    public static SoftTokenObtainParametersResponse withDefaultParameter() {
        return SoftTokenObtainParametersResponse.builder()
                .tokenSize(6)
                .tokenDuration(30)
                .serialNumber("test")
                .dateProcessed("2024-02-18")
                .build();
    }

    public static SoftTokenGenerateTokenResponse withDefaultTokenGeneration() {
        return SoftTokenGenerateTokenResponse.builder()
                .token("test")
                .tokenDuration(4)
                .build();
    }

}
