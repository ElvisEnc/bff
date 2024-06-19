package bg.com.bo.bff.providers.dtos.response.debit.card;

public class DeleteAuthPurchaseMWResponseFixture {
    public static DeleteAuthPurchaseMWResponse withDefault() {
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

    public static DeleteAuthPurchaseMWResponse errorDefault() {
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
}