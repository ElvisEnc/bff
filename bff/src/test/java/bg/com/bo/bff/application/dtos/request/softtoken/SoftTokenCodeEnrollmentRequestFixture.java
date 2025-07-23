package bg.com.bo.bff.application.dtos.request.softtoken;

public class SoftTokenCodeEnrollmentRequestFixture {
    public static SoftTokenCodeEnrollmentRequest withDefault() {
        return SoftTokenCodeEnrollmentRequest.builder()
                .phone("74123654")
                .email("test@test.com")
                .build();
    }
    public static SoftTokenCodeEnrollmentRequest withDefaultNull() {
        return SoftTokenCodeEnrollmentRequest.builder()
                .phone(null)
                .email(null)
                .build();
    }
    public static SoftTokenCodeEnrollmentRequest withDefaultNullEmpty() {
        return SoftTokenCodeEnrollmentRequest.builder()
                .phone("")
                .email("")
                .build();
    }
    public static SoftTokenCodeEnrollmentRequest withDefaultNullEmpty2() {
        return SoftTokenCodeEnrollmentRequest.builder()
                .phone(null)
                .email("")
                .build();
    }
    public static SoftTokenValidateCodeEnrollmentRequest withDefaultValidation() {
        return SoftTokenValidateCodeEnrollmentRequest.builder()
                .code("frkgjreogeroghmer")
                .build();
    }

    public static SoftTokenValidationQuestionRequest withDefaultValidationQuestion() {
        return SoftTokenValidationQuestionRequest.builder()
                .answerQuestion("frkgjreogeroghmer")
                .build();
    }

    public static SoftTokenCodeTokenRequest withDefaultToken() {
        return SoftTokenCodeTokenRequest.builder()
                .token("frkgjreogeroghmer")
                .build();
    }

    public static SoftTokenEnrollmentRequest withDefaultEnrollment() {
        return SoftTokenEnrollmentRequest.builder()
                .phone("74587458")
                .build();
    }

    public static SoftTokenCodeTokenRequest withDefaultCode() {
        return SoftTokenCodeTokenRequest.builder()
                .token("74587458")
                .build();
    }

    public static SoftTokenEnrollmentRequest withDefaultEnrollmentST() {
        return SoftTokenEnrollmentRequest.builder()
                .phone("74587458")
                .build();
    }

    public static SoftTokenCodeTokenRequest withDefaultTokenST() {
        return SoftTokenCodeTokenRequest.builder()
                .token("74587458")
                .build();
    }

}
