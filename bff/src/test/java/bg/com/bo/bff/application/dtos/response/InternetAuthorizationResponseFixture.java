package bg.com.bo.bff.application.dtos.response;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsPeriod;
import bg.com.bo.bff.application.dtos.response.debitcard.DCInternetAuthorization;
import bg.com.bo.bff.application.dtos.response.debitcard.InternetAuthorizationResponse;

import java.util.List;

public class InternetAuthorizationResponseFixture {
    public static InternetAuthorizationResponse withDefault(){
        DCLimitsPeriod period = DCLimitsPeriod.builder()
                .start("07/06/2026")
                .end("10/06/2026")
                .build();
        DCInternetAuthorization dcInternetAuthorization = DCInternetAuthorization.builder()
                .id("12078954")
                .amount("100")
                .period(period)
                .build();
        return InternetAuthorizationResponse.builder()
                .data( List.of(dcInternetAuthorization))
                .build();
    }
}
