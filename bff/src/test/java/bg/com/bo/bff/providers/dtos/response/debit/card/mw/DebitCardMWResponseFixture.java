package bg.com.bo.bff.providers.dtos.response.debit.card.mw;

import bg.com.bo.bff.providers.dtos.response.generic.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorDetailResponse;
import bg.com.bo.bff.providers.models.enums.middleware.debit.card.DebitCardMiddlewareError;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DebitCardMWResponseFixture {
    public static DCLimitsMWResponse withDefault() {
        return DCLimitsMWResponse.builder()
                .data(DCLimitsMWResponse.LimitsData.builder()
                        .pciId(1)
                        .build()
                )
                .build();
    }

    public static DCDetailMWResponse withDefaultDetail() {
        return DCDetailMWResponse.builder()
                .data(DCDetailMWResponse.CardData.builder()
                        .cardNumber("1234567890123456")
                        .typeCard("VISA")
                        .branch("1234")
                        .clientNumber("123456")
                        .personNumber("123456")
                        .cardName("John Doe")
                        .deliveryDate("2021-01-01")
                        .expirationDate("2021-12-31")
                        .status("A")
                        .statusDescription("Active")
                        .protectionInsurance("S")
                        .limitExpirationDate("2021-12-31")
                        .limitAmountME("1000")
                        .limitExtractions("5")
                        .build()
                )
                .build();
    }

    public static DCAccountsOrderMWResponse withDefaultAccountsOrder() {
        return DCAccountsOrderMWResponse.builder()
                .data(DCAccountsOrderMWResponse.AccountsData.builder()
                        .pciId(1)
                        .build()
                )
                .build();
    }

    public static AccountsDebitCardMWResponse withDefaultAccountsDebitCardMWResponse() {
        return AccountsDebitCardMWResponse.builder()
                .data(Arrays.asList(withDefaultAccountDC(), withDefaultAccountDC()))
                .build();
    }

    public static AccountsDebitCardMWResponse.AccountDebitCard withDefaultAccountDC() {
        return AccountsDebitCardMWResponse.AccountDebitCard.builder()
                .jtsOid(123)
                .accountNumber(BigDecimal.valueOf(123))
                .productType("123")
                .statusDescription("123")
                .currency("123")
                .pledgeFund(123)
                .currencyCode("123")
                .accountType("123")
                .ordinalPreference(123)
                .build();
    }

    public static CreateAuthorizationOnlinePurchaseMWResponse withDefaultCreateAuthorizationOnlinePurchaseMWResponse() {
        return new CreateAuthorizationOnlinePurchaseMWResponse(
                "12334",
                "OK",
                "0"
        );
    }

    public static CreateAuthorizationOnlinePurchaseMWResponse errorCreateAuthorizationOnlinePurchaseMWResponse() {
        return new CreateAuthorizationOnlinePurchaseMWResponse(
                null,
                "Error",
                "0"
        );
    }

    public static DCInternetAuthorizationNWResponse withDefaultDCInternetAuthorizationNWResponse() {
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

    public static String withErrorMDWTJD005() {
        return "{  \"code\": 406, \"errorType\": \"Technical\", \"errorDetailResponse\": " +
                "[ { \"code\": \"MDWTJD-005\", \"description\": \"Error no found limits internet to debit card\" }]}";
    }

    public static DCLimitsMWResponse withDefaultDCLimitsMWResponse() {
        return DCLimitsMWResponse.builder()
                .data(withDefaultLimisData())
                .build();
    }

    public static DCLimitsMWResponse.LimitsData withDefaultLimisData() {
        return DCLimitsMWResponse.LimitsData.builder()
                .pciId(123)
                .build();
    }

    public static ApiErrorResponse withDefaultApiErrorResponse() {
        List<ErrorDetailResponse> list = new ArrayList<>();
        ErrorDetailResponse errorDetailResponse = ErrorDetailResponse.builder()
                .code(DebitCardMiddlewareError.MDWTJD_002.getCodeMiddleware())
                .description(DebitCardMiddlewareError.MDWTJD_002.getMessage())
                .build();

        list.add(errorDetailResponse);

        return ApiErrorResponse.builder()
                .errorDetailResponse(list)
                .build();
    }

    public static ApiErrorResponse withErrorMDWTJD004() {
        List<ErrorDetailResponse> list = new ArrayList<>();
        ErrorDetailResponse errorDetailResponse = ErrorDetailResponse.builder()
                .code(DebitCardMiddlewareError.MDWTJD_004.getCodeMiddleware())
                .description(DebitCardMiddlewareError.MDWTJD_004.getMessage())
                .build();

        list.add(errorDetailResponse);

        return ApiErrorResponse.builder()
                .errorDetailResponse(list)
                .build();
    }

    public static DeleteAuthPurchaseMWResponse withDefaultDeleteAuthPurchaseMWResponse() {
        return DeleteAuthPurchaseMWResponse.builder()
                .data(deleteAuthDataDefault())
                .build();
    }

    public static DeleteAuthPurchaseMWResponse.DeleteAuthData deleteAuthDataDefault() {
        return DeleteAuthPurchaseMWResponse.DeleteAuthData.builder()
                .idPci("123")
                .message("OK")
                .code("0")
                .build();
    }

    public static DeleteAuthPurchaseMWResponse errorDefaultDeleteAuthPurchaseMWResponse() {
        return DeleteAuthPurchaseMWResponse.builder()
                .data(deleteAuthDataErrorDefault())
                .build();
    }

    public static DeleteAuthPurchaseMWResponse.DeleteAuthData deleteAuthDataErrorDefault() {
        return DeleteAuthPurchaseMWResponse.DeleteAuthData.builder()
                .idPci("")
                .message("ERROR")
                .code("3")
                .build();
    }

    public static ListDebitCardMWResponse withDefaultListDebitCardMWResponse() {
        return ListDebitCardMWResponse.builder()
                .data(Arrays.asList(debitCardMWDefaultListDebitCardMWResponse(), debitCardMWDefaultListDebitCardMWResponse()))
                .build();
    }

    public static ListDebitCardMWResponse.DebitCardMW debitCardMWDefaultListDebitCardMWResponse() {
        return ListDebitCardMWResponse.DebitCardMW.builder()
                .idPci("123")
                .cardId("123")
                .department("123")
                .nroClient("123")
                .nroPerson("123")
                .cardHolder("123")
                .cardName("123")
                .deliveryDate("123")
                .expirationDate("123")
                .statusDescription("123")
                .status("123")
                .protectionInsurance("123")
                .insuranceAccountId("123")
                .insuranceAccountNumber("123")
                .statusCurrier("123")
                .build();
    }

    public static UpdateSecureMWResponse withDefaultUpdateSecureMWResponse() {
        return UpdateSecureMWResponse.builder()
                .data(withDataDefaultUpdateSecureMW())
                .build();
    }

    public static UpdateSecureMWResponse.UpdateSecureMW withDataDefaultUpdateSecureMW() {
        return UpdateSecureMWResponse.UpdateSecureMW.builder()
                .idPci(123)
                .build();
    }
}
