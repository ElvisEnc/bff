package bg.com.bo.bff.providers.dtos.response.debit.card;

import java.util.List;

public class DCInternetAuthorizationNWResponseFixture {
    public static DCInternetAuthorizationNWResponse whitDefault(){
        DCInternetAuthorizationNW dcInternetAuthorizationNW = DCInternetAuthorizationNW.builder()
                .status("VIGENTE")
                .type("TEMPORAL")
                .startDate("07/06/2026")
                .endDate("10/06/2026")
                .amount("100")
                .currency("$us")
                .cardNumber("4218******0481")
                .internetIdTjTD("12078954")
                .processDate("2024-06-06")
                .build();
        return new DCInternetAuthorizationNWResponse(List.of(dcInternetAuthorizationNW));
    }

    public static String whtiErrorMDWTJD005(){
        return "{  \"code\": 406, \"errorType\": \"Technical\", \"errorDetailResponse\": " +
                "[ { \"code\": \"MDWTJD-005\", \"description\": \"Error no found limits internet to debit card\" }]}";
    }
}


