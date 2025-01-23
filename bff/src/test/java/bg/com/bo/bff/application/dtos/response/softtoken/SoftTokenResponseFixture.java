package bg.com.bo.bff.application.dtos.response.softtoken;

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
}
