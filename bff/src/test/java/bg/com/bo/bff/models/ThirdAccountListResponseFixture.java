package bg.com.bo.bff.models;
import java.util.Arrays;

public class ThirdAccountListResponseFixture {
    public static ThirdAccountListResponse withDefault() {
        ThirdAccount thirdAccount = new ThirdAccount();
        thirdAccount.setId("123454654654");
        thirdAccount.setAccountId("1234345354354");
        thirdAccount.setAccountNumber("123456453452345");
        thirdAccount.setCurrencyCode("testt");
        thirdAccount.setCurrencyAcronym("testt");
        thirdAccount.setAccountType("testt");
        thirdAccount.setAccountTypeAbbreviation("testt");
        thirdAccount.setClientName("testt");
        thirdAccount.setAccountAliases("testt");
        thirdAccount.setIsFavorite("testt");
        ThirdAccountListResponse response = new ThirdAccountListResponse();
        response.setData(Arrays.asList(thirdAccount));
        return response;
    }
}