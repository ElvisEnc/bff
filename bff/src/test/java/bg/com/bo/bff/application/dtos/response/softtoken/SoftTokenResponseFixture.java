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

    public static SoftTokenValidationEnrollmentResponse withDefaultValidate() {
        return SoftTokenValidationEnrollmentResponse.builder()
                .status("ENROLLMENT")
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
}
