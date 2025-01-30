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
}
