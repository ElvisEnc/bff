package bg.com.bo.bff.application.dtos.response;

public class ValidateAccountResponseFixture {
    public static ValidateAccountResponse withDefault() {
        return new ValidateAccountResponse(
                "6957474",
                "1310766620",
                "5219027",
                "EMPLEADO BANCO",
                "068",
                "701"
        );
    }
}
