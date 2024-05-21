package bg.com.bo.bff.application.dtos.request;

public class ChangePasswordRequestFixture {
    public static ChangePasswordRequest withDefault() {
        return ChangePasswordRequest.builder()
                .newPassword("1234")
                .oldPassword("1234")
                .build();
    }
}